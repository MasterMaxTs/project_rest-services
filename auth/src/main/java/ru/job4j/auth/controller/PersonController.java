package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.util.validation.ValidateUtil;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.exception.EmptyResultException;
import ru.job4j.auth.service.person.PersonService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * REST-Контроллер пользователей
 */
@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    /**
     * зависимость от сервиса пользователей
     */
    private final PersonService personService;

    /**
     * зависимость от BCryptPasswordEncoder
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * зависимость от ObjectMapper
     */
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public List<Person> findAll() throws EmptyResultException {
        return ValidateUtil.checkEmptyResult(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") int id) {
        var person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("User with id=%d is not found!", id)
                )),
                HttpStatus.OK
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Map<String, String> body) {
        var username = body.get("login");
        var password = body.get("password");
        ValidateUtil.validateUserCredential(username, password);
        Person person = new Person();
        person.setLogin(username);
        person.setPassword(encoder.encode(password));
        return personService.update(person)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return personService.deleteById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Метод отлавливает  и обрабатывает исключения типа
     * EmptyResultException, возникающих в контроллере,
     * меняет статус и тело ответа
     * @param ex Exception
     * @param response  HttpServletResponse
     */
    @ExceptionHandler(value = {EmptyResultException.class})
    public void handleException(Exception ex, HttpServletResponse response)
                                                            throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of("message", ex.getMessage(),
                                "type", ex.getClass())
                ));
    }
}
