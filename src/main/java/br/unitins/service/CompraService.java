package br.unitins.service;

import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import java.util.List;

public interface CompraService {
    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO compraDTO);

    CompraResponseDTO update(Long id, CompraDTO compraDTO);

    void delete(Long id);

    List<CompraResponseDTO> findByNome(String nome);

    long count();
}


