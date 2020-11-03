package com.restdeveloper.app.ws.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;


@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer  {

    @Override
    public void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
/*
                .anyMessage().authenticated();

        messages.simpDestMatchers("/app/**", "/topic/poll/**").authenticated()
                //.nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
                //.simpDestMatchers("/app/**)").hasRole("USER")
                .simpSubscribeDestMatchers("/user/**", "/topic/poll/**").authenticated();




 */


        .simpDestMatchers("/topic/**").permitAll();

    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
