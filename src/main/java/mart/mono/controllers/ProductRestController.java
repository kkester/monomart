package mart.mono.controllers;

import mart.mono.models.Product;
import mart.mono.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
