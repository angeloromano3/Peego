package br.com.fiap.peego.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val EsquemaCoresEscuro = darkColorScheme(
    primary = VerdePrimario,
    secondary = VerdePrimarioEscuro,
    tertiary = VerdeStatus,
    background = Color(0xFF1B1B1B),
    surface = Color(0xFF1B1B1B)
)

private val EsquemaCoresClaro = lightColorScheme(
    primary = VerdePrimario,
    secondary = VerdePrimarioEscuro,
    tertiary = VerdeStatus,
    background = FundoMenta,
    surface = CardBranco,
    onPrimary = CardBranco,
    onSecondary = CardBranco,
    onBackground = TextoPrimario,
    onSurface = TextoPrimario
)

@Composable
fun AcessaBanheiroTheme(
    temaEscuro: Boolean = isSystemInDarkTheme(),
    // Desativado por padrão: o app tem paleta própria (verde-petróleo/menta),
    // não queremos que o Android 12+ substitua pelas cores do papel de parede do usuário
    corDinamica: Boolean = false,
    content: @Composable () -> Unit
) {
    val esquemaCores = when {
        corDinamica && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (temaEscuro) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        temaEscuro -> EsquemaCoresEscuro
        else -> EsquemaCoresClaro
    }

    MaterialTheme(
        colorScheme = esquemaCores,
        typography = Typography,
        content = content
    )
}