package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO (
        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,

        @NotBlank(message = "O campo descricao deve ser informado.")
        String descricao,


        Integer estoque,

        @NotBlank(message = "O campo preco deve ser informado.")
        Double preco

) {

}
