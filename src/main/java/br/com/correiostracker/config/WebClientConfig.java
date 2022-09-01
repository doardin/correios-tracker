package br.com.correiostracker.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.correiostracker.config.properties.WebClientConfigProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

        private final WebClientConfigProperties properties;

        @Bean
        public WebClient webClient() {

                HttpClient httpClient = HttpClient.create(ConnectionProvider
                                .builder("aws-connection-provider")
                                .maxIdleTime(Duration.ofSeconds(300)) // must be less than 350 s
                                .build())
                                .compress(true)
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getTimeout())
                                .responseTimeout(Duration.ofMillis(properties.getTimeout()))
                                .doOnConnected(
                                                conn -> conn.addHandlerLast(new ReadTimeoutHandler(
                                                                properties.getTimeout(), TimeUnit.SECONDS))
                                                                .addHandlerLast(new WriteTimeoutHandler(
                                                                                properties.getTimeout(),
                                                                                TimeUnit.SECONDS)));

                WebClient client = WebClient.builder()
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                .exchangeStrategies(
                                                ExchangeStrategies.builder()
                                                                .codecs(configurer -> configurer
                                                                                .defaultCodecs()
                                                                                .maxInMemorySize(16 * 1024 * 1024))
                                                                .build())
                                .build();

                return client;
        }
}