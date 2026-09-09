package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.ModalidadeOperacao;
import br.com.refactoringlab.domain.enums.TipoPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pedido {

    // --- Identificadores Principais ---
    private String id;
    private Long codigoInterno;
    private String clienteGuid;
    private String clienteNome;
    private String codigoRoteiro;
    private String codigoRastreio;

    // --- Documentação Fiscal / Cliente ---
    private String numeroNotaFiscal;
    private String chaveNfe;

    // --- Máquina de Estados e Ocorrência ---
    private StatusPedido statusPedido;
    private StatusOcorrencia statusUltimaOcorrencia;

    // --- Destinatário (Quem recebe o pacote) ---
    private String nomeDestinatario;
    private String cpfCnpjDestinatario;
    private Endereco enderecoDestinatario;

    // --- Remetente (Quem enviou o pacote) ---
    private String nomeRemetente;
    private String cpfCnpjRemetente;
    private Endereco enderecoRemetente;

    // --- Centro de Distribuição (Hub para devolução/retorno) ---
    private String codigoCentroDistribuicao;
    private String nomeCentroDistribuicao;
    private Endereco enderecoCentroDistribuicao;

    // --- Datas e Controle Operacional ---
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPrometidaEntrega;
    private LocalDateTime dataUltimoStatus;
    private Integer quantidadeTentativasEntrega;

    private TipoPedido tipoPedido;
    private ModalidadeOperacao modalidadeOperacao;

    public Pedido() {
        this.dataCriacao = LocalDateTime.now();
        this.quantidadeTentativasEntrega = 0;
    }

    public Pedido(String id, Long codigoInterno, String clienteGuid, StatusPedido statusPedido) {
        this();
        this.id = id;
        this.codigoInterno = codigoInterno;
        this.clienteGuid = clienteGuid;
        this.statusPedido = statusPedido;
        this.dataUltimoStatus = LocalDateTime.now();
    }

    public void atualizarStatus(StatusPedido novoStatus, StatusOcorrencia ocorrencia, String motivo, String usuarioId) {
        validarSePodeAlterarStatus();

        this.statusPedido = novoStatus;
        this.statusUltimaOcorrencia = ocorrencia;
        this.dataUltimoStatus = LocalDateTime.now();

        if (ehOcorrenciaDeInsucesso(ocorrencia)) {
            this.quantidadeTentativasEntrega++;
        }
    }

    public void registrarTratativaEncerramento(StatusPedido novoStatus, StatusOcorrencia ocorrencia, String usuarioId) {
        atualizarStatus(novoStatus, ocorrencia, null, usuarioId);
    }

    public void estornarTratativaIndevida(StatusPedido statusAnterior, String motivoEstorno, String usuarioId) {
        if (this.statusPedido == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedidos cancelados não podem ter status estornados.");
        }

        if (ehOcorrenciaDeInsucesso(this.statusUltimaOcorrencia) && this.quantidadeTentativasEntrega > 0) {
            this.quantidadeTentativasEntrega--;
        }

        this.statusPedido = statusAnterior;
        this.statusUltimaOcorrencia = StatusOcorrencia.ESTORNO_APONTAMENTO_INCORRETO;
        this.dataUltimoStatus = LocalDateTime.now();

    }

    private void validarSePodeAlterarStatus() {
        if (this.statusPedido != null && this.statusPedido.isFinalizado()) {
            throw new IllegalStateException("O pedido " + this.id + " já está finalizado.");
        }
    }

    private boolean ehOcorrenciaDeInsucesso(StatusOcorrencia ocorrencia) {
        return ocorrencia != null && ocorrencia.name().startsWith("INSUCESSO_");
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(Long codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getClienteGuid() {
        return clienteGuid;
    }

    public void setClienteGuid(String clienteGuid) {
        this.clienteGuid = clienteGuid;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getCodigoRoteiro() {
        return codigoRoteiro;
    }

    public void setCodigoRoteiro(String codigoRoteiro) {
        this.codigoRoteiro = codigoRoteiro;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public String getChaveNfe() {
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public StatusOcorrencia getStatusUltimaOcorrencia() {
        return statusUltimaOcorrencia;
    }

    public void setStatusUltimaOcorrencia(StatusOcorrencia statusUltimaOcorrencia) {
        this.statusUltimaOcorrencia = statusUltimaOcorrencia;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getCpfCnpjDestinatario() {
        return cpfCnpjDestinatario;
    }

    public void setCpfCnpjDestinatario(String cpfCnpjDestinatario) {
        this.cpfCnpjDestinatario = cpfCnpjDestinatario;
    }

    public Endereco getEnderecoDestinatario() {
        return enderecoDestinatario;
    }

    public void setEnderecoDestinatario(Endereco enderecoDestinatario) {
        this.enderecoDestinatario = enderecoDestinatario;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getCpfCnpjRemetente() {
        return cpfCnpjRemetente;
    }

    public void setCpfCnpjRemetente(String cpfCnpjRemetente) {
        this.cpfCnpjRemetente = cpfCnpjRemetente;
    }

    public Endereco getEnderecoRemetente() {
        return enderecoRemetente;
    }

    public void setEnderecoRemetente(Endereco enderecoRemetente) {
        this.enderecoRemetente = enderecoRemetente;
    }

    public String getCodigoCentroDistribuicao() {
        return codigoCentroDistribuicao;
    }

    public void setCodigoCentroDistribuicao(String codigoCentroDistribuicao) {
        this.codigoCentroDistribuicao = codigoCentroDistribuicao;
    }

    public String getNomeCentroDistribuicao() {
        return nomeCentroDistribuicao;
    }

    public void setNomeCentroDistribuicao(String nomeCentroDistribuicao) {
        this.nomeCentroDistribuicao = nomeCentroDistribuicao;
    }

    public Endereco getEnderecoCentroDistribuicao() {
        return enderecoCentroDistribuicao;
    }

    public void setEnderecoCentroDistribuicao(Endereco enderecoCentroDistribuicao) {
        this.enderecoCentroDistribuicao = enderecoCentroDistribuicao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataPrometidaEntrega() {
        return dataPrometidaEntrega;
    }

    public void setDataPrometidaEntrega(LocalDateTime dataPrometidaEntrega) {
        this.dataPrometidaEntrega = dataPrometidaEntrega;
    }

    public LocalDateTime getDataUltimoStatus() {
        return dataUltimoStatus;
    }

    public void setDataUltimoStatus(LocalDateTime dataUltimoStatus) {
        this.dataUltimoStatus = dataUltimoStatus;
    }

    public Integer getQuantidadeTentativasEntrega() {
        return quantidadeTentativasEntrega;
    }

    public void setQuantidadeTentativasEntrega(Integer quantidadeTentativasEntrega) {
        this.quantidadeTentativasEntrega = quantidadeTentativasEntrega;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public ModalidadeOperacao getModalidadeOperacao() {
        return modalidadeOperacao;
    }

    public void setModalidadeOperacao(ModalidadeOperacao modalidadeOperacao) {
        this.modalidadeOperacao = modalidadeOperacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
