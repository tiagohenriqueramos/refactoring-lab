package br.com.refactoringlab.application.strategy;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;

public interface EncerramentoPedidoStrategy {
    boolean aceita(StatusPedido status);
    EncerramentoPedidoOutput processar(Pedido pedido, String roteiroId, String usuarioId, String motivoInsucesso);
}