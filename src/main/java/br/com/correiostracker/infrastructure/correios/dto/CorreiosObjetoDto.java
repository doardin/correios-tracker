package br.com.correiostracker.infrastructure.correios.dto;

import java.util.List;

import lombok.Data;

@Data
public class CorreiosObjetoDto {
    private String codObjeto;
    private String mensagem;
    private List<CorreiosEventoDto> eventos;
}
