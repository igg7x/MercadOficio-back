package com.example.demo.utils;

import java.text.ParseException;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String email = getEmailFromJWT(servletRequest); // Extrae el email del JWT
            System.out.println(email);
            if (email != null) {
                attributes.put("email", new UserEmailPrincipal(email)); // Asigna el Principal personalizado
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
    }

    private String getEmailFromJWT(HttpServletRequest request) throws ParseException {

        String token = request.getHeader("Authorization");
        System.out.println(token);
        JWT jwt = JWTParser.parse(token);
        return jwt.getJWTClaimsSet().getClaims().get("https://MercadOficio/email").toString();

    }

}
