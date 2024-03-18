package mart.mono.purchases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.cart.CartItemEntity;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchasesService {

    public static final String PURCHASE_EVENT = "purchaseEvent";
    private final PurchasesRepository purchasesRepository;
    private final StreamBridge streamBridge;

    public List<Purchase> getAll() {
        return purchasesRepository.findAll();
    }

    public boolean purchase(List<CartItemEntity> cartItems) {
        try {
            purchasesRepository.save(new Purchase(UUID.randomUUID(), cartItems));
            cartItems.forEach(cartItem -> {
                PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .build();
                log.info("Publishing Event {}", purchaseEvent);
                streamBridge.send(PURCHASE_EVENT, purchaseEvent);
            });
            return true;
        } catch (Exception e) {
            log.info("Nope", e);
            return false;
        }
    }
}
