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
class EncerramentoSinistroStrategyTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private OcorrenciaQueueGateway ocorrenciaQueueGateway;

    @Mock
    private RastreioQueueGateway rastreioQueueGateway;

    @InjectMocks
    private EncerramentoSinistroStrategy strategy;

    @Test
    @DisplayName("Deve aceitar status de ocorrencia de sinistro")
    void deveAceitarStatusDeOcorrenciaDeSinistro() {
        assertThat(strategy.aceita(StatusOcorrencia.EXTRAVIO)).isTrue();
        assertThat(strategy.aceita(StatusOcorrencia.AVARIA)).isTrue();
        assertThat(strategy.aceita(StatusOcorrencia.ENTREGUE_PROPRIO_DESTINATARIO)).isFalse();
        assertThat(strategy.aceita((StatusOcorrencia) null)).isFalse();
    }

    @Test
    @DisplayName("Deve aceitar apenas status de pedido sinistro")
    void deveAceitarApenasStatusDePedidoSinistro() {
        assertThat(strategy.aceita(StatusPedido.SINISTRO)).isTrue();
        assertThat(strategy.aceita(StatusPedido.ENTREGUE)).isFalse();
        assertThat(strategy.aceita((StatusPedido) null)).isFalse();
    }

    @Test
    @DisplayName("Deve processar sinistro e publicar ocorrencia e rastreio")
    void deveProcessarSinistroEPublicarOcorrenciaERastreio() {
        var pedido = new Pedido("PED-20", 2020L, "GUID-20", StatusPedido.EM_TRANSITO);
        when(pedidoGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        var output = strategy.processar(
                pedido,
                "ROT-20",
                StatusOcorrencia.EXTRAVIO,
                "Carga extraviada",
                "user-20"
        );

        assertThat(output.possuiErro()).isFalse();
        assertThat(output.pedidoId()).isEqualTo("PED-20");
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.SINISTRO);

        verify(pedidoGateway).salvar(pedido);

        var ocorrenciaCaptor = ArgumentCaptor.forClass(OcorrenciaPedidoEvent.class);
        verify(ocorrenciaQueueGateway).publicarOcorrencia(ocorrenciaCaptor.capture());
        assertThat(ocorrenciaCaptor.getValue().pedidoId()).isEqualTo("PED-20");
        assertThat(ocorrenciaCaptor.getValue().statusPedido()).isEqualTo(StatusPedido.SINISTRO);
        assertThat(ocorrenciaCaptor.getValue().motivo()).isEqualTo("Sinistro registrado: Carga extraviada");

        var rastreioCaptor = ArgumentCaptor.forClass(RastreioPedidoEvent.class);
        verify(rastreioQueueGateway).publicarRastreio(rastreioCaptor.capture());
        assertThat(rastreioCaptor.getValue().pedidoId()).isEqualTo("PED-20");
        assertThat(rastreioCaptor.getValue().statusPedido()).isEqualTo(StatusPedido.SINISTRO);
        assertThat(rastreioCaptor.getValue().descricao()).contains("sinistro");
    }
}

