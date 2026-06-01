package com.backend_app.backend_app.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDataAccessException(DataAccessException ex) {

        String mensaje = ex.getMostSpecificCause().getMessage();

        if (mensaje != null && mensaje.contains("ORA-20")) {
            String mensajeLimpio = mensaje.substring(mensaje.indexOf(":") + 1).trim();

            Map<String, String> error = new HashMap<>();
            error.put("mensaje", mensajeLimpio);
            return ResponseEntity.badRequest().body(error);
        }


        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Error inesperado en el servidor");
        return ResponseEntity.internalServerError().body(error);
    }

}
