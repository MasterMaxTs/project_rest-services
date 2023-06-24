package ru.job4j.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
     * DataIntegrityViolationException, возникающих в контроллерах,
     * меняет статус и тело ответа
     * @param ex Exception
     * @param response HttpServletResponse
     */
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex, HttpServletResponse response)
                                                             throws IOException {
        String errorMessage = "User creation/update error."
                                    + " The username or email address is already in"
                                    + " use in the application. Enter other values!";
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of("message", errorMessage,
                                "type", ex.getClass())
                ));
        log.error(ex.getLocalizedMessage());
    }
}
