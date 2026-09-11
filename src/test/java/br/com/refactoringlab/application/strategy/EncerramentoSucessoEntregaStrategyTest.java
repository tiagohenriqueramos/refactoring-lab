package br.com.refactoringlab.application.strategy;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
import br.com.refactoringlab.application.gateways.OcorrenciaQueueGateway;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.gateways.RastreioQueueGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncerramentoSucessoEntregaStrategyTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private OcorrenciaQueueGateway ocorrenciaQueueGateway;

    @Mock
    private RastreioQueueGateway rastreioQueueGateway;

    @InjectMocks
    private EncerramentoSucessoEntregaStrategy strategy;

    @Test
    @DisplayName("Deve aceitar status de ocorrencia de entrega")
    void deveAceitarStatusDeOcorrenciaDeEntrega() {
        assertThat(strategy.aceita(StatusOcorrencia.ENTREGUE_PROPRIO_DESTINATARIO)).isTrue();
        assertThat(strategy.aceita(StatusOcorrencia.ENTREGUE_TERCEIROS)).isTrue();
        assertThat(strategy.aceita(StatusOcorrencia.EXTRAVIO)).isFalse();
        assertThat(strategy.aceita((StatusOcorrencia) null)).isFalse();
    }

    @Test
    @DisplayName("Deve aceitar apenas status de pedido entregue")
    void deveAceitarApenasStatusDePedidoEntregue() {
        assertThat(strategy.aceita(StatusPedido.ENTREGUE)).isTrue();
        assertThat(strategy.aceita(StatusPedido.SINISTRO)).isFalse();
        assertThat(strategy.aceita((StatusPedido) null)).isFalse();
    }

    @Test
    @DisplayName("Deve processar entrega e publicar ocorrencia e rastreio")
    void deveProcessarEntregaEPublicarOcorrenciaERastreio() {
        var pedido = new Pedido("PED-10", 1010L, "GUID-10", StatusPedido.EM_TRANSITO);
        when(pedidoGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        var output = strategy.processar(
                pedido,
                "ROT-10",
                StatusOcorrencia.ENTREGUE_PROPRIO_DESTINATARIO,
                null,
                "user-10"
        );

        assertThat(output.possuiErro()).isFalse();
        assertThat(output.pedidoId()).isEqualTo("PED-10");
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.ENTREGUE);

        verify(pedidoGateway).salvar(pedido);

        var ocorrenciaCaptor = ArgumentCaptor.forClass(OcorrenciaPedidoEvent.class);
        verify(ocorrenciaQueueGateway).publicarOcorrencia(ocorrenciaCaptor.capture());
        assertThat(ocorrenciaCaptor.getValue().pedidoId()).isEqualTo("PED-10");
        assertThat(ocorrenciaCaptor.getValue().statusPedido()).isEqualTo(StatusPedido.ENTREGUE);
        assertThat(ocorrenciaCaptor.getValue().motivo()).isEqualTo("Entrega confirmada");

        var rastreioCaptor = ArgumentCaptor.forClass(RastreioPedidoEvent.class);
        verify(rastreioQueueGateway).publicarRastreio(rastreioCaptor.capture());
        assertThat(rastreioCaptor.getValue().pedidoId()).isEqualTo("PED-10");
        assertThat(rastreioCaptor.getValue().statusPedido()).isEqualTo(StatusPedido.ENTREGUE);
        assertThat(rastreioCaptor.getValue().descricao()).isEqualTo("Pedido entregue com sucesso");
    }
}

