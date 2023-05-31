package br.unitins.dto;

import br.unitins.model.EquipamentoOdontologico;

public record EquipamentoOdontologicoResponseDTO (
        Long id,
        String finalidade

) {
    public EquipamentoOdontologicoResponseDTO(EquipamentoOdontologico eq) {

        this(eq.getId(), eq.getFinalidade());
    }

}



