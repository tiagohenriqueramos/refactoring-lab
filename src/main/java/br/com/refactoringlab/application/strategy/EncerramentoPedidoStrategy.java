package br.com.refactoringlab.application.strategy;

import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;

public interface EncerramentoPedidoStrategy {
    boolean aceita(StatusOcorrencia status);

    boolean aceita(StatusPedido status);

    EncerramentoPedidoOutput processar(Pedido pedido, String roteiroId, StatusOcorrencia statusOcorrencia, String motivoInsucesso, String usuarioId);
}