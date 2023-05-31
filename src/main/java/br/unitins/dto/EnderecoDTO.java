package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO (
    @NotBlank(message = "O campo CEP deve ser informado.")
    String cep,

    @NotBlank(message = "O campo bairro deve ser informado.")
    String bairro,

    @NotBlank(message = "O campo numero deve ser informado.")
    String numero,

    @NotBlank(message = "O campo complemento deve ser informado.")
    String complemento,

    Long idMunicipio

) {

}
