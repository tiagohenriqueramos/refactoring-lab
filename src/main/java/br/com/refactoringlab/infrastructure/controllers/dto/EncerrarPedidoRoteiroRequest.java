package br.com.refactoringlab.infrastructure.controllers.dto;

import br.com.refactoringlab.domain.enums.StatusPedido;

public record EncerrarPedidoRoteiroRequest(
		String pedidoEntregaId,
		StatusPedido novoStatus,
		String motivoInsucesso
) {}
