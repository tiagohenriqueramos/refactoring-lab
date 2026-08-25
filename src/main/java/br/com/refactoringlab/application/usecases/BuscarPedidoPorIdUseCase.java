package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;

import java.util.Optional;

public class BuscarPedidoPorIdUseCase {

    private final PedidoGateway pedidoGateway;

    public BuscarPedidoPorIdUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Optional<Pedido> executar(String id) {
        return pedidoGateway.buscarPorId(id);
    }
}

