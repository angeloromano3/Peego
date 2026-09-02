package br.com.fiap.peego.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import br.com.fiap.peego.R
import br.com.fiap.peego.model.Bathroom

@Composable
fun BathroomListItem(bathroom: Bathroom, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = bathroom.imageUrl,
            contentDescription = bathroom.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFEDEDED))
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(bathroom.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${bathroom.distanceMeters}m", color = Color(0xFF7A7A7A), fontSize = 13.sp)
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFF5A623), modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("${bathroom.rating}", fontSize = 13.sp, color = Color(0xFF1B1B1B))
                Text("  •  ", color = Color(0xFF7A7A7A), fontSize = 13.sp)
                Text(
                    text = if (bathroom.isOpenNow) stringResource(R.string.aberto_agora) else stringResource(R.string.fechado),
                    color = if (bathroom.isOpenNow) Color(0xFF1D8348) else Color(0xFFC0392B),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (bathroom.isAccessible) {
                    CategoryTag(text = "♿ ${stringResource(R.string.acessivel)}", background = Color(0xFFDFF3E6), contentColor = Color(0xFF1D8348))
                }
                CategoryTag(
                    text = if (bathroom.isFree) stringResource(R.string.gratuito) else stringResource(R.string.pago),
                    background = Color(0xFFEDEDED),
                    contentColor = Color(0xFF5F5F5F)
                )
            }
        }
    }
}
