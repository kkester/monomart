package mart.mono.purchases;

import mart.mono.cart.CartItemEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.List;
import java.util.UUID;

import static mart.mono.purchases.PurchasesService.PURCHASE_EVENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchasesServiceTest {

    @Mock
    PurchasesRepository purchasesRepository;

    @Mock
    StreamBridge streamBridge;

    @InjectMocks
    PurchasesService purchasesService;

    @Captor
    ArgumentCaptor<PurchaseEvent> purchaseEventCaptor;

    @Captor
    ArgumentCaptor<PurchaseEntity> purchaseCaptor;

    @Test
    void purchase() {
        CartItemEntity cartItemEntity = CartItemEntity.builder()
            .quantity(1)
            .productId(UUID.randomUUID())
            .build();
        List<CartItemEntity> cartItemEntities = List.of(cartItemEntity);

        purchasesService.purchase(cartItemEntities);

        verify(purchasesRepository).save(purchaseCaptor.capture());
        PurchaseEntity actualPurchaseEntity = purchaseCaptor.getValue();
        assertThat(actualPurchaseEntity.getItems()).hasSize(1);

        verify(streamBridge).send(eq(PURCHASE_EVENT), purchaseEventCaptor.capture());
        PurchaseEvent actualPurchaseEvent = purchaseEventCaptor.getValue();
        assertThat(actualPurchaseEvent).isEqualTo(PurchaseEvent.builder()
            .productId(cartItemEntity.getProductId())
            .quantity(cartItemEntity.getQuantity())
            .build());
    }
}
