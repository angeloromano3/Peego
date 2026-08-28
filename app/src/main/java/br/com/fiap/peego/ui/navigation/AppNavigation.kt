package br.com.fiap.peego.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.peego.ui.screens.CreateAccountScreen
import br.com.fiap.peego.ui.screens.LocationPermissionScreen
import br.com.fiap.peego.ui.screens.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import br.com.fiap.peego.model.BathroomDetail
import br.com.fiap.peego.model.CondicoesRecentes
import br.com.fiap.peego.model.Informacoes
import br.com.fiap.peego.ui.screens.bathroomdetail.BathroomDetailScreen

object Rotas {
    const val LOGIN = "login"
    const val CRIAR_CONTA = "criarconta"
    const val PERMISSAO_LOCALIZACAO = "permissao_localizacao"
    const val DETALHES_BANHEIRO = "detalhes_banheiro"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()

    NavHost(
        navController = navController,
        startDestination = Rotas.LOGIN,
        modifier = modifier
    ) {
        composable(Rotas.LOGIN) {
            LoginScreen(
                entrar = { email, senha ->
                    if (email.isBlank() || senha.isBlank()) {
                        Toast.makeText(context, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show()
                        return@LoginScreen
                    }
                    auth.signInWithEmailAndPassword(email, senha)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Login realizado!", Toast.LENGTH_SHORT).show()
                            // TODO: navegar pra tela Home quando ela existir
                        }
                        .addOnSuccessListener {
                            navController.navigate(Rotas.PERMISSAO_LOCALIZACAO)
                        }
                },
                entrarGoogle = {
                    // TODO: disparar fluxo de Google Sign-In
                },
                criarConta = {
                    navController.navigate(Rotas.CRIAR_CONTA)
                },
                esqueciSenha = {
                    // TODO: FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                }
            )
        }

        composable(Rotas.CRIAR_CONTA) {
            CreateAccountScreen(
                voltar = {
                    navController.popBackStack()
                },
                criarConta = { nome, email, senha, confirmarSenha ->
                    if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                        return@CreateAccountScreen
                    }
                    if (senha != confirmarSenha) {
                        Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                        return@CreateAccountScreen
                    }
                    auth.createUserWithEmailAndPassword(email, senha)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // volta pro Login
                        }
                        .addOnFailureListener { erro ->
                            Toast.makeText(context, "Erro: ${erro.message}", Toast.LENGTH_LONG).show()
                        }
                },
                entrarGoogle = {
                    // TODO: disparar fluxo de Google Sign-In
                },
                jaTemConta = {
                    navController.popBackStack()
                }
            )
        }

        composable(Rotas.PERMISSAO_LOCALIZACAO) {
            LocationPermissionScreen(
                permitirLocalizacao = {
                    navController.navigate(Rotas.DETALHES_BANHEIRO)
                },
                buscarPorEndereco = {
                    navController.navigate(Rotas.DETALHES_BANHEIRO)
                }
            )
        }
        composable(Rotas.DETALHES_BANHEIRO) {
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
                        limpeza = "Boa", seguranca = "Boa", iluminacao = "Boa",
                        aguaDisponivel = "Sim", atualizadoHaTempo = "há 2 horas"
                    ),
                    informacoes = Informacoes(
                        funcionamento = "06h–22h", gratuito = true,
                        localizacao = "Parque Ibirapuera — Portão 3"
                    )
                ),
                voltar = { navController.popBackStack() }
            )
        }

    }
}
