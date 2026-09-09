package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModalidadeOperacaoTest {

    @Test
    @DisplayName("Deve retornar descricoes corretas das modalidades")
    void deveRetornarDescricoesCorretasDasModalidades() {
        assertThat(ModalidadeOperacao.OUTBOUND.getDescricao()).isEqualTo("Fluxo normal de entrega");
        assertThat(ModalidadeOperacao.INBOUND_DROP_OFF.getDescricao())
                .isEqualTo("Fluxo de devolução via entrega em ponto/loja pelo cliente");
        assertThat(ModalidadeOperacao.INBOUND_PICK_UP.getDescricao())
                .isEqualTo("Fluxo de devolução via coleta domiciliar pelo motorista");
    }
}

