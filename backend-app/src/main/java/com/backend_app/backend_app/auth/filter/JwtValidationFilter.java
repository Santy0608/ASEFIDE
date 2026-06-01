package com.backend_app.backend_app.auth.filter;

import com.backend_app.backend_app.auth.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.backend_app.backend_app.auth.TokenJwtConfig.*;


public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String nombreUsuario = claims.getSubject();
            // String username2 = (String) claims.get("username");
            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> roles = Arrays.stream(
                            new ObjectMapper().readValue(authoritiesClaims.toString().getBytes(), String[].class)
                    )
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    nombreUsuario,
                    null,
                    roles);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token es invalido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(CONTENT_TYPE);
        }

    }
}
