package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;

import java.util.Optional;

/**
 * REST-Контроллер регистрации пользователей
 */
@RestController
@RequestMapping("/person")
public class RegistrationController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;

    public RegistrationController(@Qualifier("personServiceImpl")
                                  PersonService personService,
                                  BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Person person) {
        Optional<Person> optionalPerson =
                personService.findByLogin(person.getLogin());
        if (optionalPerson.isEmpty()) {
            person.setPassword(encoder.encode(person.getPassword()));
            personService.save(person);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
