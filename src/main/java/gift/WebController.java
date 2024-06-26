package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProductController productController;

    @GetMapping("/products")
    public String viewProductPage(Model model) {
        List<Product> products = productController.getAllProducts().getBody();
        model.addAttribute("products", products);
        return "product/index";
    }

    @GetMapping("/products/new")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/new";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productController.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productController.getAllProducts().getBody()
                .stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("product", product);
        return "product/edit";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        productController.updateProduct(id, product);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productController.deleteProduct(id);
        return "redirect:/products";
    }
}
