package br.com.correiostracker.infrastructure.correios.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseCorreiosDto {
    private List<CorreiosObjetoDto> objetos;
}
