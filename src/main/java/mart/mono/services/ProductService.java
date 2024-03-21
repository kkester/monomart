package mart.mono.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.models.Product;
import mart.mono.repositories.ProductRepository;
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

    public void decrementProductQuantity(UUID productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            Product updatedProduct = product.get();
            int currentQuantity = product.get().getQuantity();
            int newQuantity = currentQuantity - quantity;

            updatedProduct.setQuantity(newQuantity);
            productRepository.save(updatedProduct);
        }
    }
}
