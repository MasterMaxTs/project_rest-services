package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

/**
 * Хранилище пользователей
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
}