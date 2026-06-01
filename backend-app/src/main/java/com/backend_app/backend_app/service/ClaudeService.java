package com.backend_app.backend_app.service;

import com.backend_app.backend_app.model.ChatRequest;

import java.util.List;

public interface ClaudeService {

    public String chat (String systemPrompt, String usuarioMensaje, List<ChatRequest.MensajeHistorial> historial);

    


}

