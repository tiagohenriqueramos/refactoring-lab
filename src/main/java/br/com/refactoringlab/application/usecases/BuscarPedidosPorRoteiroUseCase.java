package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;

import java.util.List;

public class BuscarPedidosPorRoteiroUseCase {

    private final PedidoGateway pedidoGateway;

    public BuscarPedidosPorRoteiroUseCase(PedidoGateway pedidoGateway) {this.pedidoGateway = pedidoGateway;}

    public List<Pedido> executar(String codigoRoteiro){
        if (codigoRoteiro == null || codigoRoteiro.isBlank()) {
            return List.of();
        }
        return pedidoGateway.buscarPorCodigoRoteiro(codigoRoteiro);
    }
}
