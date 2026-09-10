package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusRoteiro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Roteiro {

    private String id;
    private String codigoRoteiro;
    private String motoristaId;
    private String veiculoPlaca;
    private StatusRoteiro statusRoteiro;
    private List<String> pedidosIds;
    private LocalDateTime dataCriacao;

    public Roteiro() {
        this.dataCriacao = LocalDateTime.now();
        this.statusRoteiro = StatusRoteiro.EM_ANDAMENTO;
        this.pedidosIds = new ArrayList<>();
    }

    public Roteiro(String codigoRoteiro, String motoristaId, String veiculoPlaca, List<String> pedidosIds) {
        this.codigoRoteiro = codigoRoteiro;
        this.motoristaId = motoristaId;
        this.veiculoPlaca = veiculoPlaca;
        this.pedidosIds = pedidosIds;
    }

    public void adicionarPedido(String pedidoId) {
        validarSePodeModificar();
        if (pedidoId != null && !pedidoId.isBlank() && !this.pedidosIds.contains(pedidoId)) {
            this.pedidosIds.add(pedidoId);
        }
    }

    public void removerPedido(String pedidoId) {
        validarSePodeModificar();
        this.pedidosIds.remove(pedidoId);
    }

    public void finalizarRoteiro() {
        if (this.statusRoteiro == StatusRoteiro.CANCELADO) {
            throw new IllegalStateException("Roteiros cancelados não podem ser finalizados.");
        }
        this.statusRoteiro = StatusRoteiro.FINALIZADO;
    }

    public void cancelarRoteiro() {
        if (this.statusRoteiro == StatusRoteiro.FINALIZADO) {
            throw new IllegalStateException("Roteiros já finalizados não podem ser cancelados.");
        }
        this.statusRoteiro = StatusRoteiro.CANCELADO;
    }

    private void validarSePodeModificar() {
        if (this.statusRoteiro != null && this.statusRoteiro.isFinalizado()) {
            throw new IllegalStateException("O roteiro " + this.codigoRoteiro + " já está finalizado ou cancelado.");
        }
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoRoteiro() {
        return codigoRoteiro;
    }

    public void setCodigoRoteiro(String codigoRoteiro) {
        this.codigoRoteiro = codigoRoteiro;
    }

    public String getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(String motoristaId) {
        this.motoristaId = motoristaId;
    }

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }

    public StatusRoteiro getStatusRoteiro() {
        return statusRoteiro;
    }

    public void setStatusRoteiro(StatusRoteiro statusRoteiro) {
        this.statusRoteiro = statusRoteiro;
    }

    public List<String> getPedidosIds() {
        return pedidosIds != null ? Collections.unmodifiableList(pedidosIds) : Collections.emptyList();
    }

    public void setPedidosIds(List<String> pedidosIds) {
        this.pedidosIds = pedidosIds != null ? new ArrayList<>(pedidosIds) : new ArrayList<>();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roteiro roteiro = (Roteiro) o;
        return Objects.equals(id, roteiro.id) && Objects.equals(codigoRoteiro, roteiro.codigoRoteiro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoRoteiro);
    }
}
