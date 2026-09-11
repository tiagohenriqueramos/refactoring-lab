package br.com.refactoringlab.application.strategy;

import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
import br.com.refactoringlab.application.gateways.OcorrenciaQueueGateway;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.gateways.RastreioQueueGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;

import java.time.LocalDateTime;

public class EncerramentoSucessoEntregaStrategy implements EncerramentoPedidoStrategy {

    private final PedidoGateway pedidoGateway;
    private final OcorrenciaQueueGateway ocorrenciaQueueGateway;
    private final RastreioQueueGateway rastreioQueueGateway;

    public EncerramentoSucessoEntregaStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        this.pedidoGateway = pedidoGateway;
        this.ocorrenciaQueueGateway = ocorrenciaQueueGateway;
        this.rastreioQueueGateway = rastreioQueueGateway;
    }

    @Override
    public boolean aceita(StatusOcorrencia status) {
        return StatusOcorrencia.ENTREGUE_PROPRIO_DESTINATARIO.equals(status)
                || StatusOcorrencia.ENTREGUE_TERCEIROS.equals(status);
    }

    @Override
    public boolean aceita(StatusPedido status) {
        return StatusPedido.ENTREGUE.equals(status);
    }

    @Override
    public EncerramentoPedidoOutput processar(Pedido pedido, String roteiroId, StatusOcorrencia statusOcorrencia, String motivoInsucesso, String usuarioId) {
        pedido.registrarTratativaEncerramento(StatusPedido.ENTREGUE, statusOcorrencia, usuarioId);

        pedidoGateway.salvar(pedido);

        ocorrenciaQueueGateway.publicarOcorrencia(new OcorrenciaPedidoEvent(
                pedido.getId(),
                StatusPedido.ENTREGUE,
                statusOcorrencia,
                "Entrega confirmada",
                usuarioId,
                LocalDateTime.now()
        ));

        rastreioQueueGateway.publicarRastreio(new RastreioPedidoEvent(
                pedido.getId(),
                StatusPedido.ENTREGUE,
                statusOcorrencia,
                "Pedido entregue com sucesso",
                usuarioId,
                LocalDateTime.now()
        ));

        return new EncerramentoPedidoOutput(
                pedido.getId(),
                false,
                "Pedido encerrado com sucesso como entregue.",
                pedido.getId(),
                null,
                null
        );
    }
}