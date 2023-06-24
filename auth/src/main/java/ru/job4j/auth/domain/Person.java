package ru.job4j.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * Модель данных пользователь
 */
@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Login must be non null")
    @Size(min = 6, max = 15,
          message = "Login must be more 6 characters "
                    + "and less than 15 characters")
    private String login;

    @NotBlank(message = "Password must be non null")
    @Size(min = 6,
            message = "Password must be more 6 characters")
    private String password;

    @NotBlank(message = "Email must be non null")
    @Email(message = "Email must be correct")
    private String email;

    @NotBlank(message = "Gender must be non null")
    @Pattern(regexp = "^[fmFM]$",
            message = "Invalid gender value!"
                        + " Choose the correct values: f or m, case insensitive.")
    private String gender;

    @NotNull(message = "Date of birth must be non null")
    @Past(message = "Date of birth cannot be the current date"
                        + " or a date in the future")
    private Date birthday;
}
