package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
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
public class PersonDataService implements PersonService {

    private final PersonCrudRepository repository;

    @Override
    public Person save(Person person) {
        repository.save(person);
        return person;
    }

    @Override
    public void update(Person person) {
        repository.save(person);
    }

    @Override
    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }
}
