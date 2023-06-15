package ru.job4j.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Класс используется для обработки определённого вида исключений,
 * которые могут возникнуть во всех контроллерах приложения
 */
@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    /**
     * Метод глобально отлавливает  и обрабатывает исключения типа
     * EmptyResultException, возникающих во всех контроллерах,
     * меняет статус и тело ответа
     * @param ex Exception
     * @param response  HttpServletResponse
     */
    @ExceptionHandler(value = {NullPointerException.class})
    public void handleException(Exception ex, HttpServletResponse response)
                                                            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of(
                                "message", "Some of fields empty",
                                "details", ex.getMessage()
                        ))
        );
        log.error(ex.getMessage());
    }
}
