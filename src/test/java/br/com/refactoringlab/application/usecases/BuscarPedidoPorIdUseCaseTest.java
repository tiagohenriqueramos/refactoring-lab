package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPedidoPorIdUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private BuscarPedidoPorIdUseCase useCase;

    @Test
    @DisplayName("Deve buscar pedido por id usando o repositorio")
    void deveBuscarPedidoPorIdUsandoORepositorio() {
        var pedido = new Pedido();
        pedido.setId("PED-777");
        when(pedidoGateway.buscarPorId("PED-777")).thenReturn(Optional.of(pedido));

        var resultado = useCase.executar("PED-777");

        assertThat(resultado).contains(pedido);
        verify(pedidoGateway).buscarPorId("PED-777");
    }
}

