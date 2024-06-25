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


    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Long productId = product.getId();

        if (productId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (products.containsKey(productId)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        products.put(productId, product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>(products.values());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        if (!products.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updatedProduct.setId(id);
        products.put(id, updatedProduct);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!products.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        products.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

