package mart.mono.services;

import lombok.AllArgsConstructor;
import mart.mono.models.CartItem;
import mart.mono.models.Product;
import mart.mono.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private PurchasesService purchasesService;

    public List<CartItem> get() {
        return cartRepository.findAll();
    }

    public CartItem add(Product product) {
        return cartRepository.save(CartItem.builder()
                .product(product)
                .quantity(1)
                .build());
    }

    public void remove(UUID cartItemId) {
        Optional<CartItem> cartItem = cartRepository.findById(cartItemId);
        cartItem.ifPresent(item -> cartRepository.delete(item));
    }

    public void checkOut() {
        List<CartItem> cartItemEntities = cartRepository.findAll();
        purchasesService.purchase(cartItemEntities);
        cartRepository.deleteAll();
    }
}
