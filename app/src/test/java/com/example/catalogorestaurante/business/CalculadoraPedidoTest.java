package com.example.catalogorestaurante.business;

import static org.junit.Assert.assertEquals;

import com.example.catalogorestaurante.model.Bebida;
import com.example.catalogorestaurante.model.Cartao;
import com.example.catalogorestaurante.model.Dinheiro;
import com.example.catalogorestaurante.model.FormaPagamento;
import com.example.catalogorestaurante.model.ItemMenu;
import com.example.catalogorestaurante.model.Pix;
import com.example.catalogorestaurante.model.Prato;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class CalculadoraPedidoTest {

    private List<ItemMenu> itensDoCenario() {
        return List.of(
                new Prato("Pizza Margherita", new BigDecimal("42.00"), "Molho, mussarela e manjericão", true),
                new Prato("Feijoada completa", new BigDecimal("58.00"), "Feijão preto, carnes e acompanhamentos", false),
                new Bebida("Suco de laranja", new BigDecimal("12.00"), "Natural, 500ml", false)
        );
    }

    @Test
    public void cenarioValidacao_pix_deveBaterValores() {
        FormaPagamento pagamento = new Pix(new BigDecimal("10"));
        ResumoPedido resumo = CalculadoraPedido.calcular(itensDoCenario(), pagamento);

        assertEquals(0, new BigDecimal("112.00").compareTo(resumo.getSubtotal()));
        assertEquals(0, new BigDecimal("11.20").compareTo(resumo.getTaxaServico()));
        assertEquals(0, new BigDecimal("11.20").compareTo(resumo.getDesconto()));
        assertEquals(0, new BigDecimal("112.00").compareTo(resumo.getTotal()));
    }

    @Test
    public void dinheiro_naoDeveTerDesconto() {
        ResumoPedido resumo = CalculadoraPedido.calcular(itensDoCenario(), new Dinheiro());

        assertEquals(0, new BigDecimal("112.00").compareTo(resumo.getSubtotal()));
        assertEquals(0, new BigDecimal("11.20").compareTo(resumo.getTaxaServico()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resumo.getDesconto()));
        assertEquals(0, new BigDecimal("123.20").compareTo(resumo.getTotal()));
    }

    @Test
    public void cartao_naoDeveTerDesconto() {
        ResumoPedido resumo = CalculadoraPedido.calcular(itensDoCenario(), new Cartao());

        assertEquals(0, BigDecimal.ZERO.compareTo(resumo.getDesconto()));
        assertEquals(0, new BigDecimal("123.20").compareTo(resumo.getTotal()));
    }

    @Test
    public void agrupamento_deveSepararPratosEBebidas() {
        ResumoPedido resumo = CalculadoraPedido.calcular(
                itensDoCenario(), new Pix(new BigDecimal("10"))
        );

        assertEquals(2, resumo.getItensAgrupados().get("Pratos").size());
        assertEquals(1, resumo.getItensAgrupados().get("Bebidas").size());
    }
}