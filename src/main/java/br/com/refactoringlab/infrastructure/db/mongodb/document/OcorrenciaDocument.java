package br.com.refactoringlab.infrastructure.db.mongodb.document;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ocorrencias")
public class OcorrenciaDocument {

    @Id
    private String id;
    private String pedidoId;
    private StatusPedido statusPedido;
    private StatusOcorrencia statusOcorrencia;
    private String motivo;
    private String usuarioId;
    private LocalDateTime dataOcorrencia;

    public OcorrenciaDocument() {}

    public OcorrenciaDocument(String pedidoId, StatusPedido statusPedido, StatusOcorrencia statusOcorrencia, String motivo, String usuarioId, LocalDateTime dataOcorrencia) {
        this.pedidoId = pedidoId;
        this.statusPedido = statusPedido;
        this.statusOcorrencia = statusOcorrencia;
        this.motivo = motivo;
        this.usuarioId = usuarioId;
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

    public StatusPedido getStatusPedido() { return statusPedido; }
    public void setStatusPedido(StatusPedido statusPedido) { this.statusPedido = statusPedido; }

    public StatusOcorrencia getStatusOcorrencia() { return statusOcorrencia; }
    public void setStatusOcorrencia(StatusOcorrencia statusOcorrencia) { this.statusOcorrencia = statusOcorrencia; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public LocalDateTime getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDateTime dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
}