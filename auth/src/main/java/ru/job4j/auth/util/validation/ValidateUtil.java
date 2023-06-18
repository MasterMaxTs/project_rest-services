package ru.job4j.auth.util.validation;

import java.util.List;

/**
 * Утилитарный класс, содержащий методы валидации входящих данных
 */
public class ValidateUtil {

    public static final int REQUIRED_USERNAME_LENGTH = 6;
    public static final int REQUIRED_PASSWORD_LENGTH = 6;

    /**
     * Выполняет валидацию учётных данных пользователя
     * @param username username пользователя
     * @param password password пользователя
     */
    public static void validateUserCredential(String username, String password)
                        throws NullPointerException, IllegalArgumentException {
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty!");
        }
        if (username.length() < REQUIRED_USERNAME_LENGTH
                || password.length() < REQUIRED_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Invalid username or password!"
                            + " Username must not be less than %d characters"
                            + " or password must not be less than %d characters",
                    REQUIRED_USERNAME_LENGTH, REQUIRED_PASSWORD_LENGTH)
            );
        }
    }

    /**
     * Проверяет существование результирующих данных
     * @param resultingData результирующие данные
     * @return результат проверки в виде boolean
     * @param <T> тип данных на входе
     */
    public static <T> boolean checkEmptyResult(List<T> resultingData) {
        return resultingData.isEmpty();
    }
}
