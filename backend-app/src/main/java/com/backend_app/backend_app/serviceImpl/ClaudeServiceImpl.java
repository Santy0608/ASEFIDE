package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.chatbot.ClaudeProperties;
import com.backend_app.backend_app.model.ChatRequest;
import com.backend_app.backend_app.service.ClaudeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class ClaudeServiceImpl implements ClaudeService {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(ClaudeServiceImpl.class);

    private final WebClient claudeWebClient;
    private final ClaudeProperties props;
    private final ObjectMapper objectMapper;

    public ClaudeServiceImpl(WebClient claudeWebClient,
                             ClaudeProperties props,
                             ObjectMapper objectMapper) {
        this.claudeWebClient = claudeWebClient;
        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public String chat(String systemPrompt,
                       String usuarioMensaje,
                       List<ChatRequest.MensajeHistorial> historial) {
        try {
            List<Map<String, Object>> mensajes = new ArrayList<>();

            // ✅ Historial con "role" y "content" correctos
            if (historial != null && !historial.isEmpty()) {
                historial.stream()
                        .filter(h -> h.getRol() != null && h.getContenido() != null)
                        .filter(h -> !h.getRol().isBlank() && !h.getContenido().isBlank())
                        .forEach(h -> mensajes.add(
                                Map.of(
                                        "role", h.getRol(),      // ✅ "role" no "rol"
                                        "content", h.getContenido() // ✅ "content" no "contenido"
                                )
                        ));
            }

            // ✅ Mensaje actual del usuario — rol siempre "user"
            mensajes.add(Map.of(
                    "role", "user",       // ✅ siempre "user", nunca "asociado"
                    "content", usuarioMensaje
            ));

            // ✅ System prompt con cache
            List<Map<String, Object>> systemContent = List.of(
                    Map.of(
                            "type", "text",
                            "text", systemPrompt,
                            "cache_control", Map.of("type", "ephemeral")
                    )
            );

            // ✅ Body correcto
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("model", props.getModel());
            body.put("max_tokens", props.getMaxTokens());
            body.put("system", systemContent);
            body.put("messages", mensajes);

            String responseJson = claudeWebClient.post()
                    .header("anthropic-beta", "prompt-caching-2024-07-31")
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            res -> res.bodyToMono(String.class)
                                    .map(err -> new RuntimeException("Claude 4xx: " + err))
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            res -> res.bodyToMono(String.class)
                                    .map(err -> new RuntimeException("Claude 5xx: " + err))
                    )
                    .bodyToMono(String.class)
                    .block();

            return extractText(responseJson);

        } catch (Exception e) {
            log.error("=== Error COMPLETO Claude API ===", e);
            throw new RuntimeException("Error al comunicarse con el asistente", e);
        }
    }

    @SuppressWarnings("unchecked")
    private String extractText(String json) throws Exception {
        Map<String, Object> response = objectMapper.readValue(json, Map.class);

        if (response.containsKey("error")) {
            Map<String, Object> error = (Map<String, Object>) response.get("error");
            throw new RuntimeException("Claude error: " + error.get("message"));
        }

        List<Map<String, Object>> content =
                (List<Map<String, Object>>) response.get("content");

        if (content != null && !content.isEmpty()) {
            if (response.containsKey("usage")) {
                Map<String, Object> usage = (Map<String, Object>) response.get("usage");
                log.info("Tokens — entrada: {} | salida: {} | cache_hit: {}",
                        usage.get("input_tokens"),
                        usage.get("output_tokens"),
                        usage.getOrDefault("cache_read_input_tokens", 0)
                );
            }
            return (String) content.get(0).get("text");
        }

        return "No se pudo obtener respuesta del asistente.";
    }
}

