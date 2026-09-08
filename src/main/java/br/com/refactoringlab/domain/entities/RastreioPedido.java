package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import java.time.LocalDateTime;

public class RastreioPedido {

    private String id;
    private String pedidoId;
    private StatusPedido statusPedido;
    private StatusOcorrencia statusOcorrencia;
    private String descricao;
    private String usuarioId;
    private LocalDateTime dataHora;

    public RastreioPedido() {
    }

    public RastreioPedido(String pedidoId, StatusPedido statusPedido, StatusOcorrencia statusOcorrencia, String descricao, String usuarioId, LocalDateTime dataHora) {
        this.pedidoId = pedidoId;
        this.statusPedido = statusPedido;
        this.statusOcorrencia = statusOcorrencia;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
        this.dataHora = dataHora != null ? dataHora : LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public StatusOcorrencia getStatusOcorrencia() {
        return statusOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}

