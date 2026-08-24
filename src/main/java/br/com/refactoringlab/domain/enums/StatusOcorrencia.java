package br.com.refactoringlab.domain.enums;

public enum StatusOcorrencia {
    RECEBIDO_CD("Recebido no centro de distribuição"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entrega realizada com sucesso"),
    EXTRAVIO("Extravio de carga"),
    AVARIA("Avaria no produto"),
    FURTADO("Carga furtada"),
    DEVOLUCAO("Pedido devolvido ao remetente"),
    INSUCESSO_ENDERECO_NAO_ENCONTRADO("Endereço não encontrado"),
    INSUCESSO_AUSENTE("Cliente ausente"),
    INSUCESSO_RECUSADO("Entrega recusada pelo destinatário");

    private final String descricao;

    StatusOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}