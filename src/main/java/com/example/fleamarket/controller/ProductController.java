package com.example.fleamarket.controller;

import com.example.fleamarket.models.Product;
import com.example.fleamarket.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.fleamarket.services.ProductService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


/**
 * @author mikhail
 * Класс, предназначенный для непосредственной
 * обработки запросов от клиента и возвращения результатов
 */
@Controller
@RequiredArgsConstructor
public class ProductController {
    /**
     * Dependency Injection (внедрение зависимости)
     */
    private final ProductService productService;

    /**
     * Отображает список товаров или определенный товар
     *
     * @param title название товара
     * @param model данные приложения
     * @return шаблон products
     */
    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String title,
                           Principal principal, Model model) {
        model.addAttribute("products", productService.listProducts(title));
        // Передать пользователя в модель
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "products";
    }

    /**
     * Отображает информацию о товаре
     *
     * @param id    идентификатор товара
     * @param model данные приложения
     * @return шаблон product-info
     */
    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id,
                              Model model,
                              Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        // Отдельно отобразить товар
        model.addAttribute("product", productService.getProductById(id));
        // Отдельно отобразить изображения
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    /**
     * Добавляет товар с фотографиями
     *
     * @param product продукт, который необходимо добавить
     * @return перенаправление на главную страницу
     */
    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                Product product,
                                Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/my/products";
    }

    /**
     * Удаляет товар
     *
     * @param id идентификатор товара
     * @return перенаправление на главную страницу
     */
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        // Возвращаем представление
        return "my-products";
    }
}
