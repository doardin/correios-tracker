package br.com.correiostracker.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("spring.web-client.request")
@Data
public class WebClientConfigProperties {
    private Integer timeout;
}