package br.com.refactoringlab.application.dto;

import java.util.List;

public record EncerrarRoteiroInput(
        String roteiroId,
        String usuarioId,
        List<EncerrarPedidoItemInput> itens
) {}