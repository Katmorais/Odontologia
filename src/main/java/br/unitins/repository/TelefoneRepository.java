package br.unitins.repository;

import br.unitins.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

    public List<Telefone> findByNome(String numero){
        if(numero == null)
            return null;
        return find("UPPER(nome) LIKE ?1 "," %"+numero.toUpperCase()+"%").list();
    }
}
