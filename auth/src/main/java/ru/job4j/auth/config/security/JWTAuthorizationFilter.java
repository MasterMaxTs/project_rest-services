package ru.job4j.auth.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static ru.job4j.auth.config.security.JWTConstants.*;

/**
 * Фильтр, выполняющий процедуру авторизации.
 * Проверяет заголовок запроса на наличие в нём токена
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
                                            throws IOException, ServletException {
        String header = req.getHeader(headerString);
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        String user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                         .build()
                         .verify(header.replace(tokenPrefix, ""))
                         .getSubject();
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(
                         user, null, new ArrayList<>()
            );
        }
        return null;
    }
}
