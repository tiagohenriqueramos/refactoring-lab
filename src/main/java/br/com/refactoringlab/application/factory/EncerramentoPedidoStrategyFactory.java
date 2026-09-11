package br.com.refactoringlab.application.factory;

import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;

import java.util.List;

public class EncerramentoPedidoStrategyFactory {

    private final List<EncerramentoPedidoStrategy> strategies;

    public EncerramentoPedidoStrategyFactory(List<EncerramentoPedidoStrategy> strategies) {
        this.strategies = strategies;
    }

    public EncerramentoPedidoStrategy obterStrategy(StatusOcorrencia statusOcorrencia, StatusPedido statusPedido) {
        return strategies.stream()
                .filter(s -> s.aceita(statusOcorrencia) || s.aceita(statusPedido))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma estratégia de encerramento encontrada para o status: " + statusOcorrencia + " / " + statusPedido));
    }
}