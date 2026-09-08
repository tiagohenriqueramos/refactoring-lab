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

    public EncerramentoPedidoStrategy obterStrategy(StatusOcorrencia ocorrencia, StatusPedido status) {
        return strategies.stream()
                .filter(strategy -> (ocorrencia != null && strategy.aceita(ocorrencia))
                        || (status != null && strategy.aceita(status)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhuma estratégia de encerramento encontrada para a ocorrência '%s' e status '%s'.",
                                ocorrencia, status)
                ));
    }
}