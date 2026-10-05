package com.example.catalogorestaurante.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.business.ResumoPedido
import com.example.catalogorestaurante.model.Cartao
import com.example.catalogorestaurante.model.Dinheiro
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.model.Pix
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

/** Linha do recibo derivada da lista de compras (nenhum dado fixo). */
data class LinhaRecibo(
    val nome: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal,
    val totalLinha: BigDecimal
)

/** Agrupa itens repetidos em linhas com quantidade, mantendo a ordem de inserção. */
fun montarLinhas(itens: List<ItemMenu>): List<LinhaRecibo> =
    itens.groupingBy { it }.eachCount().map { (item, qtd) ->
        LinhaRecibo(
            nome = item.nome,
            quantidade = qtd,
            precoUnitario = item.preco,
            totalLinha = item.preco.multiply(BigDecimal(qtd))
        )
    }

private fun formatarMoeda(valor: BigDecimal): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(valor)

private fun rotulo(forma: FormaPagamento): String = when (forma) {
    is Dinheiro -> "Dinheiro"
    is Cartao -> "Cartão"
    is Pix -> "Pix"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumoScreen(
    itens: List<ItemMenu>,
    formaPagamentoInicial: FormaPagamento,
    formasDisponiveis: List<FormaPagamento>,
    calcular: (List<ItemMenu>, FormaPagamento) -> ResumoPedido,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var formaSelecionada by remember { mutableStateOf(formaPagamentoInicial) }

    // Recalcula na hora sempre que os itens ou a forma de pagamento mudam.
    val resumo = remember(itens, formaSelecionada) { calcular(itens, formaSelecionada) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Resumo", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    TextButton(onClick = onVoltar) {
                        Text("Voltar", style = MaterialTheme.typography.labelLarge)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SeletorFormaPagamento(
                    formas = formasDisponiveis,
                    selecionada = formaSelecionada,
                    onSelecionar = { formaSelecionada = it }
                )
            }
            item { Recibo(resumo = resumo) }
        }
    }
}

@Composable
fun SeletorFormaPagamento(
    formas: List<FormaPagamento>,
    selecionada: FormaPagamento,
    onSelecionar: (FormaPagamento) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Forma de pagamento", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            formas.forEach { forma ->
                FilterChip(
                    selected = forma::class == selecionada::class,
                    onClick = { onSelecionar(forma) },
                    label = { Text(rotulo(forma), style = MaterialTheme.typography.labelLarge) }
                )
            }
        }
    }
}

@Composable
fun Recibo(resumo: ResumoPedido) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Recibo",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            HorizontalDivider()

            // Categorias e itens vêm do motor de negócio (itensAgrupados).
            resumo.itensAgrupados.forEach { (categoria, itens) ->
                if (itens.isNotEmpty()) {
                    Text(
                        text = categoria.uppercase(Locale.forLanguageTag("pt-BR")),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    montarLinhas(itens).forEach { LinhaItem(it) }
                }
            }

            HorizontalDivider()
            LinhaValor("Subtotal", formatarMoeda(resumo.subtotal))
            LinhaValor("Taxa de serviço", formatarMoeda(resumo.taxaServico))
            LinhaValor("Desconto", "- " + formatarMoeda(resumo.desconto))
            HorizontalDivider()
            LinhaValor("Total", formatarMoeda(resumo.total), destaque = true)
        }
    }
}

@Composable
private fun LinhaItem(linha: LinhaRecibo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(linha.nome, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "${linha.quantidade} x ${formatarMoeda(linha.precoUnitario)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(formatarMoeda(linha.totalLinha), style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun LinhaValor(rotulo: String, valor: String, destaque: Boolean = false) {
    val estilo = if (destaque) MaterialTheme.typography.titleMedium
    else MaterialTheme.typography.bodyMedium
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(rotulo, style = estilo)
        Text(valor, style = estilo)
    }
}