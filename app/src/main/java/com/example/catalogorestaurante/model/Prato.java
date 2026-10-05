package com.example.catalogorestaurante.model;

import java.math.BigDecimal;

public final class Prato extends ItemMenu {
    private final boolean vegetariano;

    public Prato(String nome, BigDecimal preco, String descricao, boolean vegetariano) {
        super(nome, preco, descricao);
        this.vegetariano = vegetariano;
    }

    public boolean isVegetariano() {
        return vegetariano;
    }
}
