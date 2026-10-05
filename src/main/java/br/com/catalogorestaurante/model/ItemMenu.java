package br.com.catalogorestaurante.model;

import java.math.BigDecimal;
import java.util.Objects;

public abstract sealed class ItemMenu permits Prato, Bebida {
    private final String nome;
    private final BigDecimal preco;
    private final String descricao;

    protected ItemMenu(String nome, BigDecimal preco, String descricao) {
        this.nome = requireText(nome, "nome");
        this.preco = requireNonNegative(preco, "preco");
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " deve ser informado");
        }
        return value;
    }

    private static BigDecimal requireNonNegative(BigDecimal value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " não pode ser nulo");
        if (value.signum() < 0) {
            throw new IllegalArgumentException(fieldName + " não pode ser negativo");
        }
        return value;
    }
}
