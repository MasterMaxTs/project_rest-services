package ru.job4j.auth.controller;

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

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> update(@Valid @RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.update(person)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("User with id=%d is not found!", person.getId()));
    }

    @PatchMapping("/")
    public ResponseEntity<?> partialUpdate(@Valid @RequestBody UserCredentials
                                                          userCredentials) {
        ResponseEntity<Person> response = findById(userCredentials.getId());
        Person person = response.getBody();
        person.setPassword(userCredentials.getPassword());
        return update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return personService.deleteById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
