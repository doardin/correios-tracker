package br.com.correiostracker.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("services.correios")
@Data
public class CorreiosProperties {
    private String url;
}
