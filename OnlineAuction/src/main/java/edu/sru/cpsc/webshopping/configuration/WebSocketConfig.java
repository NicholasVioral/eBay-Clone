package edu.sru.cpsc.webshopping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import jakarta.servlet.ServletContext;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private ServletContext servletContext;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Prefix for messages from server to client
        config.setApplicationDestinationPrefixes(servletContext.getContextPath() + "/app"); // Prefix for messages from client to server
    }
    

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String websocketPath = "/websocket-endpoint";
        registry.addEndpoint(websocketPath)
                .setAllowedOriginPatterns("*") // This allows all cross-origin requests. Lock this down in a production environment!
                .withSockJS();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192); // Set max text message buffer size, default is 8192
        container.setMaxBinaryMessageBufferSize(8192); // Set max binary message buffer size, default is 8192
        container.setMaxSessionIdleTimeout(60000L); // Set max session idle timeout in milliseconds, default is null
        return container;
    }
}
