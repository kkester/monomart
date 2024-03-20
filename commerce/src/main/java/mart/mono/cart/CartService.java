package mart.mono.cart;

import lombok.AllArgsConstructor;
import mart.mono.purchases.PurchasesService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private PurchasesService purchasesService;
    private RestClient restClient;

    public List<CartItem> get() {
        return cartRepository.findAll().stream()
            .map(cartItemEntity -> cartItemEntity.toCartItem(
                restClient.get()
                    .uri("/api/products/{0}", cartItemEntity.getProductId())
                    .retrieve()
                    .body(Product.class)))
            .toList();
    }

    public CartItem add(Product product) {
        return cartRepository.save(CartItemEntity.builder()
                .productId(product.getId())
                .quantity(1)
                .build())
            .toCartItem(product);
    }

    public void remove(UUID cartItemId) {
        Optional<CartItemEntity> cartItem = cartRepository.findById(cartItemId);
        cartItem.ifPresent(item -> cartRepository.delete(item));
    }

    public void checkOut() {
        List<CartItemEntity> cartItemEntities = cartRepository.findAll();
        purchasesService.purchase(cartItemEntities);
        cartRepository.deleteAll();
    }
}
