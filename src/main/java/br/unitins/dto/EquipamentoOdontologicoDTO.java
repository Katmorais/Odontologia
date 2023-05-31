package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipamentoOdontologicoDTO (
        @NotBlank(message = "O campo finalidade deve ser informado.")
        String finalidade

) {

}

