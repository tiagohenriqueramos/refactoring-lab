package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusPedidoTest {

    @Test
    @DisplayName("Deve retornar descrições configuradas")
    void deveRetornarDescricoes() {
        assertThat(StatusPedido.RECEBIDO.getDescricao()).isEqualTo("Recebido");
        assertThat(StatusPedido.EM_TRANSITO.getDescricao()).isEqualTo("Em trânsito");
        assertThat(StatusPedido.EM_RETORNO.getDescricao()).isEqualTo("Em processo de retorno");
        assertThat(StatusPedido.DEVOLVIDO.getDescricao()).isEqualTo("Devolvido");
        assertThat(StatusPedido.CANCELADO.getDescricao()).isEqualTo("Cancelado");
        assertThat(StatusPedido.SINISTRO.getDescricao()).isEqualTo("Sinistrado");
        assertThat(StatusPedido.ENTREGUE.getDescricao()).isEqualTo("Entregue");
    }

    @Test
    @DisplayName("Deve identificar status finalizados")
    void deveIdentificarStatusFinalizados() {
        assertThat(StatusPedido.ENTREGUE.isFinalizado()).isTrue();
        assertThat(StatusPedido.DEVOLVIDO.isFinalizado()).isTrue();
        assertThat(StatusPedido.CANCELADO.isFinalizado()).isTrue();
        assertThat(StatusPedido.SINISTRO.isFinalizado()).isTrue();

        assertThat(StatusPedido.RECEBIDO.isFinalizado()).isFalse();
        assertThat(StatusPedido.EM_TRANSITO.isFinalizado()).isFalse();
        assertThat(StatusPedido.EM_RETORNO.isFinalizado()).isFalse();
    }
}

