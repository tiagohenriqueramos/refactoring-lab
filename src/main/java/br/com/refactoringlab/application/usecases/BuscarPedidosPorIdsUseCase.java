package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;

import java.util.List;

public class BuscarPedidosPorIdsUseCase {

    private final PedidoGateway pedidoGateway;

    public BuscarPedidosPorIdsUseCase(PedidoGateway pedidoGateway) {this.pedidoGateway = pedidoGateway;}

    public List<Pedido> executar(){
        return pedidoGateway.buscarPorIds();
    }

}
