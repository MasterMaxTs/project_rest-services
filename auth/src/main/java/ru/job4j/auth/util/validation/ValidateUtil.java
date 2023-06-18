package ru.job4j.auth.util.validation;

import ru.job4j.auth.domain.Person;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Утилитарный класс, содержащий методы валидации входящих данных
 */
public class ValidateUtil {

    public static final int REQUIRED_USERNAME_LENGTH = 6;
    public static final int REQUIRED_PASSWORD_LENGTH = 6;
    public static final String MALE_GENDER = "m";
    public static final String FEMALE_GENDER = "f";
    public static final String REGEX_FOR_VALID_EMAIL =
                                 "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";

    /**
     * Выполняет валидацию учётных данных пользователя
     * @param person  пользователь
     */
    public static void validateUserCredentials(Person person)
                        throws NullPointerException, IllegalArgumentException {
        String username = person.getLogin();
        String password = person.getPassword();
        String gender = person.getGender();
        String email = person.getEmail();
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
        if (!(MALE_GENDER.equalsIgnoreCase(gender)
                || FEMALE_GENDER.equalsIgnoreCase(gender))) {
            throw new IllegalArgumentException(
                    "Invalid gender value!"
                            + " Valid values: f or m, case insensitive."
            );
        }
        Pattern pattern = Pattern.compile(REGEX_FOR_VALID_EMAIL);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid email! Enter correct email."
            );
        }
    }

    /**
     * Проверяет существование результирующих данных
     * @param <T> тип данных на входе
     * @param resultingData список результирующих данных на входе
     * @return результат проверки в виде boolean
     */
    public static <T> boolean checkEmptyResult(List<T> resultingData) {
        return resultingData.isEmpty();
    }
}
