package com.example.fleamarket.services;

import com.example.fleamarket.models.User;
import com.example.fleamarket.models.enums.Role;
import com.example.fleamarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Логика регистрации пользователя
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    // Для шифрования паролей
    private final PasswordEncoder passwordEncoder;

    /**
     * Добавляет пользователя в систему
     *
     * @param user пользователь, которого необходимо добавить
     */
    public boolean createUser(User user) {
        // Электронная почта пользователя
        String userEmail = user.getEmail();

        /* Если пользователь обладает какой-нибудь
        электронной почтой, то возвращаем false
        (произошла ошибка) */
        if (userRepository.findByEmail(userEmail) != null) {
            return false;
        }

        user.setActive(true);
        // Зашифровать пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Установить роль (обычный пользователь)
        user.getRoles().add(Role.ROLE_USER);
        // Логирование данных
        log.info("Saving new User with email: {}", userEmail);
        // Сохранить пользователя
        userRepository.save(user);
        return true;
    }
}
