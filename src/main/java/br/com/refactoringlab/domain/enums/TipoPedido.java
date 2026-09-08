package br.com.refactoringlab.domain.enums;

public enum TipoPedido {
    ENTREGA_DIRETA_LEVE("Entrega direta - Carga Leve", false, ModalidadeOperacao.OUTBOUND),
    ENTREGA_DIRETA_PESADO("Entrega direta - Carga Pesada", false, ModalidadeOperacao.OUTBOUND),
    REVERSA_LEVE_LOJA("Reversa - Drop-off em Loja/Ponto", true, ModalidadeOperacao.INBOUND_DROP_OFF),
    REVERSA_PESADA_COLETA("Reversa - Coleta Domiciliar pelo Motorista", true, ModalidadeOperacao.INBOUND_PICK_UP);

    private final String descricao;
    private final boolean isDevolucao;
    private final ModalidadeOperacao modalidade;

    TipoPedido(String descricao, boolean isDevolucao, ModalidadeOperacao modalidade) {
        this.descricao = descricao;
        this.isDevolucao = isDevolucao;
        this.modalidade = modalidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isDevolucao() {
        return isDevolucao;
    }

    public ModalidadeOperacao getModalidade() {
        return modalidade;
    }

    // Regra de Domínio: Apenas reversas pesadas geram parada no roteiro de coleta do motorista
    public boolean exigeRoteirizacaoColetaMotorista() {
        return this == REVERSA_PESADA_COLETA;
    }
}