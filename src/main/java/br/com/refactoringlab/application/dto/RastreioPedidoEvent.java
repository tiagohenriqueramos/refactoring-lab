package br.com.refactoringlab.application.dto;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import java.time.LocalDateTime;

public record RastreioPedidoEvent(
        String pedidoId,
        StatusPedido statusPedido,
        StatusOcorrencia statusOcorrencia,
        String descricao,
        String usuarioId,
        LocalDateTime dataHora
) {}

