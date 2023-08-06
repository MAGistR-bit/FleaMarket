package com.example.fleamarket.services;

import com.example.fleamarket.models.Image;
import com.example.fleamarket.models.Product;
import com.example.fleamarket.models.User;
import com.example.fleamarket.repositories.ProductRepository;
import com.example.fleamarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Сервисные компоненты, которые содержат всю бизнес-логику.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    /**
     * Dependency Injection (внедрение зависимости)
     */
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Отображает товар по id, либо
     * выводит весь список товаров
     *
     * @param title название товара
     * @return определенный товар или список товаров
     */
    public List<Product> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    /**
     * Сохраняет товар
     *
     * @param product товар, который необходимо сохранить
     */
    public void saveProduct(Principal principal,
                            Product product,
                            MultipartFile file1,
                            MultipartFile file2,
                            MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1, image2, image3;
        if (file1.getSize() != 0) {
            // Выполнить преобразование
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            // Добавить фотографию
            product.addImageToProduct(image1);

        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }

        // Логирование
        log.info("Saving new Product. Title: {}; Author e-mail: {}",
                product.getTitle(), product.getUser().getEmail());

        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    /**
     * Преобразует MultipartFile (для загрузки файла) в "сущность"
     *
     * @param file файл, который необходимо загрузить
     * @return объект Image
     * @throws IOException исключение (возможна проблема ввода-вывода)
     */
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    /**
     * Удаляет товар
     *
     * @param id идентификатор товара, который будет удален
     */
    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

    /**
     * Получает товар по идентификатору
     *
     * @param id идентификатор товара, который необходимо получить
     * @return товар найден или не найден (null)
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
