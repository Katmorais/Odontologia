package br.unitins.model;

import jakarta.persistence.Entity;

@Entity
public class EquipamentoOdontologico extends Produto{
    private Long id;

    private String finalidade;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFinalidade() {
        return finalidade;
    }
    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

}

