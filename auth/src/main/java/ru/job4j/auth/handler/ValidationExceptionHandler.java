package ru.job4j.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс используется для обработки исключений при валидации моделей,
 * которые могут возникнуть во всех контроллерах приложения
 */
@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    /**
     * Метод глобально отлавливает  и обрабатывает исключения типа
     * MethodArgumentNotValidException, возникающих в контроллерах при валидации
     * моделей, меняет статус и тело ответа
     * @param ex MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(
                ex.getFieldErrors().stream()
                        .map(fe -> Map.of(
                                    fe.getField(),
                                    String.format("%s. Actual value: %s",
                                        fe.getDefaultMessage(),
                                        fe.getRejectedValue())
                        )).collect(Collectors.toList())
        );
    }
}
