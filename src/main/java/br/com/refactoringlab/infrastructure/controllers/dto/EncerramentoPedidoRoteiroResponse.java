package br.com.refactoringlab.infrastructure.controllers.dto;

import java.util.Set;

public record EncerramentoPedidoRoteiroResponse(
        String pedidoId,
        boolean possuiErro,
        String mensagem,
        String pedidoRemoverRoteiroId,
        Set<String> pedidosRemoverRoteiroId,
        String etapaAdicionarId
) {}

