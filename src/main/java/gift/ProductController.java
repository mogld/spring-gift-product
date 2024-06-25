package gift;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final Map<Long, Product> products = new HashMap<>();
    private long currentId = 0;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        currentId++;
        product.setId(currentId);
        products.put(currentId, product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>(products.values());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


   

}

