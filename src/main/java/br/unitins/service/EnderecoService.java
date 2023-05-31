package br.unitins.service;

import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {
    List<EnderecoResponseDTO> getAll();

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO enderecoDTO);

    EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO);
    void delete(Long id);

    List<EnderecoResponseDTO> findByNome(String bairro);

    long count();

}
