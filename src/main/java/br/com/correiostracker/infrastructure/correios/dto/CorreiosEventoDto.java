package br.com.correiostracker.infrastructure.correios.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CorreiosEventoDto {
    private String codigo;
    private String descricao;
    private LocalDateTime dtHrCriado;
    private String tipo;
    private CorreiosUnidadeDto unidade;
    private CorreiosUnidadeDto unidadeDestino;
    private String urlIcone;
}
