package com.example.catalogorestaurante.business;

import com.example.catalogorestaurante.model.ItemMenu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class ResumoPedido {

    private final BigDecimal subtotal;
    private final BigDecimal taxaServico;
    private final BigDecimal desconto;
    private final BigDecimal total;
    private final Map<String, List<ItemMenu>> itensAgrupados;

    public ResumoPedido(BigDecimal subtotal,
                        BigDecimal taxaServico,
                        BigDecimal desconto,
                        BigDecimal total,
                        Map<String, List<ItemMenu>> itensAgrupados) {
        this.subtotal = subtotal;
        this.taxaServico = taxaServico;
        this.desconto = desconto;
        this.total = total;
        this.itensAgrupados = itensAgrupados;
    }

    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTaxaServico() { return taxaServico; }
    public BigDecimal getDesconto() { return desconto; }
    public BigDecimal getTotal() { return total; }
    public Map<String, List<ItemMenu>> getItensAgrupados() { return itensAgrupados; }
}