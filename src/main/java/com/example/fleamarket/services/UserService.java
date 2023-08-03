package com.example.fleamarket.services;

import com.example.fleamarket.models.User;
import com.example.fleamarket.models.enums.Role;
import com.example.fleamarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Получает пользователей, зарегистрированных в системе
     *
     * @return список пользователей
     */
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Блокирует пользователя (запрещает вход в систему)
     *
     * @param id идентификатор пользователя
     */
    public void banUser(Long id) {
        // Получить пользователя по идентификатору
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}",
                        user.getId(),
                        user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}",
                        user.getId(),
                        user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        // Преобразовать роли в строковый вид
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
}
