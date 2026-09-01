# Banheiro Livre — Tela "Explorar"

Projeto Android (Kotlin + Jetpack Compose) reproduzindo a tela de referência
(mapa + busca + "Banheiros próximos" + navegação inferior), usando um
**mapa 100% gratuito** (OpenStreetMap via osmdroid — sem chave de API,
sem conta de faturamento).

## Pilha usada nesta parte
- Kotlin + Jetpack Compose (UI) + Compose Animations (`animateContentSize`,
  `AnimatedVisibility`, `Crossfade`)
- Coil (`AsyncImage`) para a foto do estabelecimento
- Navigation Compose (rotas Explorar/Lista/Contribuir + 1 Deep Link de exemplo)
- MVVM: `ExploreViewModel` (AndroidViewModel) + Coroutines + `StateFlow`/`Flow`
- Room (SQLite) como cache local, observado via `Flow`
- Retrofit + Gson já configurados (`RetrofitInstance`), prontos para apontar
  para o back-end real (H2 Database) quando ele existir — por enquanto,
  o `BathroomRepository` usa dados de exemplo (`MockBathroomData`)
- 1 teste JUnit (`src/test`) e 1 teste de UI com Compose Test Rule (`src/androidTest`)

## Passo a passo para importar no Android Studio

1. **Baixe e extraia** o arquivo `PeeGo.zip` em qualquer pasta do seu computador.
2. Abra o **Android Studio** → tela inicial → **Open** (ou **File > Open**).
3. Selecione a pasta **raiz extraída** (a que contém `settings.gradle.kts`) — não abra a pasta `app`.
4. Aguarde o **Gradle Sync** (barra de progresso embaixo). Na primeira vez ele baixa
   as dependências (Compose, Room, Retrofit, osmdroid, Coil) — precisa de internet.
5. Se aparecer um aviso pedindo para atualizar o **Android Gradle Plugin** ou o
   **Gradle Wrapper**, aceite a sugestão do próprio Android Studio (ele gera o
   `gradle-wrapper` automaticamente na primeira abertura).
6. Crie/abra um **emulador** (Device Manager → Create Device) ou conecte um
   celular Android com Depuração USB ativada.
7. Clique no botão verde **Run ▶** (ou `Shift+F10`).
8. O app abre direto na tela **Explorar**, já com o mapa OpenStreetMap
   centralizado em São Paulo e 3 banheiros de exemplo.

### Testando o Deep Link pelo ADB
Com o app instalado no emulador, rode no terminal:
```bash
adb shell am start -a android.intent.action.VIEW -d "peego://detalhe/1"
```

### Rodando os testes
- Teste unitário (JUnit): botão direito em `ExploreViewModelTest.kt` → **Run**.
- Teste de UI (Compose Test Rule / instrumentado): botão direito em
  `ExploreScreenTest.kt` → **Run** (precisa de emulador/dispositivo ligado).

## Próximos passos sugeridos
- Trocar `MockBathroomData` pela chamada real via `RetrofitInstance.api`
  quando o back-end (H2 Database) estiver no ar.
- Adicionar `DataStore`/`SharedPreferences` para lembrar o último filtro usado.
- Ligar `Firebase Authentication` (login) e `Firebase Realtime Database`
  (sincronizar banheiros cadastrados por usuários em tempo real) na aba
  **Contribuir**.
- Usar KSP (já configurado no `build.gradle.kts`) para gerar o código do Room —
  não precisa fazer nada extra, ele já roda automaticamente no build.
