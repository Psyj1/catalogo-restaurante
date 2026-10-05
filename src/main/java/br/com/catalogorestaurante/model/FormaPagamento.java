package br.com.catalogorestaurante.model;

public sealed interface FormaPagamento permits Dinheiro, Cartao, Pix {
}
