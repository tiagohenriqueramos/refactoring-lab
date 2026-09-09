package br.com.refactoringlab.infrastructure.controllers.dto;

import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;

public record EncerrarPedidoRoteiroRequest(
		String pedidoEntregaId,
		StatusPedido novoStatus,
		StatusOcorrencia novoStatusOcorrencia,
		String motivoInsucesso
) {}
