package ru.job4j.auth.dto;

import lombok.Data;

/**
 * Класс DTO для редактирования учётных данных профиля пользователя
 */
@Data
public class UserCredentials {

    private int id;

    private String password;
}
