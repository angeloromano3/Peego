package br.com.fiap.peego.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.peego.ui.theme.PeeGoTheme
import br.com.fiap.peego.ui.theme.FundoMenta
import br.com.fiap.peego.ui.theme.VerdePrimario
import br.com.fiap.peego.ui.theme.TextoSecundario

@Composable
fun CreateAccountScreen(
    voltar: () -> Unit = {},
    criarConta: (nome: String, email: String, senha: String, confirmarSenha: String) -> Unit =
        { _, _, _, _ -> },
    entrarGoogle: () -> Unit = {},
    jaTemConta: () -> Unit = {}
) {
    var nome by remember {
        mutableStateOf("") }

    var email by remember {
        mutableStateOf("") }

    var senha by remember {
        mutableStateOf("") }

    var confirmarSenha by remember {
        mutableStateOf("") }

    var senhaVisivel by remember {
        mutableStateOf(false) }

    var confirmarSenhaVisivel by remember {
        mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMenta)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = voltar)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = VerdePrimario,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Voltar", color = VerdePrimario, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = VerdePrimario,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Criar conta",
                    style = MaterialTheme.typography.titleLarge,
                    color = VerdePrimario
                )
                Text(
                    text = "Crie sua conta para começar a usar o PeeGo",
                    fontSize = 13.sp,
                    color = TextoSecundario
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "SUAS INFORMAÇÕES",
                style = MaterialTheme.typography.labelSmall,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                placeholder = { Text("Nome completo") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("E-mail") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                placeholder = { Text("Senha") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (senhaVisivel) "Ocultar senha" else "Mostrar senha",
                            tint = TextoSecundario
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmarSenha,
                onValueChange = { confirmarSenha = it },
                placeholder = { Text("Confirmar senha") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation =
                    if (confirmarSenhaVisivel) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmarSenhaVisivel = !confirmarSenhaVisivel }) {
                        Icon(
                            imageVector =
                                if (confirmarSenhaVisivel) Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility,
                            contentDescription =
                                if (confirmarSenhaVisivel) "Ocultar senha" else "Mostrar senha",
                            tint = TextoSecundario
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { criarConta(
                    nome,
                    email,
                    senha,
                    confirmarSenha) },
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Criar conta", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "  ou continue com  ",
                fontSize = 12.sp,
                color = TextoSecundario
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = entrarGoogle,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("G", color = Color(0xFF4285F4))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continuar com Google")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Já tem uma conta? ", fontSize = 13.sp, color = TextoSecundario)
            Text(
                text = "Entrar",
                fontSize = 13.sp,
                color = VerdePrimario,
                modifier = Modifier.clickable(onClick = jaTemConta)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = false)
@Composable
private fun CreateAccountScreenPreview() {
    PeeGoTheme {
        CreateAccountScreen()
    }
}

