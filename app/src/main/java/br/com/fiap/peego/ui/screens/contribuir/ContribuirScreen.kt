package br.com.fiap.peego.ui.screens.contribuir

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import br.com.fiap.peego.ui.theme.Poppins
import br.com.fiap.peego.ui.theme.VerdePrimario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContribuirScreen() {
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var isAcessivel by remember { mutableStateOf(false) }
    var isGratuito by remember { mutableStateOf(true) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var showSnackbar by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> photoUri = uri }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("Banheiro cadastrado com sucesso!")
            showSnackbar = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.contribuir_title), fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text(stringResource(R.string.contribuir_nome_label), fontFamily = Poppins) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
            OutlinedTextField(value = endereco, onValueChange = { endereco = it }, label = { Text(stringResource(R.string.contribuir_endereco_label), fontFamily = Poppins) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isAcessivel, onCheckedChange = { isAcessivel = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = VerdePrimario))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.contribuir_acessivel), fontFamily = Poppins, fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isGratuito, onCheckedChange = { isGratuito = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = VerdePrimario))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.contribuir_gratuito), fontFamily = Poppins, fontSize = 14.sp)
                }
            }

            Text(stringResource(R.string.contribuir_foto_label), fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp)

            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(16.dp)).border(2.dp, Color(0xFFD0D0D0), RoundedCornerShape(16.dp)).background(Color(0xFFF5F5F5)).clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (photoUri != null) {
                    AsyncImage(model = photoUri, contentDescription = stringResource(R.string.contribuir_foto_label), contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = Color(0xFF7A7A7A), modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(8.dp))
                        Text(stringResource(R.string.contribuir_foto_label), color = Color(0xFF7A7A7A), fontFamily = Poppins, fontSize = 14.sp)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { showSnackbar = true; nome = ""; endereco = ""; isAcessivel = false; isGratuito = true; photoUri = null },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                enabled = nome.isNotBlank() && endereco.isNotBlank()
            ) {
                Text(stringResource(R.string.contribuir_enviar), fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}
