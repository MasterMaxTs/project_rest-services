package ru.job4j.auth.service.person;

import ru.job4j.auth.domain.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис пользователей
 */
public interface PersonService {

    /**
     * Сохраняет пользователя в хранилище
     * @param person пользователь
     * @return возвращает результат операции в виде boolean
     */
    boolean save(Person person);

    /**
     * Обновляет данные о пользователе в хранилище
     * @param person пользователь
     * @return возвращает результат операции в виде boolean
     */
    boolean update(Person person);

    /**
     * Находит пользователя в хранилище по login
     * @param login login пользователя
     * @return Optional.of(person), если пользователь найден,
     * иначе Optional.empty()
     */
    Optional<Person> findByLogin(String login);

    /**
     * Находит пользователя в хранилище по id
     * @param id идентификатор пользователя
     * @return Optional.of(person), если пользователь найден,
     * иначе Optional.empty()
     */
    Optional<Person> findById(int id);

    /**
     * Производит удаление пользователя в хранилище по id
     * @param id идентификатор пользователя
     * @return возвращает результат операции в виде boolean
     */
    boolean deleteById(int id);

    /**
     * Находит всех пользователей в хранилище
     * @return список пользователей
     */
    List<Person> findAll();
}
