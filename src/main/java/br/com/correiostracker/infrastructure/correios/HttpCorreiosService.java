package br.com.correiostracker.infrastructure.correios;

import org.springframework.stereotype.Service;

import br.com.correiostracker.config.properties.CorreiosProperties;
import br.com.correiostracker.infrastructure.correios.dto.ResponseCorreiosDto;
import br.com.correiostracker.infrastructure.general.HttpWebClientRequesterService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HttpCorreiosService {
    
    private final HttpWebClientRequesterService service;
    private final CorreiosProperties properties;

    public ResponseCorreiosDto getPackageInfo(String code){
        String url = String.format("%s/%s", properties.getUrl(), code);
        ResponseCorreiosDto response = service.get(url, ResponseCorreiosDto.class);
        return response;
    }

}
