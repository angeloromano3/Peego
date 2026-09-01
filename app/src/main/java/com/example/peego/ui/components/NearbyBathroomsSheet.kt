package com.example.peego.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peego.data.model.Bathroom

/**
 * Bottom sheet "Banheiros próximos", igual à referência.
 * - Sempre mostra o primeiro item.
 * - Ao tocar na "alcinha" (handle) ou no título, expande/recolhe o restante
 *   da lista com animação (AnimatedVisibility + animateContentSize).
 */
@Composable
fun NearbyBathroomsSheet(
    bathrooms: List<Bathroom>,
    onListarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .animateContentSize()
            .padding(bottom = 8.dp)
    ) {
        // Handle (alcinha) para indicar que o sheet é arrastável/expansível.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFD0D0D0))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Banheiros próximos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = "Listar",
                color = Color(0xFF0F6B5C),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = onListarClick)
            )
        }

        Spacer(Modifier.height(14.dp))

        if (bathrooms.isNotEmpty()) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BathroomListItem(bathroom = bathrooms.first())
            }
        }

        AnimatedVisibility(
            visible = expanded && bathrooms.size > 1,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .heightIn(max = 260.dp)
            ) {
                Spacer(Modifier.height(14.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(bathrooms.drop(1)) { bathroom ->
                        BathroomListItem(bathroom = bathroom)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}
