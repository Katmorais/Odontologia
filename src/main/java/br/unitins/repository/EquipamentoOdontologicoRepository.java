package br.unitins.repository;

import br.unitins.model.EquipamentoOdontologico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EquipamentoOdontologicoRepository implements PanacheRepository<EquipamentoOdontologico> {
    public List<EquipamentoOdontologico> findByNome(String finalidade){
        if (finalidade == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+ finalidade.toUpperCase()+"%").list();
    }

}
