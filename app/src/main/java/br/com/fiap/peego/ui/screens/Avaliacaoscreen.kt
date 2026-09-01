package br.com.fiap.peego.ui.screens.avaliacao

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import br.com.fiap.peego.R
import br.com.fiap.peego.ui.theme.PeeGoTheme
import br.com.fiap.peego.ui.theme.Poppins
import coil.compose.AsyncImage
import java.io.File

/*
 * Tela "Sua Avaliação" — reprodução do frame do Figma (HTML/CSS .avaliao),
 * a tela de avaliar um banheiro específico (Limpeza, Acessibilidade,
 * Manutenção + comentário + foto).
 *
 * Observações sobre fidelidade ao design:
 * 1) Fonte do Figma é "Inter" — o projeto só tem "Poppins" em res/font. Usei
 *    Poppins como substituto (mesmo esquema das outras telas já ajustadas).
 * 2) Cada critério (Limpeza, Acessibilidade, Manutenção) é avaliado com 1 a 5
 *    estrelas — mesmos ícones da listagem (ic_avaliacao_estrela_cheia/vazia):
 *    ao tocar numa estrela, as estrelas até ali "acendem" em amarelo e o
 *    restante fica cinza/apagado.
 * 3) O ícone de voltar (icon.svg) e o de câmera (vector.svg) também não
 *    vieram como arquivo — recriei formas equivalentes (seta e câmera).
 */

private val FundoTela = Color(0xFFEEFCFA)
private val HeaderBg = Color(0xFFFCF8F9)
private val HeaderBorda = Color(0xFFC2C6D4)
private val TituloHeader = Color(0xFF000000)
private val PublicarTexto = Color(0xFF4B5563)
private val SubtituloBanheiro = Color(0xFF4B5563)
private val CardBg = Color(0xFFFFFFFF)
private val CardBorda = Color(0xFFE5E7EB)
private val CriterioTitulo = Color(0xFF111827)
private val CriterioDescricao = Color(0xFF9CA3AF)
private val ComentarioLabel = Color(0xFF4B5563)
private val ComentarioPlaceholder = Color(0xFF9CA3AF)
private val FotoBg = Color(0xFFF5F3ED)
private val FotoBorda = Color(0xFFE5E7EB)
private val FotoTexto = Color(0xFF0D9488)
private val DialogoTituloTexto = Color(0xFF111827)
private val DialogoSairTexto = Color(0xFF9CA3AF)
private val DialogoOpcaoTexto = Color(0xFF111827)

/** Cria um arquivo temporário em cache e devolve uma content:// Uri (via FileProvider) para a câmera gravar a foto. */
private fun criarUriParaFoto(context: Context): Uri {
    val pasta = File(context.cacheDir, "fotos_avaliacao").apply { mkdirs() }
    val arquivo = File(pasta, "foto_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", arquivo)
}

@Composable
fun AvaliacaoScreen(
    nomeBanheiro: String = "Banheiro Extra Mercado - Campo Limpo",
    voltar: () -> Unit = {},
    aoPublicar: (limpeza: Int, acessibilidade: Int, manutencao: Int, comentario: String, foto: Uri?) -> Unit = { _, _, _, _, _ -> }
) {
    val context = LocalContext.current

    var limpezaNota by remember { mutableIntStateOf(0) }
    var acessibilidadeNota by remember { mutableIntStateOf(0) }
    var manutencaoNota by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }
    var mostrarDialogoSair by remember { mutableStateOf(false) }

    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var uriCameraPendente by remember { mutableStateOf<Uri?>(null) }
    var mostrarDialogoFoto by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { sucesso ->
        if (sucesso) {
            fotoUri = uriCameraPendente
        }
    }

    val permissaoCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedida ->
        if (concedida) {
            val uri = criarUriParaFoto(context)
            uriCameraPendente = uri
            cameraLauncher.launch(uri)
        }
    }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            fotoUri = uri
        }
    }

    fun abrirCamera() {
        val temPermissao = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (temPermissao) {
            val uri = criarUriParaFoto(context)
            uriCameraPendente = uri
            cameraLauncher.launch(uri)
        } else {
            permissaoCameraLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    fun abrirGaleria() {
        galeriaLauncher.launch(
            androidx.activity.result.PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    if (mostrarDialogoSair) {
        DialogoConfirmarSaida(
            onSair = {
                mostrarDialogoSair = false
                voltar()
            },
            onFechar = { mostrarDialogoSair = false }
        )
    }

    if (mostrarDialogoFoto) {
        DialogoEscolherFoto(
            onTirarFoto = {
                mostrarDialogoFoto = false
                abrirCamera()
            },
            onEscolherGaleria = {
                mostrarDialogoFoto = false
                abrirGaleria()
            },
            onFechar = { mostrarDialogoFoto = false }
        )
    }

    Scaffold(
        containerColor = FundoTela,
        topBar = {
            CabecalhoAvaliacao(
                onVoltar = { mostrarDialogoSair = true },
                onPublicar = {
                    aoPublicar(limpezaNota, acessibilidadeNota, manutencaoNota, comentario, fotoUri)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 16.dp)
            ) {
                Text(
                    text = nomeBanheiro,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = SubtituloBanheiro
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CartaoCriterio(
                    titulo = "Limpeza",
                    descricao = "Higiene do vaso, pia e chão",
                    nota = limpezaNota,
                    aoAlterar = { limpezaNota = it }
                )
                CartaoCriterio(
                    titulo = "Acessibilidade",
                    descricao = "Presença de rampas e barras",
                    nota = acessibilidadeNota,
                    aoAlterar = { acessibilidadeNota = it }
                )
                CartaoCriterio(
                    titulo = "Manutenção",
                    descricao = "Torneiras e trincos funcionando",
                    nota = manutencaoNota,
                    aoAlterar = { manutencaoNota = it }
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Comentário opcional",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = ComentarioLabel
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardBg)
                            .border(BorderStroke(1.dp, CardBorda), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        BasicTextFieldComentario(
                            valor = comentario,
                            aoAlterar = { comentario = it }
                        )
                    }
                }

                SeletorDeFoto(
                    fotoUri = fotoUri,
                    aoTocar = { mostrarDialogoFoto = true },
                    aoRemover = { fotoUri = null }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CabecalhoAvaliacao(onVoltar: () -> Unit, onPublicar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(HeaderBg)
            .border(BorderStroke(1.dp, HeaderBorda))
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVoltar) {
            Icon(
                painter = painterResource(id = R.drawable.ic_avaliacao_form_voltar),
                contentDescription = "Voltar",
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Sua Avaliação",
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 28.sp,
            color = TituloHeader,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Publicar",
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = PublicarTexto,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onPublicar)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun CartaoCriterio(
    titulo: String,
    descricao: String,
    nota: Int,
    aoAlterar: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .border(BorderStroke(1.dp, CardBorda), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = titulo,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = CriterioTitulo
            )
            Text(
                text = descricao,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = CriterioDescricao
            )
        }

        SeletorDeEstrelas(nota = nota, aoAlterar = aoAlterar)
    }
}

/**
 * Seletor de 1 a 5 estrelas. Ao tocar em uma estrela, todas até ela "acendem"
 * (ícone amarelo cheio) e as restantes ficam apagadas (ícone cinza vazio) —
 * mesmo padrão usado na listagem de banheiros.
 */
@Composable
private fun SeletorDeEstrelas(nota: Int, aoAlterar: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        (1..5).forEach { posicao ->
            Icon(
                painter = painterResource(
                    id = if (posicao <= nota) {
                        R.drawable.ic_avaliacao_estrela_cheia
                    } else {
                        R.drawable.ic_avaliacao_estrela_vazia
                    }
                ),
                contentDescription = "Nota $posicao",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        // tocar na mesma estrela já marcada zera a nota
                        aoAlterar(if (posicao == nota) 0 else posicao)
                    }
            )
        }
    }
}

@Composable
private fun BasicTextFieldComentario(valor: String, aoAlterar: (String) -> Unit) {
    androidx.compose.foundation.text.BasicTextField(
        value = valor,
        onValueChange = aoAlterar,
        textStyle = androidx.compose.ui.text.TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = CriterioTitulo
        ),
        decorationBox = { campoInterno ->
            if (valor.isEmpty()) {
                Text(
                    text = "Escreva detalhes que possam ajudar outras pessoas...",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = ComentarioPlaceholder
                )
            }
            campoInterno()
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun DialogoConfirmarSaida(onSair: () -> Unit, onFechar: () -> Unit) {
    AlertDialog(
        onDismissRequest = onFechar,
        containerColor = Color.White,
        confirmButton = {},
        text = {
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Tem certeza que deseja sair?",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DialogoTituloTexto
                )
                Text(
                    text = "Sair",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = DialogoSairTexto,
                    modifier = Modifier.clickable(onClick = onSair)
                )
            }
        }
    )
}

/**
 * Botão "Adicionar foto do banheiro". Quando já existe uma foto escolhida
 * (câmera ou galeria), mostra a miniatura no lugar do placeholder, com um
 * botão para remover.
 */
@Composable
private fun SeletorDeFoto(fotoUri: Uri?, aoTocar: () -> Unit, aoRemover: () -> Unit) {
    if (fotoUri == null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(FotoBg)
                .border(BorderStroke(1.dp, FotoBorda), RoundedCornerShape(12.dp))
                .clickable(onClick = aoTocar)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_avaliacao_camera),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Adicionar foto do banheiro",
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = FotoTexto
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(BorderStroke(1.dp, FotoBorda), RoundedCornerShape(12.dp))
                .clickable(onClick = aoTocar)
        ) {
            AsyncImage(
                model = fotoUri,
                contentDescription = "Foto do banheiro",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = aoRemover,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remover foto",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/** Diálogo simples oferecendo tirar uma foto nova ou escolher uma da galeria. */
@Composable
private fun DialogoEscolherFoto(
    onTirarFoto: () -> Unit,
    onEscolherGaleria: () -> Unit,
    onFechar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onFechar,
        containerColor = Color.White,
        confirmButton = {},
        title = {
            Text(
                text = "Foto do banheiro",
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = DialogoTituloTexto
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                OpcaoDialogoFoto(
                    icone = Icons.Default.CameraAlt,
                    texto = "Tirar foto",
                    onClick = onTirarFoto
                )
                OpcaoDialogoFoto(
                    icone = Icons.Default.PhotoLibrary,
                    texto = "Escolher da galeria",
                    onClick = onEscolherGaleria
                )
            }
        }
    )
}

@Composable
private fun OpcaoDialogoFoto(icone: androidx.compose.ui.graphics.vector.ImageVector, texto: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = FotoTexto,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = texto,
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = DialogoOpcaoTexto
        )
    }
}

@Preview(showBackground = true, widthDp = 402, heightDp = 874)
@Composable
private fun AvaliacaoScreenPreview() {
    PeeGoTheme {
        AvaliacaoScreen()
    }
}