package br.unitins.dto;

import br.unitins.model.Compra;
import java.time.LocalDate;

public record CompraResponseDTO (
        Long id,

        LocalDate data,

        Double totalCompra

) {
    public CompraResponseDTO(Compra compra) {
        this(compra.getId(), compra.getData(), compra.getTotalCompra()
        );
    }

}
