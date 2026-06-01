package com.backend_app.backend_app;

import com.backend_app.backend_app.chatbot.ClaudeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(ClaudeProperties.class)
public class BackendAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendAppApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("123"));
	}

}
