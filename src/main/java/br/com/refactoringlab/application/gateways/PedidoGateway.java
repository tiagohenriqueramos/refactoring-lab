package br.com.refactoringlab.application.gateways;

import br.com.refactoringlab.domain.entities.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoGateway {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
    List<Pedido> buscarPorIds(List<String> ids);
}


