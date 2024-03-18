package mart.mono.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getForCatalog(String catalogKey) {
        return productRepository.findByCatalogId(catalogKey);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getForProductId(UUID id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void processPurchaseEvent(PurchaseEvent purchaseEvent) {
        log.info("Processing Event {}", purchaseEvent);
        Optional<Product> product = productRepository.findById(purchaseEvent.getProductId());
        if(product.isPresent()) {
            Product updatedProduct = product.get();
            int currentQuantity = product.get().getQuantity();
            int newQuantity = currentQuantity - purchaseEvent.getQuantity();

            updatedProduct.setQuantity(newQuantity);
            productRepository.save(updatedProduct);
        }
    }
}
