package br.unitins.dto;

import br.unitins.model.Municipio;

public record MunicipioResponseDTO (
    Long id,
    String nome,

    EstadoResponseDTO estado

) {
    public MunicipioResponseDTO(Municipio municipio) {
            this(municipio.getId(), municipio.getNome(), new EstadoResponseDTO(municipio.getEstado()));
        }
}