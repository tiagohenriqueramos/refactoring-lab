package br.com.refactoringlab.domain.enums;

public enum StatusOcorrencia {
    // --- Outbound (Fluxo Normal de Entrega) ---
    RECEBIDO_CD("Recebido no centro de distribuição"),
    EM_TRANSITO("Em trânsito para entrega"),
    COLETADO_MOTORISTA_EMBARCADOR("Coletado pelo motorista no embarcador/seller"),
    ENTREGUE_PROPRIO_DESTINATARIO("Entrega realizada ao próprio destinatário"),
    ENTREGUE_TERCEIROS("Entrega realizada a terceiros (portaria/vizinho)"),

    // --- Inbound / Reversa (Solicitação) ---
    DEVOLUCAO_SOLICITADA_CLIENTE("Devolução solicitada pelo cliente via portal/app"),
    DEVOLUCAO_SOLICITADA_SAC("Devolução solicitada via atendimento/SAC"),

    // --- Reversa - Carga Pesada (Pick-up / Coleta Domiciliar) ---
    COLETADO_MOTORISTA_CLIENTE_REVERSA("Coletado pelo motorista na residência do cliente"),
    INSUCESSO_COLETA_REVERSA_AUSENTE("Cliente ausente na tentativa de coleta domiciliar"),
    INSUCESSO_COLETA_REVERSA_EMBALAGEM("Produto fora dos padrões de embalagem para reversa"),
    INSUCESSO_COLETA_REVERSA_RECUSADA("Cliente desistiu da devolução / recusou entregar pacote"),

    // --- Reversa - Carga Leve (Drop-off em Loja / Ponto Parceiro) ---
    RECEBIDO_PONTO_COLETA_LOJA("Entregue pelo cliente na loja ou ponto de coleta"),
    INSUCESSO_RECEBIMENTO_LOJA_EMBALAGEM("Recusado na loja por inconformidade do produto/embalagem"),

    // --- Insucessos de Entrega (Outbound) ---
    INSUCESSO_ENDERECO_NAO_ENCONTRADO("Endereço não encontrado"),
    INSUCESSO_DESTINATARIO_AUSENTE("Cliente ausente na tentativa de entrega"),
    INSUCESSO_RECUSADO_DESTINATARIO("Entrega recusada pelo destinatário"),
    INSUCESSO_ESTABELECIMENTO_FECHADO("Estabelecimento comercial fechado"),

    // --- Retorno & Finalização de Reversa ---
    DEVOLVIDO_CD_ORIGEM("Devolvido ao centro de distribuição de origem"),
    DEVOLVIDO_SELLER_EMBARCADOR("Devolvido ao seller/embarcador original"),

    // --- Sinistros ---
    EXTRAVIO("Extravio de carga"),
    AVARIA("Avaria no produto"),
    FURTADO("Carga furtada"),

    ESTORNO_APONTAMENTO_INCORRETO("Estorno de apontamento operacional incorreto");

    private final String descricao;

    StatusOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}