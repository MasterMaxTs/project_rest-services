package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;

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
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") int id) {
        var person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
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
}
