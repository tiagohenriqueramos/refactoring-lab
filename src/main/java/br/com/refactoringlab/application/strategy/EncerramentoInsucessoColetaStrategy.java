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

public class EncerramentoInsucessoColetaStrategy implements EncerramentoPedidoStrategy {

    private final PedidoGateway pedidoGateway;
    private final OcorrenciaQueueGateway ocorrenciaQueueGateway;
    private final RastreioQueueGateway rastreioQueueGateway;

    public EncerramentoInsucessoColetaStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        this.pedidoGateway = pedidoGateway; this.ocorrenciaQueueGateway = ocorrenciaQueueGateway;
        this.rastreioQueueGateway = rastreioQueueGateway;
    }

    @Override
    public boolean aceita(StatusOcorrencia ocorrencia) {
        return ocorrencia != null && ocorrencia.name().startsWith("INSUCESSO_COLETA_REVERSA_");
    }

    @Override
    public boolean aceita(StatusPedido status) {
        return StatusPedido.INSUCESSO.equals(status);
    }

    @Override
    public EncerramentoPedidoOutput processar(Pedido pedido, String roteiroId, StatusOcorrencia ocorrencia, String motivoInsucesso, String usuarioId) {
        try {

            pedido.registrarTratativaEncerramento(StatusPedido.INSUCESSO, ocorrencia, usuarioId);

            pedidoGateway.salvar(pedido);

            ocorrenciaQueueGateway.publicarOcorrencia(new OcorrenciaPedidoEvent(pedido.getId(), pedido.getStatusPedido(), ocorrencia, motivoInsucesso, usuarioId, LocalDateTime.now()));

            rastreioQueueGateway.publicarRastreio(new RastreioPedidoEvent(pedido.getId(), StatusPedido.INSUCESSO, ocorrencia, motivoInsucesso, usuarioId, LocalDateTime.now()));

            return new EncerramentoPedidoOutput(pedido.getId(), false, "Insucesso de coleta (" + ocorrencia + ") registrado com sucesso.", pedido.getId(), null, null);

        } catch (IllegalStateException ex) {
            return new EncerramentoPedidoOutput(pedido.getId(), true, "Regra de negócio violada: " + ex.getMessage(), null, null, null);
        } catch (Exception ex) {
            return new EncerramentoPedidoOutput(pedido.getId(), true, "Erro ao processar insucesso de coleta: " + ex.getMessage(), null, null, null);
        }
    }
}