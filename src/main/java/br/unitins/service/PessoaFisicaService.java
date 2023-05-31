package br.unitins.service;

import br.unitins.dto.PessoaFisicaDTO;
import br.unitins.dto.PessoaFisicaResponseDTO;

import java.util.List;

public interface PessoaFisicaService {

    // recursos basicos
    List<PessoaFisicaResponseDTO> getAll();

    PessoaFisicaResponseDTO findById(Long id);

    PessoaFisicaResponseDTO create(PessoaFisicaDTO pessoaFisicaDTO);

    PessoaFisicaResponseDTO update(Long id, PessoaFisicaDTO pessoaFisicaDTO);

    void delete(Long id);

    // recursos extras

    List<PessoaFisicaResponseDTO> findByNome(String nome);

    long count();

}