package br.unitins.service;
import java.util.List;

import br.unitins.dto.EquipamentoOdontologicoResponseDTO;
import br.unitins.dto.EquipamentoOdontologicoDTO;

public interface EquipamentoOdontologicoService {

    List<EquipamentoOdontologicoResponseDTO> getAll();

    EquipamentoOdontologicoResponseDTO findById(Long id);

    EquipamentoOdontologicoResponseDTO create(EquipamentoOdontologicoDTO equipamentoOdontologicoDTO);

    EquipamentoOdontologicoResponseDTO update(Long id, EquipamentoOdontologicoDTO equipamentoOdontologicoDTO);

    void delete(Long id);

    List<EquipamentoOdontologicoResponseDTO> findByNome(String nome);

    long count();
}


