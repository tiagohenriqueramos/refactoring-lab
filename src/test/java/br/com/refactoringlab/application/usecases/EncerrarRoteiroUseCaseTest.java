package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.AlterarPedidosRoteiroInput;
import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.dto.EncerrarPedidoItemInput;
import br.com.refactoringlab.application.dto.EncerrarRoteiroInput;
import br.com.refactoringlab.application.gateways.EncerrarRoteiroGateway;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.gateways.RastreioInternoGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncerrarRoteiroUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private EncerrarRoteiroGateway encerrarRoteiroGateway;

    @Mock
    private RastreioInternoGateway rastreioInternoGateway;

    @InjectMocks
    private EncerrarRoteiroUseCase useCase;

    @Test
    @DisplayName("Deve retornar erro de validacao quando pedidoEntregaId estiver em branco")
    void deveRetornarErroDeValidacaoQuandoPedidoEntregaIdEstiverEmBranco() {
        var itens = List.of(new EncerrarPedidoItemInput(" ", StatusPedido.ENTREGUE, null));
        var input = new EncerrarRoteiroInput("ROT-1", "USR-1", itens);

        var resultado = useCase.executar(input);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).possuiErro()).isTrue();
        assertThat(resultado.get(0).mensagem()).contains("pedidoEntregaId");
        verifyNoInteractions(pedidoGateway, encerrarRoteiroGateway, rastreioInternoGateway);
    }

    @Test
    @DisplayName("Deve retornar erro de validacao quando novoStatus for nulo")
    void deveRetornarErroDeValidacaoQuandoNovoStatusForNulo() {
        var itens = List.of(new EncerrarPedidoItemInput("PED-10", null, null));
        var input = new EncerrarRoteiroInput("ROT-10", "USR-10", itens);

        var resultado = useCase.executar(input);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).possuiErro()).isTrue();
        assertThat(resultado.get(0).pedidoId()).isEqualTo("PED-10");
        assertThat(resultado.get(0).mensagem()).contains("statusTratativa");
        verifyNoInteractions(pedidoGateway, encerrarRoteiroGateway, rastreioInternoGateway);
    }

    @Test
    @DisplayName("Deve buscar pedidos por ids quando entrada for valida")
    void deveBuscarPedidosPorIdsQuandoEntradaForValida() {
        var itens = List.of(
                new EncerrarPedidoItemInput("PED-1", StatusPedido.ENTREGUE, null),
                new EncerrarPedidoItemInput("PED-2", StatusPedido.CANCELADO, "cliente ausente")
        );
        var input = new EncerrarRoteiroInput("ROT-2", "USR-2", itens);
        var ids = List.of("PED-1", "PED-2");

        when(pedidoGateway.buscarPorIds(ids)).thenReturn(List.of(new Pedido(), new Pedido()));

        var resultado = useCase.executar(input);

        assertThat(resultado).isEmpty();
        verify(pedidoGateway).buscarPorIds(ids);
        verify(encerrarRoteiroGateway, never()).enviarParaFilaAtualizacao(any());
        verifyNoInteractions(rastreioInternoGateway);
    }

    @Test
    @DisplayName("Deve notificar fila quando houver pedidos e etapas para atualizar")
    void deveNotificarFilaQuandoHouverPedidosEEtapasParaAtualizar() throws Exception {
        var resultados = List.of(
                new EncerramentoPedidoOutput("PED-1", false, "ok", "PED-1", Set.of("PED-2"), "ETAPA-1"),
                new EncerramentoPedidoOutput("PED-ERRO", true, "erro", "IGNORAR", Set.of("IGNORAR"), "IGNORAR")
        );

        Method method = EncerrarRoteiroUseCase.class
                .getDeclaredMethod("notificarFilaAtualizacao", List.class, String.class);
        method.setAccessible(true);
        method.invoke(useCase, resultados, "ROT-99");

        var captor = org.mockito.ArgumentCaptor.forClass(AlterarPedidosRoteiroInput.class);
        verify(encerrarRoteiroGateway, times(1)).enviarParaFilaAtualizacao(captor.capture());

        var payload = captor.getValue();
        assertThat(payload.roteiroGuid()).isEqualTo("ROT-99");
        assertThat(payload.removerPedidoGuidList()).containsExactlyInAnyOrder("PED-1", "PED-2");
        assertThat(payload.adicionarEtapaGuidList()).containsExactly("ETAPA-1");
        assertThat(payload.origemAlteracao()).isEqualTo("ALTERACAO_MANUAL_CANCELAMENTO");
    }

    @Test
    @DisplayName("Nao deve notificar fila quando nao houver alteracoes")
    void naoDeveNotificarFilaQuandoNaoHouverAlteracoes() throws Exception {
        var resultados = List.of(
                new EncerramentoPedidoOutput("PED-1", true, "erro", "PED-1", Set.of("PED-2"), "ETAPA-1"),
                new EncerramentoPedidoOutput("PED-2", false, "ok", null, null, " ")
        );

        Method method = EncerrarRoteiroUseCase.class
                .getDeclaredMethod("notificarFilaAtualizacao", List.class, String.class);
        method.setAccessible(true);
        method.invoke(useCase, resultados, "ROT-100");

        verify(encerrarRoteiroGateway, never()).enviarParaFilaAtualizacao(any());
    }
}

