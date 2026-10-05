package com.example.catalogorestaurante.model;

public sealed interface FormaPagamento permits Dinheiro, Cartao, Pix {
}
