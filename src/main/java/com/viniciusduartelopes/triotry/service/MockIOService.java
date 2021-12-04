package com.viniciusduartelopes.triotry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viniciusduartelopes.triotry.configuration.ConfigurationSingleton;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MockIOService {

    private WebClient webClientToMockApi;

    @Autowired
    private ConfigurationSingleton configurationSingleton;

    @PostConstruct
    public void init() {
        initBaseWebClientToMockAPI();
    }

    private void initBaseWebClientToMockAPI() {
        if (webClientToMockApi == null) {
            webClientToMockApi = WebClient.builder()
                    .baseUrl(configurationSingleton.getMockAPIUrl())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
    }

    public List<ContactDTO> getContacts() {
        Mono<Object[]> response = webClientToMockApi.get()
                .uri("/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object[].class);

        Object[] objects = response.block();
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, ContactDTO.class))
                .collect(Collectors.toList());
    }

}
