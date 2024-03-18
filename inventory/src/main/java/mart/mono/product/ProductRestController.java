package mart.mono.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list(@RequestParam(value = "catalog", required = false) String catalogKey) {
        if (catalogKey == null) {
            return productService.getAll();
        }
        return productService.getForCatalog(catalogKey);
    }

    @GetMapping("/{id}")
    public Product list(@PathVariable UUID id) {
        return productService.getForProductId(id);
    }
}
