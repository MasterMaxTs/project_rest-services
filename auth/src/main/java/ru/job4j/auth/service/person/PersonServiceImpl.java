package ru.job4j.auth.service.person;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса пользователей
 */
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public Person save(Person person) {
        return repository.save(person);
    }

    @Override
    public boolean update(Person person) {
        boolean rsl = false;
        Optional<Person> optionalPerson = repository.findById(person.getId());
        if (optionalPerson.isPresent()) {
            repository.save(person);
            rsl = true;
        }
        return rsl;
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
            rsl = true;
        }
        return rsl;
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }
}
