package br.com.fiap.acessabanheiro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.acessabanheiro.ui.theme.AcessaBanheiroTheme
import br.com.fiap.acessabanheiro.ui.theme.FundoMenta
import br.com.fiap.acessabanheiro.ui.theme.VerdePrimario
import br.com.fiap.acessabanheiro.ui.theme.TextoSecundario

@Composable
fun LoginScreen(
    entrar: (email: String, senha: String) -> Unit = { email, senha -> },
    entrarGoogle: () -> Unit = {},
    criarConta: () -> Unit = {},
    esqueciSenha: () -> Unit = {}
) {
    var email by remember {
        mutableStateOf("") }

    var senha by remember {
        mutableStateOf("") }

    var senhaVisivel by remember {
        mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMenta)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = VerdePrimario,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "PeeGo",
            style = MaterialTheme.typography.titleLarge,
            color = VerdePrimario
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Encontre banheiros públicos acessíveis perto de você com facilidade",
            fontSize = 14.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "ENTRAR NA SUA CONTA",
                style = MaterialTheme.typography.labelSmall,
                color = TextoSecundario
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esqueci minha senha",
                fontSize = 13.sp,
                color = VerdePrimario,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = esqueciSenha)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { entrar (email, senha) },
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Entrar", fontSize = 16.sp)
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
            Text("Entrar com Google")
        }

        Spacer(modifier = Modifier
            .height(20.dp))

        Row {
            Text("Não tem uma conta? ",
                fontSize = 13.sp,
                color = TextoSecundario)
            Text(
                text = "Criar conta",
                fontSize = 13.sp,
                color = VerdePrimario,
                modifier = Modifier
                    .clickable(onClick = criarConta)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    AcessaBanheiroTheme {
        LoginScreen()
    }
}