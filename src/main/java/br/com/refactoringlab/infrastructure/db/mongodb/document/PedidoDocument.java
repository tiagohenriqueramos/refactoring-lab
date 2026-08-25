package br.com.refactoringlab.infrastructure.db.mongodb.document;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pedidos")
public class PedidoDocument {

    @Id
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
}
