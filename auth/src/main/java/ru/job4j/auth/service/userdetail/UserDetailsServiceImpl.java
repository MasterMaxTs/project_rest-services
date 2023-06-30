package ru.job4j.auth.service.userdetail;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.person.PersonService;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Реализация сервиса загрузки деталей авторизованного пользователя в
 * SecurityContextHolder
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username)
                                            throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personService.findByLogin(username);
        if (optionalPerson.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Person person = optionalPerson.get();
        return new User(person.getLogin(), person.getPassword(), emptyList());
    }
}
