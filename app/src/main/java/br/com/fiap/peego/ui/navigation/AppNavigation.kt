package br.com.fiap.peego.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.peego.ui.screens.CreateAccountScreen
import br.com.fiap.peego.ui.screens.LocationPermissionScreen
import br.com.fiap.peego.ui.screens.LoginScreen
import br.com.fiap.peego.ui.viewmodel.AuthUiState
import br.com.fiap.peego.ui.viewmodel.AuthViewModel
import br.com.fiap.peego.model.BathroomDetail
import br.com.fiap.peego.model.CondicoesRecentes
import br.com.fiap.peego.model.Informacoes
import br.com.fiap.peego.ui.screens.bathroomdetail.BathroomDetailScreen
import br.com.fiap.peego.ui.screens.avaliacao.AvaliacaoScreen

object Rotas {
    const val LOGIN = "login"
    const val CRIAR_CONTA = "criarconta"
    const val PERMISSAO_LOCALIZACAO = "permissao_localizacao"
    const val DETALHES_BANHEIRO = "detalhes_banheiro"
    const val AVALIACAO = "avaliacao"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Rotas.LOGIN,
        modifier = modifier
    ) {
        composable(Rotas.LOGIN) {
            val authViewModel: AuthViewModel = viewModel()
            val uiState by authViewModel.uiState.collectAsState()

            LaunchedEffect(uiState) {
                when (val estado = uiState) {
                    is AuthUiState.Sucesso -> {
                        Toast.makeText(context, "Login realizado!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Rotas.PERMISSAO_LOCALIZACAO)
                        authViewModel.resetarEstado()
                    }
                    is AuthUiState.Erro -> {
                        Toast.makeText(context, "Erro: ${estado.mensagem}", Toast.LENGTH_LONG).show()
                        authViewModel.resetarEstado()
                    }
                    else -> {}
                }
            }

            LoginScreen(
                entrar = { email, senha ->
                    if (email.isBlank() || senha.isBlank()) {
                        Toast.makeText(context, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show()
                        return@LoginScreen
                    }
                    authViewModel.login(email, senha)
                },
                entrarGoogle = {
                    authViewModel.loginComGoogle(context)
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
            val authViewModel: AuthViewModel = viewModel()
            val uiState by authViewModel.uiState.collectAsState()

            LaunchedEffect(uiState) {
                when (val estado = uiState) {
                    is AuthUiState.Sucesso -> {
                        Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                        authViewModel.resetarEstado()
                    }
                    is AuthUiState.Erro -> {
                        Toast.makeText(context, "Erro: ${estado.mensagem}", Toast.LENGTH_LONG).show()
                        authViewModel.resetarEstado()
                    }
                    else -> {}
                }
            }

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
                    authViewModel.criarConta(email, senha)
                },
                entrarGoogle = {
                    authViewModel.loginComGoogle(context)
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
                voltar = { navController.popBackStack() },
                avaliarBanheiro = { navController.navigate(Rotas.AVALIACAO) }
            )
        }

        composable(Rotas.AVALIACAO) {
            AvaliacaoScreen(
                nomeBanheiro = "Banheiro Extra Mercado - Campo Limpo",
                voltar = { navController.popBackStack() },
                aoPublicar = { _, _, _, _, _ ->
                    Toast.makeText(context, "Avaliação publicada. Obrigado!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            )
        }

    }
}