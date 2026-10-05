package com.example.catalogorestaurante.business;

import android.util.Log;

import com.example.catalogorestaurante.model.Bebida;
import com.example.catalogorestaurante.model.Cartao;
import com.example.catalogorestaurante.model.Dinheiro;
import com.example.catalogorestaurante.model.FormaPagamento;
import com.example.catalogorestaurante.model.ItemMenu;
import com.example.catalogorestaurante.model.Pix;
import com.example.catalogorestaurante.model.Prato;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CalculadoraPedido {

    private static final String TAG = "CalculadoraPedido";
    private static final BigDecimal TAXA_SERVICO = new BigDecimal("0.10");
    private static final BigDecimal CEM = new BigDecimal("100");

    private CalculadoraPedido() {
        // classe utilitária
    }

    public static ResumoPedido calcular(List<ItemMenu> itens, FormaPagamento pagamento) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("A lista de itens não pode ser vazia");
        }
        if (pagamento == null) {
            throw new IllegalArgumentException("A forma de pagamento deve ser informada");
        }

        BigDecimal subtotal = calcularSubtotal(itens);
        BigDecimal taxaServico = subtotal.multiply(TAXA_SERVICO);
        BigDecimal desconto = calcularDesconto(subtotal, pagamento);
        BigDecimal total = subtotal.add(taxaServico).subtract(desconto);

        Map<String, List<ItemMenu>> agrupados = agruparPorCategoria(itens);
        imprimirRelatorio(agrupados, subtotal, taxaServico, desconto, total);

        return new ResumoPedido(subtotal, taxaServico, desconto, total, agrupados);
    }

    private static BigDecimal calcularSubtotal(List<ItemMenu> itens) {
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemMenu item : itens) {
            soma = soma.add(item.getPreco());
        }
        return soma;
    }

    private static BigDecimal calcularDesconto(BigDecimal subtotal, FormaPagamento pagamento) {
        return switch (pagamento) {
            case Pix pix -> subtotal.multiply(
                    pix.getPercentualDesconto().divide(CEM, 10, RoundingMode.HALF_UP)
            );
            case Dinheiro dinheiro -> BigDecimal.ZERO;
            case Cartao cartao -> BigDecimal.ZERO;
        };
    }

    private static Map<String, List<ItemMenu>> agruparPorCategoria(List<ItemMenu> itens) {
        Map<String, List<ItemMenu>> agrupado = new LinkedHashMap<>();
        agrupado.put("Pratos", new ArrayList<>());
        agrupado.put("Bebidas", new ArrayList<>());

        for (ItemMenu item : itens) {
            switch (item) {
                case Prato prato -> agrupado.get("Pratos").add(prato);
                case Bebida bebida -> agrupado.get("Bebidas").add(bebida);
            }
        }
        return agrupado;
    }

    private static void imprimirRelatorio(Map<String, List<ItemMenu>> agrupados,
                                          BigDecimal subtotal,
                                          BigDecimal taxaServico,
                                          BigDecimal desconto,
                                          BigDecimal total) {
        Log.d(TAG, "===== RELATÓRIO DO PEDIDO =====");
        for (Map.Entry<String, List<ItemMenu>> entry : agrupados.entrySet()) {
            Log.d(TAG, "Categoria: " + entry.getKey());
            for (ItemMenu item : entry.getValue()) {
                Log.d(TAG, "  - " + item.getNome() + " | R$ " + item.getPreco());
            }
        }
        Log.d(TAG, "Subtotal: R$ " + subtotal);
        Log.d(TAG, "Taxa de serviço: R$ " + taxaServico);
        Log.d(TAG, "Desconto: R$ " + desconto);
        Log.d(TAG, "Total: R$ " + total);
        Log.d(TAG, "================================");
    }
}