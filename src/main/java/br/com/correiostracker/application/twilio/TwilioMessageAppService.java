package br.com.correiostracker.application.twilio;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import br.com.correiostracker.config.properties.TwilioProperties;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TwilioMessageAppService {
    
    private final TwilioProperties properties;

    public void sendMessage(String to, String text){
        Twilio.init(properties.getAccountSid(), properties.getAuthToken());
        Message.creator(new PhoneNumber(to), new PhoneNumber(properties.getFrom()),  text).create();
    }

}
