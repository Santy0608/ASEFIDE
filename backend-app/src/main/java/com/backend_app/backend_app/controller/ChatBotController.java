package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.ChatResponse;
import com.backend_app.backend_app.model.ChatRequest;
import com.backend_app.backend_app.service.ClaudeService;
import com.backend_app.backend_app.serviceImpl.ContextBuilderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ChatBotController {

    private final ClaudeService claudeService;

    private final ContextBuilderServiceImpl contextBuilderService;

    public ChatBotController(ClaudeService claudeService, ContextBuilderServiceImpl contextBuilderService){
        this.claudeService = claudeService;
        this.contextBuilderService = contextBuilderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ASOCIADO')")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request, Authentication auth){
        try{

            if(request.getMensaje() == null || request.getMensaje().isBlank()){
                return ResponseEntity.badRequest()
                        .body(ChatResponse.error("El mensaje no puede estar vacío"));
            }

            String promptSistema = contextBuilderService.buildSystemPrompt(auth);

            String respuesta = claudeService.chat(
                    promptSistema,
                    request.getMensaje(),
                    request.getHistorial()
            );
            return ResponseEntity.ok(ChatResponse.ok(respuesta));

        } catch (Exception e){
            System.out.print(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ChatResponse.error("El asistente no está disponible en este momento"));
        }
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChatResponse> chatAdmin(
            @RequestBody ChatRequest request,
            Authentication auth) {

        try {

            String promptSistema = contextBuilderService.buildSystemPrompt(auth);
            String respuesta = claudeService.chat(
                    promptSistema,
                    request.getMensaje(),
                    request.getHistorial()
            );

            return ResponseEntity.ok(ChatResponse.ok(respuesta));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ChatResponse.error("Error del asistente."));
        }
    }

}
