package ru.job4j.auth;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
public class AuthApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AuthApplication.class);
	}

	@Bean
	public DataSource ds(@Value("${spring.datasource.driver-class-name}") String driver,
						 @Value("${spring.datasource.url}") String url,
						 @Value("${spring.datasource.username}") String username,
						 @Value("${spring.datasource.password}") String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public SpringLiquibase liquibase(DataSource ds,
									 @Value("${spring.liquibase.change-log}") String changeLogFile) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(ds);
		liquibase.setChangeLog(changeLogFile);
		return liquibase;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
