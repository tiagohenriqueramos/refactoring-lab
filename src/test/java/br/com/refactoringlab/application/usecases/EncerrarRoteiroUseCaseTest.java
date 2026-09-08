package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.dto.EncerrarPedidoItemInput;
import br.com.refactoringlab.application.dto.EncerrarRoteiroInput;
import br.com.refactoringlab.application.factory.EncerramentoPedidoStrategyFactory;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncerrarRoteiroUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private EncerramentoPedidoStrategyFactory strategyFactory;

    @Mock
    private EncerramentoPedidoStrategy strategy;

    @InjectMocks
    private EncerrarRoteiroUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista vazia quando input for nulo")
    void deveRetornarListaVaziaQuandoInputForNulo() {
        var resultado = useCase.executar(null);

        assertThat(resultado).isEmpty();
        verify(pedidoGateway, never()).buscarPorIds(org.mockito.ArgumentMatchers.anyList());
        verify(strategyFactory, never()).obterStrategy(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando itens estiver vazio")
    void deveRetornarListaVaziaQuandoItensEstiverVazio() {
        var input = new EncerrarRoteiroInput("ROT-1", "USR-1", List.of());

        var resultado = useCase.executar(input);

        assertThat(resultado).isEmpty();
        verify(pedidoGateway, never()).buscarPorIds(org.mockito.ArgumentMatchers.anyList());
        verify(strategyFactory, never()).obterStrategy(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve retornar erro quando pedido nao for encontrado")
    void deveRetornarErroQuandoPedidoNaoForEncontrado() {
        var ocorrencia = StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE;
        var itens = List.of(new EncerrarPedidoItemInput("PED-404", StatusPedido.INSUCESSO, ocorrencia, "cliente ausente"));
        var input = new EncerrarRoteiroInput("ROT-1", "USR-1", itens);
        when(pedidoGateway.buscarPorIds(List.of("PED-404"))).thenReturn(List.of());

        var resultado = useCase.executar(input);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).pedidoId()).isEqualTo("PED-404");
        assertThat(resultado.get(0).possuiErro()).isTrue();
        assertThat(resultado.get(0).mensagem()).contains("Pedido não encontrado");
        verify(strategyFactory, never()).obterStrategy(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve processar item com strategy e ordem correta de parametros")
    void deveProcessarItemComStrategyEOrdemCorretaDeParametros() {
        var ocorrencia = StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE;
        var item = new EncerrarPedidoItemInput("PED-10", StatusPedido.INSUCESSO, ocorrencia, "cliente ausente");
        var input = new EncerrarRoteiroInput("ROT-10", "USR-10", List.of(item));

        var pedido = new Pedido();
        pedido.setId("PED-10");

        var output = new EncerramentoPedidoOutput("PED-10", false, "ok", "PED-10", null, null);

        when(pedidoGateway.buscarPorIds(List.of("PED-10"))).thenReturn(List.of(pedido));
        when(strategyFactory.obterStrategy(ocorrencia, StatusPedido.INSUCESSO)).thenReturn(strategy);
        when(strategy.processar(pedido, "ROT-10", ocorrencia, "cliente ausente", "USR-10")).thenReturn(output);

        var resultado = useCase.executar(input);

        assertThat(resultado).containsExactly(output);
        verify(strategy).processar(pedido, "ROT-10", ocorrencia, "cliente ausente", "USR-10");
    }

    @Test
    @DisplayName("Deve retornar erro quando nenhuma strategy for encontrada")
    void deveRetornarErroQuandoNenhumaStrategyForEncontrada() {
        var ocorrencia = StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE;
        var item = new EncerrarPedidoItemInput("PED-99", StatusPedido.INSUCESSO, ocorrencia, "motivo");
        var input = new EncerrarRoteiroInput("ROT-99", "USR-99", List.of(item));

        var pedido = new Pedido();
        pedido.setId("PED-99");

        when(pedidoGateway.buscarPorIds(List.of("PED-99"))).thenReturn(List.of(pedido));
        when(strategyFactory.obterStrategy(ocorrencia, StatusPedido.INSUCESSO))
                .thenThrow(new IllegalArgumentException("combinacao invalida"));

        var resultado = useCase.executar(input);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).pedidoId()).isEqualTo("PED-99");
        assertThat(resultado.get(0).possuiErro()).isTrue();
        assertThat(resultado.get(0).mensagem()).contains("Nenhuma estratégia encontrada para o encerramento");
    }
}

