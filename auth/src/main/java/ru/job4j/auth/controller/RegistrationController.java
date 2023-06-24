package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;

import javax.validation.Valid;

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

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
