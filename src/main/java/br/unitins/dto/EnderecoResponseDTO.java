package br.unitins.dto;

import br.unitins.model.Endereco;

import jakarta.validation.constraints.NotBlank;

public record EnderecoResponseDTO (

        Long id,
        @NotBlank(message = "O CEP deve ser informado.")

        String cep,
        @NotBlank(message = "O bairro deve ser informado.")
        String bairro,

        @NotBlank(message = "O número deve ser informado.")
        String numero,
        String complemento,

        Long idMunicipio

) {

    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId(), endereco.getCep(), endereco.getBairro(), endereco.getNumero(),
                endereco.getComplemento(), endereco.getId()
        );
    }
}

