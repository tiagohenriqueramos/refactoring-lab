package br.com.refactoringlab.application.dto;

import br.com.refactoringlab.domain.enums.StatusPedido;

public record EncerrarPedidoItemInput(
        String pedidoEntregaId,
        StatusPedido novoStatus,
        String motivoInsucesso
) {}