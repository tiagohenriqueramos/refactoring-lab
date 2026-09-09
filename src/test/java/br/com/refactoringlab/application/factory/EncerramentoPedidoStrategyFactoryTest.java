package br.com.refactoringlab.application.factory;

import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncerramentoPedidoStrategyFactoryTest {

    @Test
    @DisplayName("Deve retornar strategy quando ocorrencia for aceita")
    void deveRetornarStrategyQuandoOcorrenciaForAceita() {
        var strategy1 = mock(EncerramentoPedidoStrategy.class);
        var strategy2 = mock(EncerramentoPedidoStrategy.class);

        when(strategy1.aceita(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE)).thenReturn(false);
        when(strategy2.aceita(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE)).thenReturn(true);

        var factory = new EncerramentoPedidoStrategyFactory(List.of(strategy1, strategy2));

        var resultado = factory.obterStrategy(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE, null);

        assertThat(resultado).isSameAs(strategy2);
    }

    @Test
    @DisplayName("Deve retornar strategy quando status for aceito")
    void deveRetornarStrategyQuandoStatusForAceito() {
        var strategy1 = mock(EncerramentoPedidoStrategy.class);
        var strategy2 = mock(EncerramentoPedidoStrategy.class);

        when(strategy1.aceita(StatusPedido.INSUCESSO)).thenReturn(false);
        when(strategy2.aceita(StatusPedido.INSUCESSO)).thenReturn(true);

        var factory = new EncerramentoPedidoStrategyFactory(List.of(strategy1, strategy2));

        var resultado = factory.obterStrategy(null, StatusPedido.INSUCESSO);

        assertThat(resultado).isSameAs(strategy2);
    }

    @Test
    @DisplayName("Deve lancar erro quando nenhuma strategy aceitar ocorrencia e status")
    void deveLancarErroQuandoNenhumaStrategyAceitarOcorrenciaEStatus() {
        var strategy = mock(EncerramentoPedidoStrategy.class);
        when(strategy.aceita(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE)).thenReturn(false);
        when(strategy.aceita(StatusPedido.INSUCESSO)).thenReturn(false);

        var factory = new EncerramentoPedidoStrategyFactory(List.of(strategy));

        assertThatThrownBy(() -> factory.obterStrategy(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE, StatusPedido.INSUCESSO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nenhuma estratégia de encerramento encontrada");
    }
}

