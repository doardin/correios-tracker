package br.com.correiostracker.application.tracker;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import br.com.correiostracker.application.twilio.TwilioMessageAppService;
import br.com.correiostracker.infrastructure.correios.HttpCorreiosService;
import br.com.correiostracker.infrastructure.correios.dto.CorreiosEventoDto;
import br.com.correiostracker.infrastructure.correios.dto.CorreiosObjetoDto;
import br.com.correiostracker.infrastructure.correios.dto.ResponseCorreiosDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TrackerApplicationService {
    
    private final HttpCorreiosService correiosService;
    private final TwilioMessageAppService messageAppService;

    public void handlerMessage(String code, String to){
        ResponseCorreiosDto response = this.correiosService.getPackageInfo(code);
        CorreiosObjetoDto correiosDto = response.getObjetos().stream().findFirst().orElse(null);

        String template = "Objeto não encontrado na base de dados dos Correios. 😭😭";

        if(correiosDto.getMensagem() == null){
            CorreiosEventoDto dto = correiosDto.getEventos().stream().findFirst().orElse(null);

            if(dto != null){
                template = String.format(
                    "ℹ️ Código de rastreio: %s \n🤨 Status: %s \n📍 Unidade: %s \n🕒 Data e Hora: %s", 
                        correiosDto.getCodObjeto(), 
                        dto.getDescricao(),
                        dto.getUnidade().getEndereco().getCidade(),
                        String.format("%s ás %s", 
                            dto.getDtHrCriado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            dto.getDtHrCriado().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            }
        }

        this.messageAppService.sendMessage(to, template);
    }

}
