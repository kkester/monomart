package mart.mono;

import mart.mono.product.Product;
import mart.mono.product.ProductRepository;
import mart.mono.product.ProductService;
import mart.mono.product.PurchaseEvent;
import net.bytebuddy.agent.VirtualMachine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void getAll() {
        productService.getAll();

        Mockito.verify(productRepository).findAll();
    }

    @Test
    void processPurchaseEventTest() throws Exception {
        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .productId(product.getId())
                .purchaseId(new UUID(123L, 456L))
                .quantity(10)
                .build();

        purchaseEvent.setProductId(product.getId());

        productService.processPurchaseEvent(purchaseEvent);

        assertThat(product.getQuantity()).isEqualTo(0);

    }
}
