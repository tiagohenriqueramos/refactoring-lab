package br.com.refactoringlab.domain.ports;

import br.com.refactoringlab.domain.entities.Pedido;

import java.util.Optional;

public interface PedidoRepositoryPort {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
}
