package com.tutorial.gatewayservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/")
    public Mono<String> index(WebSession session) {
        return Mono.just(session.getId());
    }

    @GetMapping("/token")
    public Mono<String> getToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
        return Mono.just(client.getAccessToken().getTokenValue());
    }

    @GetMapping("/userinfo")
    public Mono<Map> getUserInfo(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
        return webClientBuilder.build()
                .get()
                .uri(client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(headers -> headers.setBearerAuth(client.getAccessToken().getTokenValue()))
                .retrieve()
                .bodyToMono(Map.class);
    }
}
