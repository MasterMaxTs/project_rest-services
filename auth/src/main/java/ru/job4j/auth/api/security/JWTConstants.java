package ru.job4j.auth.api.security;

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
    static String SECRET;

    /**
     * время жизни JWT
     */
    static long EXPIRATION_TIME;

    /**
     * токен префикс
     */
    static String TOKEN_PREFIX;

    /**
     * название заголовка
     */
    static String HEADER_STRING;

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param secret секретный ключ для генерации JWT
     */
    @Value("${jwt.authentication.filter.secret}")
    public void setSECRET(String secret) {
        JWTConstants.SECRET = secret;
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param expirationTime время жизни JWT
     */
    @Value("${jwt.authentication.filter.expiration-time}")
    public void setExpirationTime(String expirationTime) {
        JWTConstants.EXPIRATION_TIME = Long.parseLong(expirationTime);
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param tokenPrefix токен префикс
     */
    @Value("${jwt.authentication.filter.token-prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        JWTConstants.TOKEN_PREFIX = tokenPrefix;
    }

    /**
     * Инициализация статической константы
     * значением из файла application.properties
     * @param headerString название заголовка
     */
    @Value("${jwt.authentication.filter.header-string}")
    public void setHeaderString(String headerString) {
        JWTConstants.HEADER_STRING = headerString;
    }
}
