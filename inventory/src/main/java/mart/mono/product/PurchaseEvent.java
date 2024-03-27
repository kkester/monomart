package mart.mono.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEvent {
    private UUID purchaseId;
    private UUID productId;
    private Integer quantity;
}
