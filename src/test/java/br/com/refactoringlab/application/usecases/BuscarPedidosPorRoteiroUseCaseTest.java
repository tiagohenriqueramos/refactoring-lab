package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarPedidosPorRoteiroUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private BuscarPedidosPorRoteiroUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista vazia se o código do roteiro for nulo")
    void deveRetornarListaVaziaSeCodigoRoteiroForNulo() {
        var resultado = useCase.executar(null);

        assertThat(resultado).isEmpty();
        verifyNoInteractions(pedidoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista vazia se o código do roteiro for vazio ou em branco")
    void deveRetornarListaVaziaSeCodigoRoteiroForEmBranco() {
        var resultado = useCase.executar("   ");

        assertThat(resultado).isEmpty();
        verifyNoInteractions(pedidoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de pedidos quando o código do roteiro for válido")
    void deveRetornarListaDePedidosQuandoCodigoRoteiroValido() {
        var pedido1 = new Pedido();
        pedido1.setId("PED-001");
        var pedido2 = new Pedido();
        pedido2.setId("PED-002");
        var list = List.of(pedido1, pedido2);

        when(pedidoGateway.buscarPorCodigoRoteiro("ROT-001")).thenReturn(list);

        var resultado = useCase.executar("ROT-001");

        assertThat(resultado).containsExactlyElementsOf(list);
        verify(pedidoGateway).buscarPorCodigoRoteiro("ROT-001");
    }
}