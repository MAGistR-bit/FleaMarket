package com.example.fleamarket.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**
     * Идентификатор товара, который генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * Название товара
     */
    @Column(name = "title")
    private String title;
    /**
     * Описание товара, не имеющее ограничений по символам.
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;
    /**
     * Цена
     */
    @Column(name = "price")
    private int price;
    /**
     * Город
     */
    @Column(name = "city")
    private String city;
    /**
     * Автор публикации
     */
    @Column(name = "author")
    private String author;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    /**
     * Дата создания
     */
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    /**
     * Добавляет фотографию товара
     */
    public void addImageToProduct(Image image){
        // Текущий товар
        image.setProduct(this);
        // Добавляем фотографию в коллекцию
        images.add(image);
    }
}
