package mart.mono.cart;

import mart.mono.models.CartItem;
import mart.mono.models.Product;
import mart.mono.services.PurchasesService;
import mart.mono.repositories.CartRepository;
import mart.mono.services.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    PurchasesService purchasesService;

    @InjectMocks
    CartService cartService;

    @Captor
    ArgumentCaptor<CartItem> cartItemCaptor;

    @Test
    @DisplayName("Should give list of all cart items")
    void returnListOfCartItems() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder()
            .id(productId)
            .build();

        CartItem cartItem = CartItem.builder()
            .product(product)
            .quantity(69)
            .build();
        when(cartRepository.findAll()).thenReturn(List.of(cartItem));

        List<CartItem> returnedItems = cartService.get();

        assertThat(returnedItems).containsExactly(cartItem);
        verify(cartRepository, times(1)).findAll();
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
        CartItem cartItemEntity = CartItem.builder()
            .product(product)
            .quantity(1)
            .build();
        when(cartRepository.save(cartItemCaptor.capture())).thenReturn(cartItemEntity);

        CartItem cartItem = cartService.add(product);

        assertThat(cartItem.getProduct()).isEqualTo(product);
        assertThat(cartItem.getQuantity()).isEqualTo(1);
        CartItem savedCartItem = cartItemCaptor.getValue();
        assertThat(savedCartItem.getProduct()).isEqualTo(product);
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
                .thenReturn(Optional.of(CartItem
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
        List<CartItem> items = List.of(CartItem
            .builder()
            .id(UUID.randomUUID())
            .build());
        when(cartRepository.findAll()).thenReturn(items);

        cartService.checkOut();

        verify(purchasesService).purchase(items);
        verify(cartRepository, times(1)).deleteAll();
    }
}





