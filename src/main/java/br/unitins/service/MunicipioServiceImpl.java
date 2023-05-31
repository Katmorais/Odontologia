package br.unitins.service;

import br.unitins.dto.MunicipioDTO;
import br.unitins.dto.MunicipioResponseDTO;
import br.unitins.model.Estado;
import br.unitins.model.Municipio;
import br.unitins.repository.EstadoRepository;
import br.unitins.repository.MunicipioRepository;

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
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    Validator validator;

    @Override
    public List<MunicipioResponseDTO> getAll() {
        List<Municipio> list = municipioRepository.listAll();
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public MunicipioResponseDTO findById(Long id) {
        Municipio municipio = municipioRepository.findById(id);
        if (municipio == null)
            return null;
        return new MunicipioResponseDTO(municipio);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO create(MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);

        Municipio municipio = new Municipio();
        municipio.setNome(municipioDTO.nome());
        municipio.setEstado(new Estado());
        municipio.getEstado().setId(municipioDTO.idEstado());
        municipioRepository.persist(municipio);

        if (municipioDTO.idEstado() == null)
            throw new NotFoundException("Estado não encontrado.");

        return new MunicipioResponseDTO(municipio);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO update(Long id, MunicipioDTO municipioDTO) throws ConstraintViolationException {
        validar(municipioDTO);
        Municipio municipio = municipioRepository.findById(id);
        if (municipio == null) {
            throw new NotFoundException(" Municipio não encontrado pelo id");
        }
        Estado estado = estadoRepository.findById(municipioDTO.idEstado());

        if (estado == null) {
            throw new NotFoundException(" Estado não encontrado pelo id");
        }

        municipio.setNome(municipioDTO.nome());
        municipio.setEstado(estado);
        municipioRepository.persist(municipio);

        return new MunicipioResponseDTO(municipio);
    }

    private void validar(MunicipioDTO municipioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<MunicipioDTO>> violations = validator.validate(municipioDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) {

        municipioRepository.deleteById(id);
    }

    @Override
    public List<MunicipioResponseDTO> findByNome(String nome) {
        List<Municipio> list = municipioRepository.findByNome(nome);
        return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return municipioRepository.count();
    }
}