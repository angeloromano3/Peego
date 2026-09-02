package br.com.fiap.peego.model

/**
 * Converte o modelo usado na tela de Lista (Bathroom) para o modelo
 * usado na tela de Detalhes (BathroomDetail).
 *
 * A tela de Lista ainda não tem todas as informações que a tela de
 * Detalhes mostra (ex.: acessibilidade detalhada, condições recentes,
 * horário de funcionamento, endereço) — por isso os campos abaixo
 * marcados como "placeholder" ficam com um valor genérico até que
 * a API real traga esses dados também para a Lista, ou até que a tela
 * de Detalhes busque o restante da informação por conta própria.
 */
fun Bathroom.paraDetail(): BathroomDetail = BathroomDetail(
    nome = name,
    avaliacao = rating,
    quantidadeAvaliacoes = 0, // placeholder: Bathroom ainda não traz essa contagem
    distancia = "${distanceMeters}m",
    aberto = isOpenNow,
    imagemUrl = imageUrl,
    acessibilidade = if (isAccessible) listOf("Acessível para cadeirante") else emptyList(),
    inclusao = emptyList(), // placeholder: Bathroom ainda não traz esses dados
    condicoesRecentes = CondicoesRecentes(
        limpeza = "—",
        seguranca = "—",
        iluminacao = "—",
        aguaDisponivel = "—",
        atualizadoHaTempo = "Sem atualizações recentes"
    ), // placeholder: sem dados de condições recentes ainda
    informacoes = Informacoes(
        funcionamento = "—", // placeholder: Bathroom ainda não traz horário
        gratuito = isFree,
        localizacao = "—" // placeholder: Bathroom ainda não traz endereço detalhado
    )
)