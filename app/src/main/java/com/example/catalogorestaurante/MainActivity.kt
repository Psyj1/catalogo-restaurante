package com.example.catalogorestaurante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.catalogorestaurante.business.CalculadoraPedido
import com.example.catalogorestaurante.business.ResumoPedido
import com.example.catalogorestaurante.model.Bebida
import com.example.catalogorestaurante.model.Cartao
import com.example.catalogorestaurante.model.Dinheiro
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.model.Pix
import com.example.catalogorestaurante.model.Prato
import com.example.catalogorestaurante.ui.screens.CatalogoScreen
import com.example.catalogorestaurante.ui.screens.ResumoScreen
import com.example.catalogorestaurante.ui.theme.CatalogoRestauranteTheme
import java.math.BigDecimal

// Dados de exemplo para desenvolvimento; substitua pela fonte real do catálogo.
private val catalogoExemplo: List<ItemMenu> = listOf(
    Prato("Pizza Margherita", BigDecimal("42.00"), "Molho de tomate, manjericão e queijo", true),
    Prato("Feijoada completa tradicional", BigDecimal("58.00"), null, false),
    Bebida("Suco de laranja", BigDecimal("12.00"), "Suco natural", false)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatalogoRestauranteTheme {
                AppCatalogo(
                    catalogo = catalogoExemplo,
                    formasDisponiveis = listOf(
                        Dinheiro(),
                        Cartao(),
                        Pix(BigDecimal("10")) // percentual de desconto do Pix (ajuste à regra real)
                    ),
                    calcular = CalculadoraPedido::calcular
                )
            }
        }
    }
}

@Composable
fun AppCatalogo(
    catalogo: List<ItemMenu>,
    formasDisponiveis: List<FormaPagamento>,
    calcular: (List<ItemMenu>, FormaPagamento) -> ResumoPedido
) {
    // null = tela de catálogo; lista = tela de resumo com o carrinho recebido
    var itensResumo by remember { mutableStateOf<List<ItemMenu>?>(null) }
    val itens = itensResumo

    if (itens == null) {
        CatalogoScreen(
            catalogo = catalogo,
            onNavegarResumo = { itensResumo = it }
        )
    } else {
        BackHandler { itensResumo = null }
        ResumoScreen(
            itens = itens,
            formaPagamentoInicial = formasDisponiveis.first(),
            formasDisponiveis = formasDisponiveis,
            calcular = calcular,
            onVoltar = { itensResumo = null }
        )
    }
}