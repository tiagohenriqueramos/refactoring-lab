package br.com.refactoringlab.infrastructure.controllers.dto;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;

public record PedidoResponse(
        String id,
        Long codigoInterno,
        StatusPedido statusPedido,
        StatusOcorrencia statusUltimaOcorrencia
) {
    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCodigoInterno(),
                pedido.getStatusPedido(),
                pedido.getStatusUltimaOcorrencia()
        );
    }
}

