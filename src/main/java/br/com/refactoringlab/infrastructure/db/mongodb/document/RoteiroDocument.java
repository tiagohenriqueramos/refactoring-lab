package br.com.refactoringlab.infrastructure.db.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "roteiros")
public class RoteiroDocument {

    @Id
    private String id;
    private String codigoRoteiro;
    private String motoristaId;
    private String veiculoPlaca;
    private String statusRoteiro;
    private List<String> pedidosIds;
    private LocalDateTime dataCriacao;

    public RoteiroDocument() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCodigoRoteiro() { return codigoRoteiro; }
    public void setCodigoRoteiro(String codigoRoteiro) { this.codigoRoteiro = codigoRoteiro; }

    public String getMotoristaId() { return motoristaId; }
    public void setMotoristaId(String motoristaId) { this.motoristaId = motoristaId; }

    public String getVeiculoPlaca() { return veiculoPlaca; }
    public void setVeiculoPlaca(String veiculoPlaca) { this.veiculoPlaca = veiculoPlaca; }

    public String getStatusRoteiro() { return statusRoteiro; }
    public void setStatusRoteiro(String statusRoteiro) { this.statusRoteiro = statusRoteiro; }

    public List<String> getPedidosIds() { return pedidosIds; }
    public void setPedidosIds(List<String> pedidosIds) { this.pedidosIds = pedidosIds; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}