package br.com.refactoringlab.application.gateways;

public interface RastreioInternoGateway {
    void registrarObservacao(String usuarioId, String pedidoId, String descricao, String evento);
}