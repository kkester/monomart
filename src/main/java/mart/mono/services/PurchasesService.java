package mart.mono.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mart.mono.models.CartItem;
import mart.mono.models.Purchase;
import mart.mono.models.PurchasedItem;
import mart.mono.repositories.PurchasesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchasesService {

    public static final String PURCHASE_EVENT = "purchaseEvent";
    private final PurchasesRepository purchasesRepository;
    private final ProductService productService;

    public List<Purchase> getAll() {
        return purchasesRepository.findAll();
    }

    public void purchase(List<CartItem> cartItems) {
        cartItems.forEach(cartItem -> productService.decrementProductQuantity(
            cartItem.getProduct().getId(),
            cartItem.getQuantity()));

        Purchase purchase = new Purchase();
        List<PurchasedItem> items = cartItems.stream()
            .map(cartItem -> PurchasedItem.builder()
                .purchase(purchase)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build())
            .toList();
        purchase.setItems(items);
        purchasesRepository.save(purchase);
    }
}
