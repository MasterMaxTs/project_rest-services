package ru.job4j.auth.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс используется для инициализации статических констант, используемых
 * в классах одноимённого пакета
 */
@Component
public class JWTConstants {

    /**
     * секретный ключ для генерации JWT
     */
    static String secret;

    /**
     * время жизни JWT
     */
    static long expirationTime;

    /**
     * токен префикс
     */
    static String tokenPrefix;

    /**
     * название заголовка
     */
    static String headerString;

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param secret секретный ключ для генерации JWT
     */
    @Value("${jwt.authentication.filter.secret}")
    public void setSecret(String secret) {
        JWTConstants.secret = secret;
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param expirationTime время жизни JWT
     */
    @Value("${jwt.authentication.filter.expiration-time}")
    public void setExpirationTime(String expirationTime) {
        JWTConstants.expirationTime = Long.parseLong(expirationTime);
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param tokenPrefix токен префикс
     */
    @Value("${jwt.authentication.filter.token-prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        JWTConstants.tokenPrefix = tokenPrefix;
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param headerString название заголовка
     */
    @Value("${jwt.authentication.filter.header-string}")
    public void setHeaderString(String headerString) {
        JWTConstants.headerString = headerString;
    }
}
