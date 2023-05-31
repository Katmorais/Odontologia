package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneDTO (
        @NotBlank(message = "O campo código Area deve ser informado.")
        @Size(max = 2, message = "O código Area deve posssuir 3 caracteres.")
        String codigoArea,

        @NotBlank(message = "O campo numero deve ser informado.")
        String numero
) {

}


