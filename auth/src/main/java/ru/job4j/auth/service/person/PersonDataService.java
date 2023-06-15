package ru.job4j.auth.service.person;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonCrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса пользователей
 */
@Service
@AllArgsConstructor
@Slf4j
public class PersonDataService implements PersonService {

    private final PersonCrudRepository repository;

    @Override
    public boolean save(Person person) {
        boolean rsl = false;
        try {
            repository.save(person);
            log.info("User with id={} created/updated successfully!", person.getId());
            rsl = true;
        } catch (RuntimeException ex) {
           log.error("User creation/update error. Invalid username={}!",
                        person.getLogin());
        }
        return rsl;
    }

    @Override
    public boolean update(Person person) {
        Optional<Person> optionalPerson = repository.findById(person.getId());
        if (optionalPerson.isPresent()) {
            return save(person);
        }
        log.error("User with id={} update error! No found!", person.getId());
        return false;
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        Optional<Person> optionalPerson = repository.findById(id);
        if (optionalPerson.isPresent()) {
            repository.deleteById(id);
            log.info("User with id={} deleted successfully.", id);
            rsl = true;
        }
        log.error("User with id={} delete error!", id);
        return rsl;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }
}
