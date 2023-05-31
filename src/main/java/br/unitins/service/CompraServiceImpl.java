package br.unitins.service;

import br.unitins.dto.CompraDTO;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.model.Compra;
import br.unitins.repository.CompraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {
    @Inject
    CompraRepository compraRepository;
    @Inject
    Validator validator;

    @Override
    public List<CompraResponseDTO> getAll() {
        List<Compra> list = compraRepository.listAll();
        return list.stream().map(CompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        Compra compra = compraRepository.findById(id);
        if(compra == null){
            throw new NotFoundException("Não encontrou a compra");
        }
        return new CompraResponseDTO(compra);
    }

    private void validar(CompraDTO compraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CompraDTO>> violations = validator.validate(compraDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public CompraResponseDTO create(CompraDTO compraDTO) throws ConstraintViolationException{
        validar(compraDTO);

        Compra entity = new Compra();
        entity.setData(compraDTO.date());
        entity.setTotalCompra(compraDTO.totalCompra());

        compraRepository.persist(entity);
        return new CompraResponseDTO(entity);
    }

    @Override
    @Transactional
    public CompraResponseDTO update(Long id, CompraDTO compraDTO) throws ConstraintViolationException {
        Compra compraUpdate = compraRepository.findById(id);
        if (compraUpdate == null)
            throw new NotFoundException("Compra não encontrado.");
        validar(compraDTO);

        compraUpdate.setData(compraDTO.date());
        compraUpdate.setTotalCompra(compraDTO.totalCompra());

        compraRepository.persist(compraUpdate);
        return new CompraResponseDTO(compraUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        compraRepository.deleteById(id);
    }


    @Override
    public List<CompraResponseDTO> findByNome(String nome) {
        List<Compra> list = compraRepository.findByNome(nome);
        return list.stream().map(CompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return compraRepository.count();
    }
}


