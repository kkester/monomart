package mart.mono.purchases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.cart.CartItemEntity;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchasesService {

    public static final String PURCHASE_EVENT = "purchaseEvent";
    private final PurchasesRepository purchasesRepository;
    private final StreamBridge streamBridge;

    public List<PurchaseEntity> getAll() {
        return purchasesRepository.findAll();
    }

    public void purchase(List<CartItemEntity> cartItems) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        List<PurchasedItemEntity> items = cartItems.stream()
            .map(cartItem -> PurchasedItemEntity.builder()
                .purchase(purchaseEntity)
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build())
            .toList();
        purchaseEntity.setItems(items);
        purchasesRepository.save(purchaseEntity);

        cartItems.forEach(cartItem -> {
            PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();
            log.info("Publishing Event {}", purchaseEvent);
            streamBridge.send(PURCHASE_EVENT, purchaseEvent);
        });
    }
}
