package com.backend_app.backend_app.auth.filter;

import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.model.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend_app.backend_app.auth.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String nombreUsuario = null;
        String contrasenia = null;

        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            nombreUsuario = loginRequest.getNombreUsuario();
            contrasenia = loginRequest.getContrasenia();
            System.out.println("Intentando login con: " + loginRequest.getNombreUsuario());
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nombreUsuario,
                contrasenia);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String nombreUsuario = user.getUsername();

        List<String> authoritiesList = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        boolean isAdmin = authoritiesList.contains("ROLE_ADMIN");
        boolean isAsociado = authoritiesList.contains("ROLE_ASOCIADO");

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(authoritiesList)) // Ahora es un simple ["ROLE_ADMIN"]
                .add("nombreUsuario", nombreUsuario)
                .add("isAdmin", isAdmin)
                .add("isAsociado", isAsociado)
                .build();

        String jwt = Jwts.builder()
                .subject(nombreUsuario)
                .claims(claims)
                .signWith(SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jwt);

        Map<String, Object> body = new HashMap<>(); // Cambia String por Object para meter booleanos
        body.put("token", jwt);
        body.put("nombreUsuario", nombreUsuario);
        body.put("isAdmin", isAdmin);
        body.put("isAsociado", isAsociado);
        body.put("authorities", authoritiesList);
        body.put("message", String.format("Hola %s has iniciado sesion con exito", nombreUsuario));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion con username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }

}
