package br.com.fiap.peego.ui.screens
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BabyChangingStation
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.peego.ui.theme.AcessaBanheiroTheme
import br.com.fiap.peego.ui.theme.BordaClara
import br.com.fiap.peego.ui.theme.CardBranco
import br.com.fiap.peego.ui.theme.FundoMenta
import br.com.fiap.peego.ui.theme.TextoPrimario
import br.com.fiap.peego.ui.theme.TextoSecundario
import br.com.fiap.peego.ui.theme.VerdePrimario

// A navegação entre MapScreen e FilterScreen será conectada
// no AppNavigation quando a tela de mapa estiver disponível.
@Composable
fun FilterScreen(
    voltar: () -> Unit = {},
    aplicarFiltros: (
        cadeirante: Boolean,
        idoso: Boolean,
        deficienciaVisual: Boolean,
        ostomia: Boolean,
        fraldario: Boolean
    ) -> Unit = { _, _, _, _, _ -> }
) {
    var cadeirante by remember { mutableStateOf(false) }
    var idoso by remember { mutableStateOf(false) }
    var deficienciaVisual by remember { mutableStateOf(false) }
    var ostomia by remember { mutableStateOf(false) }
    var fraldario by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMenta)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBranco)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = voltar) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = TextoPrimario
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Filtro",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrimario
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Selecione as necessidades para refinar sua busca por instalações adequadas.",
                fontSize = 14.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(20.dp))

            SecaoTitulo("ACESSIBILIDADE")

            Spacer(modifier = Modifier.height(12.dp))

            FiltroCard(
                icone = Icons.Filled.Accessible,
                titulo = "Cadeirante",
                descricao = "Espaço amplo, barras de apoio e pias rebaixadas.",
                selecionado = cadeirante,
                onToggle = { cadeirante = !cadeirante }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FiltroCard(
                icone = Icons.Filled.Elderly,
                titulo = "Idoso",
                descricao = "Barras de apoio, piso antiderrapante e assento elevado.",
                selecionado = idoso,
                onToggle = { idoso = !idoso }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FiltroCard(
                icone = Icons.Filled.RemoveRedEye,
                titulo = "Deficiência Visual",
                descricao = "Sinalização em braille e piso tátil direcional.",
                selecionado = deficienciaVisual,
                onToggle = { deficienciaVisual = !deficienciaVisual }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SecaoTitulo("SAÚDE")

            Spacer(modifier = Modifier.height(12.dp))

            FiltroCard(
                icone = Icons.Filled.MedicalServices,
                titulo = "Adequado para Ostomia",
                descricao = "Bancada de apoio, ducha higiênica e descarte adequado.",
                selecionado = ostomia,
                onToggle = { ostomia = !ostomia }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FiltroCard(
                icone = Icons.Filled.BabyChangingStation,
                titulo = "Fraldário",
                descricao = "Trocador seguro, lixeira com tampa e pia próxima.",
                selecionado = fraldario,
                onToggle = { fraldario = !fraldario }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botão inferior
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBranco)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = {
                    aplicarFiltros(cadeirante, idoso, deficienciaVisual, ostomia, fraldario)
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Aplicar Filtros", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun SecaoTitulo(texto: String) {
    Column {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            color = VerdePrimario
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = BordaClara)
    }
}

@Composable
private fun FiltroCard(
    icone: ImageVector,
    titulo: String,
    descricao: String,
    selecionado: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBranco, RoundedCornerShape(16.dp))
            .border(1.dp, BordaClara, RoundedCornerShape(16.dp))
            .clickable(onClick = onToggle)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = VerdePrimario,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                fontSize = 15.sp,
                color = TextoPrimario
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = descricao,
                fontSize = 13.sp,
                color = TextoSecundario
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = selecionado,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(checkedColor = VerdePrimario)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterScreenPreview() {
    AcessaBanheiroTheme {
        FilterScreen()
    }
}
