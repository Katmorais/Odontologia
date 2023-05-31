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

import br.unitins.dto.EquipamentoOdontologicoDTO;
import br.unitins.dto.EquipamentoOdontologicoResponseDTO;
import br.unitins.model.EquipamentoOdontologico;
import br.unitins.repository.EquipamentoOdontologicoRepository;

@ApplicationScoped
public class EquipamentoOdontologicoServiceImpl implements EquipamentoOdontologicoService {
    @Inject
    EquipamentoOdontologicoRepository equipamentoOdontologicoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EquipamentoOdontologicoResponseDTO> getAll() {
        List<EquipamentoOdontologico> list =  equipamentoOdontologicoRepository.listAll();
        return list.stream().map(EquipamentoOdontologicoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EquipamentoOdontologicoResponseDTO findById(Long id) {
        EquipamentoOdontologico equipamentoOdontologico = equipamentoOdontologicoRepository.findById(id);
        if(equipamentoOdontologico == null){
            throw new NotFoundException("Não encontrou o Equipamento Odontologico");
        }
        return new EquipamentoOdontologicoResponseDTO(equipamentoOdontologico);
    }

    @Override
    @Transactional
    public EquipamentoOdontologicoResponseDTO create(EquipamentoOdontologicoDTO equipamentoOdontologicoDTO) throws ConstraintViolationException {
        validar(equipamentoOdontologicoDTO);

        EquipamentoOdontologico entity = new EquipamentoOdontologico();
        entity.setFinalidade(equipamentoOdontologicoDTO.finalidade());
        equipamentoOdontologicoRepository.persist(entity);

        return new EquipamentoOdontologicoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EquipamentoOdontologicoResponseDTO update(Long id, EquipamentoOdontologicoDTO equipamentoOdontologicoDTO) throws ConstraintViolationException {
        EquipamentoOdontologico equipamentoOdontologicoUpdate = equipamentoOdontologicoRepository.findById(id);
        if (equipamentoOdontologicoUpdate == null)
            throw new NotFoundException("Telefone não encontrado.");
        validar(equipamentoOdontologicoDTO);

        equipamentoOdontologicoUpdate.setFinalidade(equipamentoOdontologicoDTO.finalidade());

        equipamentoOdontologicoRepository.persist(equipamentoOdontologicoUpdate);
        return new EquipamentoOdontologicoResponseDTO(equipamentoOdontologicoUpdate);
    }

    private void validar(EquipamentoOdontologicoDTO equipamentoOdontologicoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EquipamentoOdontologicoDTO>> violations = validator.validate(equipamentoOdontologicoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        equipamentoOdontologicoRepository.deleteById(id);
    }

    @Override
    public List<EquipamentoOdontologicoResponseDTO> findByNome(String finalidade) {
        List<EquipamentoOdontologico> list = equipamentoOdontologicoRepository.findByNome(finalidade);
        return list.stream().map(EquipamentoOdontologicoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return equipamentoOdontologicoRepository.count();
    }

}

