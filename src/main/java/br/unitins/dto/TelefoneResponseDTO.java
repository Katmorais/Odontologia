package br.unitins.dto;

import br.unitins.model.Telefone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneResponseDTO (
        Long id,
        @NotBlank(message = "O DDD deve ser informado.")
        @Size(max = 3, message = "O cep deve posssuir 3 caracteres.")
        String codigoArea,

        @NotBlank(message = "O campo numero deve ser informado.")
        @Size(max = 9, message = "O cep deve posssuir 9 caracteres.")
        String numero

) {
    public TelefoneResponseDTO(Telefone telefone){
        this(telefone.getId(), telefone.getCodigoArea(), telefone.getNumero());
    }

}



