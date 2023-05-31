package br.unitins.repository;

import br.unitins.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    public List<Endereco> findByNome(String bairro){
        if (bairro == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+ bairro.toUpperCase()+"%").list();
    }

}


