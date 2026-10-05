# Catálogo de Restaurante - Integração e Entrega

Este é um aplicativo Android nativo desenvolvido para exibição de catálogo de itens de restaurante, seleção de produtos e resumo final do pedido com diferentes formas de pagamento.

---

##  Tecnologias Utilizadas

- **Linguagens:** Kotlin e Java
- **Interface:** Jetpack Compose
- **Build System:** Gradle (Kotlin DSL - `.gradle.kts`)
- **Testes:** JUnit 4 / AndroidX Test

---

##  Pré-requisitos

Antes de iniciar, certifique-se de ter os seguintes softwares instalados na sua máquina:

1. **JDK 17** ou superior (compatível com as versões recentes do Gradle e do Android Studio).
2. **Android Studio** (Recomendado: versão *Hedgehog* ou superior).
3. **Android SDK** com suporte a API Level 24 (Android 7.0) ou superior.
4. **Dispositivo Físico** com *Depuração USB* ativada OU um **Emulador Android (AVD)** configurado.

---
## Modelagem

Os modelos do domínio estão em `br.com.catalogorestaurante.model`:

- `ItemMenu` é a raiz selada da hierarquia de itens e contém nome, preço e descrição opcional.
- `Prato` informa se o item é vegetariano.
- `Bebida` informa se o item é alcoólico.
- `FormaPagamento` é uma hierarquia selada limitada a `Dinheiro`, `Cartao` e `Pix`.
- `Pix` carrega o percentual de desconto.

Os valores monetários e percentuais usam `BigDecimal`.

##  Como Executar o Projeto

### Opção 1: Pelo Android Studio (Recomendado)

1. **Clonar o Repositório:**
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd catalogo-restaurante-Integra-ao-e-entrega
```

Abra o Android Studio.

Clique em Open e selecione a pasta raiz do projeto (catalogo-restaurante-Integra-ao-e-entrega).

Aguarde o término da sincronização do Gradle (Gradle Sync).

Executar o App:

Selecione o dispositivo de destino (Emulador ou Dispositivo Físico) na barra superior.

Clique no botão Run

