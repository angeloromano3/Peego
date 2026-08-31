package br.com.fiap.peego.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.peego.ui.theme.PeeGoTheme
import br.com.fiap.peego.ui.theme.FundoMenta
import br.com.fiap.peego.ui.theme.VerdePrimario
import br.com.fiap.peego.ui.theme.TextoPrimario
import br.com.fiap.peego.ui.theme.TextoSecundario

@Composable
fun LocationPermissionScreen(
    permitirLocalizacao: () -> Unit = {},
    buscarPorEndereco: () -> Unit = {}
) {
    // TODO (próxima fase): trocar esse botão visual pela permissão real do Android
    //   usando ActivityResultContracts.RequestPermission() com
    //   android.permission.ACCESS_FINE_LOCATION, integrando com o FusedLocationProviderClient
    //   (Google Play Services) pra pegar a posição real do usuário.

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMenta),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(FundoMenta, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = VerdePrimario,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sua localização é sua",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrimario,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Precisamos da sua localização apenas para mostrar os banheiros mais próximos." +
                        " Nada é compartilhado ou salvo.",
                fontSize = 13.sp,
                color = TextoSecundario,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = permitirLocalizacao,
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Permitir localização", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Buscar por endereço manualmente",
                fontSize = 13.sp,
                color = VerdePrimario,
                modifier = Modifier.clickable(onClick = buscarPorEndereco)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationPermissionScreenPreview() {
    PeeGoTheme {
        LocationPermissionScreen()
    }
}
