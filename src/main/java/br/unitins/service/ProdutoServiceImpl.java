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

import br.unitins.dto.ProdutoDTO;
import br.unitins.dto.ProdutoResponseDTO;
import br.unitins.model.Produto;
import br.unitins.repository.ProdutoRepository;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    Validator validator;

    @Override
    public List<ProdutoResponseDTO> getAll() {
        List<Produto> list = produtoRepository.listAll();
        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null)
            throw new NotFoundException("Produto não encontrado.");
        return new ProdutoResponseDTO(produto);
    }

    @Override
    public ProdutoResponseDTO create(ProdutoDTO produtoDTO) throws ConstraintViolationException {
        validar(produtoDTO);

        Produto entity = new Produto();
        entity.setNome(produtoDTO.nome());
        entity.setDescricao(produtoDTO.descricao());
        entity.setEstoque(produtoDTO.estoque());
        entity.setPreco(produtoDTO.preco());

        produtoRepository.persist(entity);
        return new ProdutoResponseDTO(entity);
    }

    private void validar(ProdutoDTO produtoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public ProdutoResponseDTO update(Long id, ProdutoDTO produtoDTO) {
        Produto entity = produtoRepository.findById(id);

        entity.setNome(produtoDTO.nome());
        entity.setDescricao(produtoDTO.descricao());
        entity.setEstoque(produtoDTO.estoque());
        entity.setPreco(produtoDTO.preco());

        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome) {
        List<Produto> list = produtoRepository.findByNome(nome);
        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return produtoRepository.count();
    }
    
}
