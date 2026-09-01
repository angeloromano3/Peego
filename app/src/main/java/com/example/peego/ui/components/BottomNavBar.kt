package com.example.peego.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class BottomTab { EXPLORAR, LISTA, CONTRIBUIR }

/**
 * Barra inferior com 3 abas, igual à referência: aba selecionada ganha
 * uma "pill" preenchida com ícone + texto; abas não selecionadas só ícone.
 */
@Composable
fun BottomNavBar(
    selected: BottomTab,
    onSelect: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem(
            icon = Icons.Filled.Map,
            label = "Explorar",
            selected = selected == BottomTab.EXPLORAR,
            onClick = { onSelect(BottomTab.EXPLORAR) }
        )
        NavItem(
            icon = Icons.AutoMirrored.Filled.List,
            label = "Lista",
            selected = selected == BottomTab.LISTA,
            onClick = { onSelect(BottomTab.LISTA) }
        )
        NavItem(
            icon = Icons.Filled.AddCircle,
            label = "Contribuir",
            selected = selected == BottomTab.CONTRIBUIR,
            onClick = { onSelect(BottomTab.CONTRIBUIR) }
        )
    }
}

@Composable
private fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) Color(0xFF0F6B5C) else Color.Transparent,
        animationSpec = tween(durationMillis = 250),
        label = "navItemBg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color(0xFF7A7A7A),
        animationSpec = tween(durationMillis = 250),
        label = "navItemContent"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(icon, contentDescription = label, tint = contentColor, modifier = Modifier.size(22.dp))
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200))
        ) {
            Text(
                text = label,
                color = contentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}
