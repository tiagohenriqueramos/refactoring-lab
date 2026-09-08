package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.*;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.factory.EncerramentoPedidoStrategyFactory;
import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.domain.entities.Pedido;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EncerrarRoteiroUseCase {

    private final PedidoGateway pedidoGateway;
    private final EncerramentoPedidoStrategyFactory strategyFactory;

    public EncerrarRoteiroUseCase(PedidoGateway pedidoGateway,
            EncerramentoPedidoStrategyFactory strategyFactory) {
        this.pedidoGateway = pedidoGateway;
        this.strategyFactory = strategyFactory;
    }

    public List<EncerramentoPedidoOutput> executar(EncerrarRoteiroInput input) {
        if (input == null || input.itens() == null || input.itens().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> pedidosIds = input.itens().stream()
                .map(EncerrarPedidoItemInput::pedidoEntregaId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<Pedido> pedidos = pedidoGateway.buscarPorIds(pedidosIds);

        Map<String, Pedido> pedidosPorId = pedidos.stream()
                .collect(Collectors.toMap(Pedido::getId, Function.identity()));

        List<EncerramentoPedidoOutput> resultados = new ArrayList<>();

        for (EncerrarPedidoItemInput item : input.itens()) {
            Pedido pedido = pedidosPorId.get(item.pedidoEntregaId());

            if (pedido == null) {
                resultados.add(new EncerramentoPedidoOutput(
                        item.pedidoEntregaId(),
                        true,
                        "Pedido não encontrado na base de dados.",
                        null,
                        null,
                        null
                ));
                continue;
            }

            try {
                EncerramentoPedidoStrategy strategy = strategyFactory.obterStrategy(item.novoStatusOcorrencia(), item.novoStatus());

                EncerramentoPedidoOutput resultado = strategy.processar(
                        pedido,
                        input.roteiroId(),
                        item.novoStatusOcorrencia(),
                        input.usuarioId(),
                        item.motivoInsucesso()
                );

                resultados.add(resultado);

            } catch (IllegalArgumentException ex) {
                resultados.add(new EncerramentoPedidoOutput(
                        pedido.getId(),
                        true,
                        "Nenhuma estratégia encontrada para o encerramento: " + ex.getMessage(),
                        null,
                        null,
                        null
                ));
            }
        }

        return resultados;
    }
}