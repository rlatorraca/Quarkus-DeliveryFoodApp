package com.rlsp.graphql;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.microprofile.graphql.Ignore;

public class Prato {


    private String name;
    private String description;
    private BigDecimal value;

    @Ignore
    public List<Integer> ingredientesIds;

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String descricao) {
        this.description = descricao;
    }

    public BigDecimal getValor() {
        return value;
    }

    public void setValor(BigDecimal valor) {
        this.value = valor;
    }
}
