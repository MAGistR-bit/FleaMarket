package com.example.fleamarket.repositories;

import com.example.fleamarket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Находит пользователя по e-mail
     * @param email электронная почта пользователя
     * @return пользователь, обладающий данной электронной почтой
     */
    User findByEmail(String email);
}
