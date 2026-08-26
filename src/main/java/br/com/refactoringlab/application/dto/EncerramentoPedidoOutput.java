package br.com.refactoringlab.application.dto;

import java.util.Set;

public record EncerramentoPedidoOutput(
        String pedidoId,
        boolean possuiErro,
        String mensagem,
        String pedidoRemoverRoteiroId,
        Set<String> pedidosRemoverRoteiroId,
        String etapaAdicionarId
) {}