package br.com.correiostracker.infrastructure.correios.dto;

import lombok.Data;

@Data
public class CorreiosUnidadeDto {
    private CorreiosEnderecoDto endereco;
    private String tipo;
    private String nome;
    private String codSro;
}
