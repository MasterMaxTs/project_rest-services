package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;
import ru.job4j.auth.util.validation.ValidateUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * REST-Контроллер регистрации пользователей
 */
@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Person person) {
        ValidateUtil.validateUserCredentials(person);
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.save(person)
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Метод отлавливает  и обрабатывает исключения типа
     * IllegalArgumentException, возникающих в контроллере,
     * меняет статус и тело ответа
     * @param ex Exception
     * @param response  HttpServletResponse
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    private void handleException(Exception ex, HttpServletResponse response)
                                                            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of("message", ex.getMessage(),
                                "type", ex.getClass()
                        ))
        );
        log.error(ex.getLocalizedMessage());
    }
}
