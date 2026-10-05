# Catálogo de Restaurante

## Requisitos

- Java 17 ou superior
- Maven 3.9 ou superior

## Modelagem

Os modelos do domínio estão em `br.com.catalogorestaurante.model`:

- `ItemMenu` é a raiz selada da hierarquia de itens e contém nome, preço e descrição opcional.
- `Prato` informa se o item é vegetariano.
- `Bebida` informa se o item é alcoólico.
- `FormaPagamento` é uma hierarquia selada limitada a `Dinheiro`, `Cartao` e `Pix`.
- `Pix` carrega o percentual de desconto.

Os valores monetários e percentuais usam `BigDecimal`.

Para executar os testes:

```bash
mvn test
```