package br.unitins.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.model.Endereco;
import br.unitins.model.Municipio;
import br.unitins.repository.EnderecoRepository;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService{

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoResponseDTO> getAll() {
        List<Endereco> list = enderecoRepository.listAll();
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if(endereco == null){
            throw new NotFoundException("Não encontrou o endereco");
        }
        return new EnderecoResponseDTO(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        validar(enderecoDTO);

        Endereco entity = new Endereco();
        entity.setBairro(enderecoDTO.bairro());
        entity.setNumero(enderecoDTO.numero());
        entity.setComplemento(enderecoDTO.complemento());
        entity.setCep(enderecoDTO.cep());
        entity.setMunicipio(new Municipio());
        entity.getMunicipio().setId(enderecoDTO.idMunicipio());

        enderecoRepository.persist(entity);
        return new EnderecoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Endereco enderecoUpdate = enderecoRepository.findById(id);
        if (enderecoUpdate == null)
            throw new NotFoundException("Endereco não encontrado.");
        validar(enderecoDTO);

        enderecoUpdate.setBairro(enderecoDTO.bairro());
        enderecoUpdate.setNumero(enderecoDTO.numero());
        enderecoUpdate.setComplemento(enderecoDTO.complemento());
        enderecoUpdate.setCep(enderecoDTO.cep());

        enderecoRepository.persist(enderecoUpdate);
        return new EnderecoResponseDTO(enderecoUpdate);
    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecoRepository.deleteById(id);

    }

    @Override
    public List<EnderecoResponseDTO> findByNome(String nome) {
        List<Endereco> list = enderecoRepository.findByNome(nome);
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());

    }

    @Override
    public long count() {
        return enderecoRepository.count();
    }
}

