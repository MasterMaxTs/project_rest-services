package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.auth.domain.Person;

import java.util.List;
import java.util.Optional;

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

    /**
     * Находит пользователя в хранилище по login
     * @param login login пользователя
     * @return Optional.of(person), если пользователь найден,
     * иначе Optional.empty()
     */
    Optional<Person> findByLogin(String login);
}
