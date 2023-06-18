package ru.job4j.auth.service.person;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonCrudRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
           log.error("User creation/update error."
                        + " The username = {} or email = {} address is already in"
                           + " use in the application. Enter other values!",
                        person.getLogin(), person.getEmail());
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
    public boolean partialUpdate(Person person)
                    throws InvocationTargetException, IllegalAccessException {
        Optional<Person> currentOptional = findById(person.getId());
        if (currentOptional.isEmpty()) {
            log.error("User with id={} partial update error! No found!", person.getId());
            throw new IllegalArgumentException("User partial update error! "
                                                    + "User no found!");
        }
        Person current = currentOptional.get();
        Method[] methods = current.getClass().getDeclaredMethods();
        Map<String, Method> namePerMethod = new HashMap<>();
        for (Method method
                : methods) {
            String name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (String name
                : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                Method getMethod = namePerMethod.get(name);
                Method setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    log.error("User with id={} partial update error!"
                            + "Impossible invoke set method from object : {} "
                            + "Check set and get pairs.", person.getId(), current);
                    throw new IllegalArgumentException(
                            String.format("User partial update error! "
                                    + "Impossible invoke set method from "
                                    + "object : %s . Check set and get pairs.",
                                    current));
                }
                Object newValue = getMethod.invoke(person);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        return save(current);
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
