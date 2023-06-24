package ru.job4j.auth.util.validation;

import java.util.List;

/**
 * Утилитарный класс, содержащий методы валидации входящих данных
 */
public class ValidateUtil {

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
