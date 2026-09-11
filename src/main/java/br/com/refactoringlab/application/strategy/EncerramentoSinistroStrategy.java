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

public class EncerramentoSinistroStrategy implements EncerramentoPedidoStrategy {

    private final PedidoGateway pedidoGateway;
    private final OcorrenciaQueueGateway ocorrenciaQueueGateway;
    private final RastreioQueueGateway rastreioQueueGateway;

    public EncerramentoSinistroStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        this.pedidoGateway = pedidoGateway;
        this.ocorrenciaQueueGateway = ocorrenciaQueueGateway;
        this.rastreioQueueGateway = rastreioQueueGateway;
    }

    @Override
    public boolean aceita(StatusOcorrencia status) {
        return status != null && (status.name().startsWith("SINISTRO") || status.name().contains("AVARIA") || status.name().contains("EXTRAVIO"));
    }

    @Override
    public boolean aceita(StatusPedido status) {
        return StatusPedido.SINISTRO.equals(status);
    }

    @Override
    public EncerramentoPedidoOutput processar(Pedido pedido, String roteiroId, StatusOcorrencia statusOcorrencia, String motivoInsucesso, String usuarioId) {

        pedido.registrarTratativaEncerramento(StatusPedido.SINISTRO, statusOcorrencia, usuarioId);

        pedidoGateway.salvar(pedido);

        ocorrenciaQueueGateway.publicarOcorrencia(new OcorrenciaPedidoEvent(pedido.getId(), StatusPedido.SINISTRO, statusOcorrencia, "Sinistro registrado: " + motivoInsucesso, usuarioId, LocalDateTime.now()));

        rastreioQueueGateway.publicarRastreio(new RastreioPedidoEvent(pedido.getId(), StatusPedido.SINISTRO, statusOcorrencia, "Ocorrência de sinistro com a carga durante o transporte", usuarioId, LocalDateTime.now()));

        return new EncerramentoPedidoOutput(pedido.getId(), false, "Sinistro registrado com sucesso para o pedido", pedido.getId(), null, null);
    }
}