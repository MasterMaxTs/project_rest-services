package ru.job4j.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс DTO для редактирования учётных данных профиля пользователя
 */
@Data
public class UserCredentials {

    private int id;

    @NotBlank(message = "Password must be non null")
    @Size(min = 6,
            message = "Password must be more 6 characters")
    private String password;
}
