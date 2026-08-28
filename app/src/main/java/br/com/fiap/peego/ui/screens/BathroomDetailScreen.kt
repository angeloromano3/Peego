package br.com.fiap.peego.ui.screens.bathroomdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.peego.R
import br.com.fiap.peego.model.BathroomDetail
import br.com.fiap.peego.model.CondicoesRecentes
import br.com.fiap.peego.model.Informacoes
import br.com.fiap.peego.ui.theme.AcessaBanheiroTheme
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BathroomDetailScreen(
    detalhe: BathroomDetail,
    voltar: () -> Unit = {},
    favoritar: () -> Unit = {},
    avaliarBanheiro: () -> Unit = {},
    irAgora: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do banheiro") },
                navigationIcon = {
                    IconButton(onClick = voltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = favoritar) {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favoritar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mercado_extra),
                contentDescription = detalhe.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = detalhe.nome,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("${detalhe.avaliacao} · ${detalhe.quantidadeAvaliacoes} avaliações")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(50),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (detalhe.aberto) "Aberto agora" else "Fechado",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "· ${detalhe.distancia} de distância",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = irAgora,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.Navigation, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ir Agora")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Acessibilidade
            InfoCard(titulo = "Acessibilidade") {
                LinhaInfo(Icons.Default.Accessible, "Acessível para cadeirante")
                LinhaInfo(Icons.Default.Remove, "Barras de apoio")
                LinhaInfo(Icons.Default.SwapHoriz, "Espaço para manobra")
                LinhaInfo(Icons.Default.WaterDrop, "Pia acessível")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Inclusão
            InfoCard(titulo = "Inclusão e necessidades") {
                LinhaInfo(Icons.Default.Wc, "Banheiro neutro / sem gênero")
                LinhaInfo(Icons.Default.ChildCare, "Fraldário")
                LinhaInfo(Icons.Default.MedicalServices, "Adequado para pessoas ostomizadas")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Condições recentes — 2 colunas
            InfoCard(titulo = "Condições recentes") {
                val c = detalhe.condicoesRecentes
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        LinhaInfo(Icons.Default.CleaningServices, "Limpeza: ${c.limpeza}")
                        LinhaInfo(Icons.Default.Lightbulb, "Iluminação: ${c.iluminacao}")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LinhaInfo(Icons.Default.Shield, "Segurança: ${c.seguranca}")
                        LinhaInfo(Icons.Default.Waves, "Água disponível: ${c.aguaDisponivel}")
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Atualizado ${c.atualizadoHaTempo} pela comunidade",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Informações
            InfoCard(titulo = "Informações") {
                val i = detalhe.informacoes
                LinhaInfo(Icons.Default.AccessTime, "Funcionamento: ${i.funcionamento}")
                LinhaInfo(Icons.Default.LocalAtm, if (i.gratuito) "Gratuito" else "Pago")
                LinhaInfo(Icons.Default.LocationOn, i.localizacao)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Já utilizou este banheiro?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = avaliarBanheiro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Avaliar banheiro")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoCard(
    titulo: String,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            conteudo()
        }
    }
}

@Composable
private fun LinhaInfo(icone: ImageVector, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            icone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun BathroomDetailScreenPreview() {
    AcessaBanheiroTheme {
        BathroomDetailScreen(
            detalhe = BathroomDetail(
                nome = "Extra Mercado - Campo Limpo",
                avaliacao = 4.8,
                quantidadeAvaliacoes = 124,
                distancia = "120 m",
                aberto = true,
                imagemUrl = "",
                acessibilidade = listOf(),
                inclusao = listOf(),
                condicoesRecentes = CondicoesRecentes(
                    limpeza = "Boa",
                    seguranca = "Boa",
                    iluminacao = "Boa",
                    aguaDisponivel = "Sim",
                    atualizadoHaTempo = "há 2 horas"
                ),
                informacoes = Informacoes(
                    funcionamento = "06h–22h",
                    gratuito = true,
                    localizacao = "Parque Ibirapuera — Portão 3"
                )
            )
        )
    }
}