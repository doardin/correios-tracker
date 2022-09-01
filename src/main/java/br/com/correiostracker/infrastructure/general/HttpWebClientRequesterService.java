package br.com.correiostracker.infrastructure.general;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HttpWebClientRequesterService {
    
    private final WebClient webClient;

    public <T> T get(String url, Class<T> clazz) {
        try {
            UriSpec<?> uriSpec = this.webClient.get();
            RequestHeadersSpec<?> headersSpec = uriSpec.uri(url);
            ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve();
            Mono<T> monoResponse = responseSpec.bodyToMono(clazz);
            return monoResponse.block();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Erro ao realizar requisição para GET: " + url);
        }
    }


}
