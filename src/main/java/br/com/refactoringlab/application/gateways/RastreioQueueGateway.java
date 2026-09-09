package br.com.refactoringlab.application.gateways;

import br.com.refactoringlab.application.dto.RastreioPedidoEvent;

public interface RastreioQueueGateway {
    void publicarRastreio(RastreioPedidoEvent event);
}

