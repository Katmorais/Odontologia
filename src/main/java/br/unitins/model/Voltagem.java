package br.unitins.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Voltagem {
    VOLT_110(1, "110V"),
    VOLT_220V(2, "220V");

    private int id;
    private String label;

    Voltagem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Voltagem valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for(Voltagem voltagem : Voltagem.values()) {
            if (id.equals(voltagem.getId()))
                return voltagem;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }

}


