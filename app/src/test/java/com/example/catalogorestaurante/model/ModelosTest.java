package com.example.catalogorestaurante.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ModelosTest {
    @Test
    void deveModelarPratoComDescricaoNullableEFlagVegetariana() {
        Prato prato = new Prato("Salada", new BigDecimal("25.90"), null, true);

        assertEquals("Salada", prato.getNome());
        assertEquals(new BigDecimal("25.90"), prato.getPreco());
        assertNull(prato.getDescricao());
        assertTrue(prato.isVegetariano());
    }

    @Test
    void deveModelarBebidaComFlagAlcoolica() {
        Bebida bebida = new Bebida("Suco", new BigDecimal("8.00"), "Natural", false);

        assertEquals("Suco", bebida.getNome());
        assertEquals(new BigDecimal("8.00"), bebida.getPreco());
        assertTrue(!bebida.isAlcoolica());
    }

    @Test
    void deveModelarPixComPercentualDeDesconto() {
        Pix pix = new Pix(new BigDecimal("5.00"));

        assertEquals(new BigDecimal("5.00"), pix.getPercentualDesconto());
    }
}
