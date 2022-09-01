package br.com.correiostracker.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("services.twilio")
@Data
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String from;
}
