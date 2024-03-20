package mart.mono.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import mart.mono.purchases.PurchasesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    PurchasesService purchasesService;

    CartService cartService;

    RestClient restClient;

    MockRestServiceServer mockRestServiceServer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Captor
    ArgumentCaptor<CartItemEntity> cartItemCaptor;

    @BeforeEach
    void setUp() {
        RestClient.Builder restClientBuilder = RestClient.builder();
        mockRestServiceServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        restClient = restClientBuilder.build();
        cartService = new CartService(cartRepository, purchasesService, restClient);
    }

    @Test
    @DisplayName("Should give list of all cart items")
    void returnListOfCartItems() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder()
            .id(productId)
            .build();
        mockRestServiceServer.expect(requestToUriTemplate("/api/products/{0}", productId))
            .andRespond(withSuccess(objectMapper.writeValueAsString(product), MediaType.APPLICATION_JSON));

        CartItemEntity cartItemEntity = CartItemEntity.builder()
            .productId(productId)
            .quantity(69)
            .build();
        when(cartRepository.findAll()).thenReturn(List.of(cartItemEntity));

        List<CartItem> returnedItems = cartService.get();

        assertThat(returnedItems).containsExactly(cartItemEntity.toCartItem(product));
        verify(cartRepository, times(1)).findAll();
        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("Should add single item to cart when given product")
    void addSingleProduct() {
        UUID productId = UUID.randomUUID();
        Product product = Product
            .builder()
            .name("GBaller's Secret Pokemon Card Collection")
            .id(productId)
            .build();
        CartItemEntity cartItemEntity = CartItemEntity.builder()
            .productId(productId)
            .quantity(1)
            .build();
        when(cartRepository.save(cartItemCaptor.capture())).thenReturn(cartItemEntity);

        CartItem cartItem = cartService.add(product);

        assertThat(cartItem.getProduct()).isEqualTo(product);
        assertThat(cartItem.getQuantity()).isEqualTo(1);
        CartItemEntity savedCartItem = cartItemCaptor.getValue();
        assertThat(savedCartItem.getProductId()).isEqualTo(productId);
        assertThat(savedCartItem.getQuantity()).isEqualTo(1);
    }

    @Nested
    @DisplayName("Remove item from cart")
    class removeItem {

        @Test
        @DisplayName(" Should remove item from cart when given item id")
        void removeSuccess() {
            UUID uuid = UUID.randomUUID();
            when(cartRepository.findById(uuid))
                .thenReturn(Optional.of(CartItemEntity
                    .builder()
                    .id(uuid)
                    .build()));

            cartService.remove(uuid);

            verify(cartRepository, times(1)).delete(any());
        }

        @Test
        @DisplayName("Should not try to remove item from cart when given id cannot be found")
        void removeFail() {
            UUID uuid = UUID.randomUUID();
            when(cartRepository.findById(uuid))
                .thenReturn(Optional.empty());

            cartService.remove(uuid);

            verify(cartRepository, times(0)).delete(any());
        }
    }

    @Test
    @DisplayName("Should remove all items in cart when purchase success")
    void checkOutRequest() {
        List<CartItemEntity> items = List.of(CartItemEntity
            .builder()
            .id(UUID.randomUUID())
            .build());
        when(cartRepository.findAll()).thenReturn(items);

        cartService.checkOut();

        verify(purchasesService).purchase(items);
        verify(cartRepository, times(1)).deleteAll();
    }
}





