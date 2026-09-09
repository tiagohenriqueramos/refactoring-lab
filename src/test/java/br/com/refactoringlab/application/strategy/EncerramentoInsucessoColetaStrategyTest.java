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
class EncerramentoInsucessoColetaStrategyTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private OcorrenciaQueueGateway ocorrenciaQueueGateway;

    @Mock
    private RastreioQueueGateway rastreioQueueGateway;

    @InjectMocks
    private EncerramentoInsucessoColetaStrategy strategy;

    @Test
    @DisplayName("Deve processar encerramento e publicar ocorrência e rastreio")
    void deveProcessarEncerramentoEPublicarEventos() {
        var pedido = new Pedido("PED-1", 1001L, "GUID-1", StatusPedido.EM_TRANSITO);
        when(pedidoGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        var output = strategy.processar(
                pedido,
                "ROT-1",
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Cliente ausente",
                "user-1"
        );

        assertThat(output.possuiErro()).isFalse();
        assertThat(output.pedidoId()).isEqualTo("PED-1");

        var ocorrenciaCaptor = ArgumentCaptor.forClass(OcorrenciaPedidoEvent.class);
        verify(ocorrenciaQueueGateway).publicarOcorrencia(ocorrenciaCaptor.capture());
        assertThat(ocorrenciaCaptor.getValue().pedidoId()).isEqualTo("PED-1");
        assertThat(ocorrenciaCaptor.getValue().motivo()).isEqualTo("Cliente ausente");

        var rastreioCaptor = ArgumentCaptor.forClass(RastreioPedidoEvent.class);
        verify(rastreioQueueGateway).publicarRastreio(rastreioCaptor.capture());
        assertThat(rastreioCaptor.getValue().pedidoId()).isEqualTo("PED-1");
        assertThat(rastreioCaptor.getValue().descricao()).isEqualTo("Cliente ausente");
    }

    @Test
    @DisplayName("Deve retornar erro de regra de negocio quando pedido finalizado")
    void deveRetornarErroDeRegraDeNegocioQuandoPedidoFinalizado() {
        var pedido = new Pedido("PED-2", 1002L, "GUID-2", StatusPedido.ENTREGUE);

        var output = strategy.processar(
                pedido,
                "ROT-2",
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Nao coletado",
                "user-2"
        );

        assertThat(output.possuiErro()).isTrue();
        assertThat(output.mensagem()).contains("Regra de negócio violada");
    }

    @Test
    @DisplayName("Deve retornar erro tecnico quando salvar falhar")
    void deveRetornarErroTecnicoQuandoSalvarFalhar() {
        var pedido = new Pedido("PED-3", 1003L, "GUID-3", StatusPedido.EM_TRANSITO);
        when(pedidoGateway.salvar(any(Pedido.class))).thenThrow(new RuntimeException("mongo offline"));

        var output = strategy.processar(
                pedido,
                "ROT-3",
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Falha persistencia",
                "user-3"
        );

        assertThat(output.possuiErro()).isTrue();
        assertThat(output.mensagem()).contains("Erro ao processar insucesso de coleta");
    }
}

