package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TipoPedidoTest {

    @Test
    @DisplayName("Deve validar propriedades de cada tipo de pedido")
    void deveValidarPropriedadesDeCadaTipoDePedido() {
        assertThat(TipoPedido.ENTREGA_DIRETA_LEVE.isDevolucao()).isFalse();
        assertThat(TipoPedido.ENTREGA_DIRETA_LEVE.getModalidade()).isEqualTo(ModalidadeOperacao.OUTBOUND);

        assertThat(TipoPedido.ENTREGA_DIRETA_PESADO.isDevolucao()).isFalse();
        assertThat(TipoPedido.ENTREGA_DIRETA_PESADO.getModalidade()).isEqualTo(ModalidadeOperacao.OUTBOUND);

        assertThat(TipoPedido.REVERSA_LEVE_LOJA.isDevolucao()).isTrue();
        assertThat(TipoPedido.REVERSA_LEVE_LOJA.getModalidade()).isEqualTo(ModalidadeOperacao.INBOUND_DROP_OFF);

        assertThat(TipoPedido.REVERSA_PESADA_COLETA.isDevolucao()).isTrue();
        assertThat(TipoPedido.REVERSA_PESADA_COLETA.getModalidade()).isEqualTo(ModalidadeOperacao.INBOUND_PICK_UP);
    }

    @Test
    @DisplayName("Deve exigir roteirizacao apenas para reversa pesada")
    void deveExigirRoteirizacaoApenasParaReversaPesada() {
        assertThat(TipoPedido.REVERSA_PESADA_COLETA.exigeRoteirizacaoColetaMotorista()).isTrue();
        assertThat(TipoPedido.ENTREGA_DIRETA_LEVE.exigeRoteirizacaoColetaMotorista()).isFalse();
        assertThat(TipoPedido.ENTREGA_DIRETA_PESADO.exigeRoteirizacaoColetaMotorista()).isFalse();
        assertThat(TipoPedido.REVERSA_LEVE_LOJA.exigeRoteirizacaoColetaMotorista()).isFalse();
    }
}

