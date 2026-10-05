package br.com.catalogorestaurante.model;

import java.math.BigDecimal;

public final class Bebida extends ItemMenu {
    private final boolean alcoolica;

    public Bebida(String nome, BigDecimal preco, String descricao, boolean alcoolica) {
        super(nome, preco, descricao);
        this.alcoolica = alcoolica;
    }

    public boolean isAlcoolica() {
        return alcoolica;
    }
}
