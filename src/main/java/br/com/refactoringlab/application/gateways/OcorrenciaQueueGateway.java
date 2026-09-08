package br.com.refactoringlab.application.gateways;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;

public interface OcorrenciaQueueGateway {
    void publicarOcorrencia(OcorrenciaPedidoEvent event);
}

