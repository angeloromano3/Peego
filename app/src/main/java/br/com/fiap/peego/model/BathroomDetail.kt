package br.com.fiap.peego.model

data class BathroomDetail(
    val nome: String,
    val avaliacao: Double,
    val quantidadeAvaliacoes: Int,
    val distancia: String,
    val aberto: Boolean,
    val imagemUrl: String,
    val acessibilidade: List<String>,
    val inclusao: List<String>,
    val condicoesRecentes: CondicoesRecentes,
    val informacoes: Informacoes
)

data class CondicoesRecentes(
    val limpeza: String,
    val seguranca: String,
    val iluminacao: String,
    val aguaDisponivel: String,
    val atualizadoHaTempo: String
)

data class Informacoes(
    val funcionamento: String,
    val gratuito: Boolean,
    val localizacao: String
)