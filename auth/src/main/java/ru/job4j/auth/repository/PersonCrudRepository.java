package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

import java.util.List;

/**
 * Хранилище пользователей
 */
public interface PersonCrudRepository extends CrudRepository<Person, Integer> {

    /**
     * Получает список всех пользователей
     * @return список пользователей
     */
    @Query("from Person")
    List<Person> findAll();
}
