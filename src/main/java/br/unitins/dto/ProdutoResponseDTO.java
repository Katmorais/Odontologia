package br.unitins.dto;

import br.unitins.model.Produto;
import jakarta.validation.constraints.NotBlank;

public record ProdutoResponseDTO (
    Long id,

    @NotBlank(message = "O nome do produto deve ser informado.")
    String nome,
    String descricao,
    @NotBlank(message = "O estoque deve ser informado.")
    Integer estoque,

    @NotBlank(message = "O preço do produto deve ser informado.")
    Double preco

) {

    public ProdutoResponseDTO(Produto p) {
            this(p.getId(), p.getNome(), p.getDescricao(), p.getEstoque(), p.getPreco());
        }
    }



