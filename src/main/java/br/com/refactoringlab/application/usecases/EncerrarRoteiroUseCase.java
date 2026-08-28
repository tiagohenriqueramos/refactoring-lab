package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.*;
import br.com.refactoringlab.application.gateways.*;
import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusPedido;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EncerrarRoteiroUseCase {

    private final PedidoGateway pedidoGateway;
    private final EncerrarRoteiroGateway encerrarRoteiroGateway;
    private final RastreioInternoGateway rastreioInternoGateway;
    private final List<EncerramentoPedidoStrategy> strategies;

    public EncerrarRoteiroUseCase(PedidoGateway pedidoGateway,
                                  EncerrarRoteiroGateway encerrarRoteiroGateway,
                                  RastreioInternoGateway rastreioInternoGateway,
                                  List<EncerramentoPedidoStrategy> strategies) {
        this.pedidoGateway = pedidoGateway;
        this.encerrarRoteiroGateway = encerrarRoteiroGateway;
        this.rastreioInternoGateway = rastreioInternoGateway;
        this.strategies = strategies;
    }

    public List<EncerramentoPedidoOutput> executar(EncerrarRoteiroInput input) {
       List<String> pedidosIds = input.itens().stream().map(EncerrarPedidoItemInput::pedidoEntregaId).filter(Objects::nonNull).distinct().toList();

       List<Pedido> pedidos = pedidoGateway.buscarPorIds(pedidosIds);

        Map<String, Pedido> pedidosPorId = pedidos.stream()
                .collect(Collectors.toMap(Pedido::getId, Function.identity()));

        return null;
    }

}