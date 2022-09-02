package br.com.correiostracker.application.tracker;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import br.com.correiostracker.application.twilio.TwilioMessageAppService;
import br.com.correiostracker.infrastructure.correios.HttpCorreiosService;
import br.com.correiostracker.infrastructure.correios.dto.CorreiosEventoDto;
import br.com.correiostracker.infrastructure.correios.dto.CorreiosObjetoDto;
import br.com.correiostracker.infrastructure.correios.dto.ResponseCorreiosDto;
import br.com.correiostracker.utils.StringUtils;
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
                String codeText = String.format("ℹ️ Código de rastreio: %s", correiosDto.getCodObjeto());
                String statusText = String.format("\n🤨 Status: %s", StringUtils.capitalizeFirstLetter(dto.getDescricao()));
                String placeText = String.format("\n📍 Unidade: %s", 
                    dto.getUnidade().getEndereco().getCidade() != null ? StringUtils.capitalizeFirstLetter(dto.getUnidade().getEndereco().getCidade()) : StringUtils.capitalizeFirstLetter(dto.getUnidade().getNome()));
                String dateTimeText = String.format("\n🕒 Data e Hora: %s",  String.format("%s às %s", 
                    dto.getDtHrCriado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    dto.getDtHrCriado().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

                if(dto.getUnidadeDestino() != null){
                    placeText += String.format(" para %s", dto.getUnidadeDestino().getEndereco().getCidade() != null ?  StringUtils.capitalizeFirstLetter(dto.getUnidadeDestino().getEndereco().getCidade()) :  StringUtils.capitalizeFirstLetter(dto.getUnidadeDestino().getNome()));
                }

                template = String.format("%s %s %s %s", codeText, statusText, placeText, dateTimeText);
            }
        }

        this.messageAppService.sendMessage(to, template);
    }

}
