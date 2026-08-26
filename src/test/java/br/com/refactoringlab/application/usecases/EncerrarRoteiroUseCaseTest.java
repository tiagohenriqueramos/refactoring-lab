package br.com.refactoringlab.application.usecases;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        verifyNoInteractions(rastreioInternoGateway);
    }
}

