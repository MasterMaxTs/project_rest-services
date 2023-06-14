package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;

/**
 * REST-Контроллер регистрации пользователей
 */
@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class RegistrationController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.save(person)
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
