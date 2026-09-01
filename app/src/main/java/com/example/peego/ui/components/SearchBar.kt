package com.example.peego.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.peego.R

/** Barra de busca superior, igual à referência: campo arredondado com ícone de busca + filtro integrado. */
@Composable
fun TopSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(Color.White, RoundedCornerShape(28.dp))
            .padding(start = 16.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = null,
            tint = Color(0xFF7A7A7A),
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = query.ifBlank { stringResourceCompat() },
            color = Color(0xFF7A7A7A),
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        // Divider vertical sutil antes do botão de filtro
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(28.dp)
                .background(Color(0xFFE0E0E0))
        )

        IconButton(onClick = onFilterClick) {
            Icon(
                Icons.Filled.Tune,
                contentDescription = "Filtros",
                tint = Color(0xFF1B1B1B),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun stringResourceCompat(): String =
    androidx.compose.ui.res.stringResource(id = R.string.search_hint)
