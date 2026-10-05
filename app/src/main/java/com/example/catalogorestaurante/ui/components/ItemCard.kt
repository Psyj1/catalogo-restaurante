package com.example.catalogorestaurante.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale
import com.example.catalogorestaurante.model.ItemMenu


@Composable
fun ItemCard(
    item: ItemMenu,
    onAdicionarClick: (ItemMenu) -> Unit
) {

    // Formatação do BigDecimal para exibição monetária
    val precoFormatado = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(item.preco)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Tipografia exclusiva Material Design 3
            Text(
                text = item.nome,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Tratamento de descrição nula e truncamento com reticências (maxLines)
            Text(
                text = item.descricao ?: "Sem descrição",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = precoFormatado,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                // Emissão do callback ao adicionar ao carrinho
                TextButton(onClick = { onAdicionarClick(item) }) {
                    Text("[+ Add]") // Baseado no wireframe
                }
            }
        }
    }
}