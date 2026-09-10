package br.com.refactoringlab.domain.enums;

public enum StatusRoteiro {
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusRoteiro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isFinalizado() {
        return this == FINALIZADO || this == CANCELADO;
    }
}
