package com.example.fleamarket.models;

import com.example.fleamarket.models.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Пользователь системы
 */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Электронная почта (уникальное значение)
     */
    @Column(name = "email", unique = true)
    private String email;
    /**
     * Номер телефона (уникальное значение)
     */
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    /**
     * Имя пользователя
     */
    @Column(name = "name")
    private String name;

    /**
     * Подтверждение данных
     */
    @Column(name = "active")
    private boolean active;

    /**
     * Фотография
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;

    /**
     * Пароль (может иметь длину 1000 символов)
     */
    @Column(name = "password", length = 1000)
    private String password;
    /**
     * Список ролей
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    /**
     * Дата создания
     */
    private LocalDateTime dateOfCreated;

    /**
     * Инициализация данных
     */
    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    // Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
