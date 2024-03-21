package mart.mono.purchases;

import mart.mono.models.CartItem;
import mart.mono.models.Purchase;
import mart.mono.repositories.PurchasesRepository;
import mart.mono.services.PurchasesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchasesServiceTest {

    @Mock
    PurchasesRepository purchasesRepository;

    @InjectMocks
    PurchasesService purchasesService;

    @Captor
    ArgumentCaptor<Purchase> purchaseCaptor;

    @Test
    void purchase() {
        CartItem cartItem = CartItem.builder()
            .quantity(1)
            .product(null)
            .build();
        List<CartItem> cartItemEntities = List.of(cartItem);

        purchasesService.purchase(cartItemEntities);

        verify(purchasesRepository).save(purchaseCaptor.capture());
        Purchase actualPurchase = purchaseCaptor.getValue();
        assertThat(actualPurchase.getItems()).hasSize(1);
    }
}
