package mart.mono.config;

import mart.mono.product.ProductService;
import mart.mono.product.PurchaseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConfig {
    @Bean
    public Consumer<PurchaseEvent> purchaseEvent(ProductService service) {
        return service::processPurchaseEvent;
    }
}
