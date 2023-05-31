package br.unitins.dto;

import java.time.LocalDate;
public record CompraDTO (
    LocalDate date,

    Double totalCompra

) {

}
