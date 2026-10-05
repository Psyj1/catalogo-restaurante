package com.example.catalogorestaurante.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.ui.components.ItemCard
import com.example.catalogorestaurante.model.Bebida
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.model.Prato

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    catalogo: List<ItemMenu>,
    onNavegarResumo: (List<ItemMenu>) -> Unit
) {
    // Estado do carrinho que será passado para a próxima tela
    var carrinho by remember { mutableStateOf(listOf<ItemMenu>()) }
    val pratos = catalogo.filterIsInstance<Prato>()

    // Separação lógica para o layout baseado no wireframe
    val bebidas = catalogo.filterIsInstance<Bebida>()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cardápio") },
                actions = {
                    // Indicador de estado do carrinho no cabeçalho
                    Text(
                        text = "🛒 (${carrinho.size})",
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        },
        bottomBar = {
            // Navegação para o resumo com o estado do carrinho
            BottomAppBar {
                Button(
                    onClick = { onNavegarResumo(carrinho) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = carrinho.isNotEmpty()
                ) {
                    Text("[ VER RESUMO ]")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (pratos.isNotEmpty()) {
                item {
                    Text(
                        text = "PRATOS",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(pratos) { prato ->
                    ItemCard(
                        item = prato,
                        onAdicionarClick = { carrinho = carrinho + it }
                    )
                }
            }

            if (bebidas.isNotEmpty()) {
                item {
                    Text(
                        text = "BEBIDAS",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                }
                items(bebidas) { bebida ->
                    ItemCard(
                        item = bebida,
                        onAdicionarClick = { carrinho = carrinho + it }
                    )
                }
            }
        }
    }
}