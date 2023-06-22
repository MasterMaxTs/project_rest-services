package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.UserCredentials;
import ru.job4j.auth.service.person.PersonService;
import ru.job4j.auth.util.validation.ValidateUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<List<Person>> findAll() {
        List<Person> persons = personService.findAll();
        if (ValidateUtil.checkEmptyResult(persons)) {
            throw new ResponseStatusException(HttpStatus.OK, "Return result is empty!");
        }
        return ResponseEntity.ok(persons);

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
    public ResponseEntity<Void> update(@RequestBody Person person) {
        ValidateUtil.validateUserCredentials(person);
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.update(person)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/")
    public ResponseEntity<Void> partialUpdate(@RequestBody UserCredentials
                                                          userCredentials) {
        ResponseEntity<Person> response = findById(userCredentials.getId());
        Person person = response.getBody();
        person.setPassword(userCredentials.getPassword());
        return update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return personService.deleteById(id)
                ? ResponseEntity.ok().build()
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
    public void handleException(Exception ex, HttpServletResponse response)
                                                            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of("message", ex.getMessage(),
                                "type", ex.getClass())
                ));
    }
}
