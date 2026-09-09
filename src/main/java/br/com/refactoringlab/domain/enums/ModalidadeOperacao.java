package br.com.refactoringlab.domain.enums;

public enum ModalidadeOperacao {
    OUTBOUND("Fluxo normal de entrega"),
    INBOUND_DROP_OFF("Fluxo de devolução via entrega em ponto/loja pelo cliente"),
    INBOUND_PICK_UP("Fluxo de devolução via coleta domiciliar pelo motorista");

    private final String descricao;

    ModalidadeOperacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}