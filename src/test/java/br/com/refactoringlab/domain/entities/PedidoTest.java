package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PedidoTest {

    @Test
    @DisplayName("Deve atualizar o status do pedido e registrar a ocorrência com sucesso")
    void deveAtualizarStatusEOcorrencia() {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.RECEBIDO);

        // Act
        pedido.atualizarStatus(StatusPedido.EM_TRANSITO, StatusOcorrencia.EM_TRANSITO);

        // Assert
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.EM_TRANSITO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.EM_TRANSITO);
        assertThat(pedido.getDataUltimoStatus()).isNotNull();
        assertThat(pedido.getQuantidadeTentativasEntrega()).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = StatusOcorrencia.class, names = {"INSUCESSO_ENDERECO_NAO_ENCONTRADO", "INSUCESSO_AUSENTE", "INSUCESSO_RECUSADO"})
    @DisplayName("Deve incrementar a quantidade de tentativas para ocorrências de insucesso")
    void deveIncrementarTentativasParaOcorrenciasDeInsucesso(StatusOcorrencia ocorrenciaInsucesso) {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.EM_TRANSITO);

        // Act
        pedido.atualizarStatus(StatusPedido.EM_TRANSITO, ocorrenciaInsucesso);

        // Assert
        assertThat(pedido.getQuantidadeTentativasEntrega()).isEqualTo(1);
    }

    @Test
    @DisplayName("Não deve incrementar tentativas de entrega para ocorrências que não sejam de insucesso")
    void naoDeveIncrementarTentativasParaOutrasOcorrencias() {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.EM_TRANSITO);

        // Act
        pedido.atualizarStatus(StatusPedido.ENTREGUE, StatusOcorrencia.ENTREGUE);

        // Assert
        assertThat(pedido.getQuantidadeTentativasEntrega()).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"ENTREGUE", "DEVOLVIDO", "CANCELADO", "SINISTRO"})
    @DisplayName("Deve lançar exceção ao tentar atualizar um pedido que já está em status finalizado")
    void deveLancarExcecaoAoAtualizarPedidoFinalizado(StatusPedido statusFinal) {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", statusFinal);

        // Act & Assert
        assertThatThrownBy(() -> pedido.atualizarStatus(StatusPedido.EM_TRANSITO, StatusOcorrencia.RECEBIDO_CD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O pedido já está finalizado.");
    }
}