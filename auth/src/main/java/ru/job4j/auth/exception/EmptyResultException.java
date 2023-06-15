package ru.job4j.auth.exception;

/**
 * Класс исключения в случае отсутствия результирующих данных
 */
public class EmptyResultException extends Exception {

    public EmptyResultException(String message) {
        super(message);
    }
}
