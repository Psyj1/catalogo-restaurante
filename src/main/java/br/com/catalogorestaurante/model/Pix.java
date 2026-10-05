package br.com.catalogorestaurante.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Pix implements FormaPagamento {
    private final BigDecimal percentualDesconto;

    public Pix(BigDecimal percentualDesconto) {
        Objects.requireNonNull(percentualDesconto, "percentualDesconto não pode ser nulo");
        if (percentualDesconto.signum() < 0
                || percentualDesconto.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("percentualDesconto deve estar entre 0 e 100");
        }
        this.percentualDesconto = percentualDesconto;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }
}
