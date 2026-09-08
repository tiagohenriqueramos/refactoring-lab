package br.com.refactoringlab.domain.enums;

public enum StatusPedido {
    RECEBIDO("Recebido"),
    EM_TRANSITO("Em trânsito"),
    EM_RETORNO("Em processo de retorno"),
    DEVOLVIDO("Devolvido"),
    DEVOLUCAO_SOLICITADA("Devolução solicitada pelo cliente"),
    CANCELADO("Cancelado"),
    SINISTRO("Sinistrado"),
    INSUCESSO("Insucesso"),
    ENTREGUE("Entregue");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isFinalizado() {
        return this == ENTREGUE || this == DEVOLVIDO || this == CANCELADO || this == SINISTRO;
    }
}