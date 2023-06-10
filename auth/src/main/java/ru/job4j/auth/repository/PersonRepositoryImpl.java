package ru.job4j.auth.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.auth.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация хранилища пользователей в памяти
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    /**
     * Хранилище в памяти в виде ConcurrentHashMap
     */
    private final Map<String, Person> persons = new ConcurrentHashMap<>();

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public Person save(Person person) {
        person.setId(atomicInteger.incrementAndGet());
        return persons.putIfAbsent(person.getLogin(), person);
    }

    @Override
    public boolean update(Person person) {
        return persons.replace(person.getLogin(), person) != null;
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return Optional.ofNullable(persons.get(login));
    }

    @Override
    public Optional<Person> findById(int id) {
        return persons.values()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        Optional<Person> optionalPerson = findById(id);
        if (optionalPerson.isPresent()) {
            rsl = persons.remove(optionalPerson.get().getLogin()) != null;
        }
        return rsl;
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }
}
