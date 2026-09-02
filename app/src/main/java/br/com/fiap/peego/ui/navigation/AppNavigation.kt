package br.com.fiap.peego.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.peego.ui.screens.CreateAccountScreen
import br.com.fiap.peego.ui.screens.LocationPermissionScreen
import br.com.fiap.peego.ui.screens.LoginScreen
import br.com.fiap.peego.ui.viewmodel.AuthUiState
import br.com.fiap.peego.ui.viewmodel.AuthViewModel
import br.com.fiap.peego.model.paraDetail
import br.com.fiap.peego.ui.screens.bathroomdetail.BathroomDetailScreen
import br.com.fiap.peego.ui.screens.avaliacao.AvaliacaoScreen
import br.com.fiap.peego.ui.screens.explore.ExploreScreen
import br.com.fiap.peego.ui.screens.explore.ExploreViewModel
import br.com.fiap.peego.ui.screens.lista.ListaScreen
import br.com.fiap.peego.ui.screens.contribuir.ContribuirScreen
import br.com.fiap.peego.ui.screens.FilterScreen

object Rotas {
    const val LOGIN = "login"
    const val CRIAR_CONTA = "criarconta"
    const val PERMISSAO_LOCALIZACAO = "permissao_localizacao"
    const val DETALHES_BANHEIRO = "detalhes_banheiro"
    const val AVALIACAO = "avaliacao"
    const val EXPLORAR = "explorar"
    const val LISTA = "lista"
    const val CONTRIBUIR = "contribuir"
    const val FILTROS = "filtros"
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
                    navController.navigate(Rotas.EXPLORAR) {
                        popUpTo(Rotas.PERMISSAO_LOCALIZACAO) { inclusive = true }
                    }
                },
                buscarPorEndereco = {
                    navController.navigate(Rotas.EXPLORAR) {
                        popUpTo(Rotas.PERMISSAO_LOCALIZACAO) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "${Rotas.DETALHES_BANHEIRO}/{bathroomId}",
            arguments = listOf(navArgument("bathroomId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bathroomId = backStackEntry.arguments?.getString("bathroomId")
            val exploreViewModel: ExploreViewModel = viewModel()
            val uiState by exploreViewModel.uiState.collectAsState()
            val bathroom = uiState.bathrooms.find { it.id == bathroomId }

            if (bathroom != null) {
                BathroomDetailScreen(
                    detalhe = bathroom.paraDetail(),
                    voltar = { navController.popBackStack() },
                    avaliarBanheiro = { navController.navigate(Rotas.AVALIACAO) }
                )
            }
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

        composable(Rotas.EXPLORAR) {
            ExploreScreen(navController = navController)
        }

        composable(Rotas.LISTA) {
            ListaScreen(navController = navController)
        }

        composable(Rotas.CONTRIBUIR) {
            ContribuirScreen(voltar = { navController.popBackStack() })
        }

        composable(Rotas.FILTROS) {
            FilterScreen(
                voltar = { navController.popBackStack() },
                aplicarFiltros = { _, _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

    }
}