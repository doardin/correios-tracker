package br.com.correiostracker.infrastructure.correios.dto;

import lombok.Data;

@Data
public class CorreiosUnidadeDto {
    private CorreiosEnderecoDto endereco;
    private String tipo;
}
