package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusRoteiroTest {

    @Test
    @DisplayName("Deve retornar descrições configuradas")
    void deveRetornarDescricoes() {
        assertThat(StatusRoteiro.EM_ANDAMENTO.getDescricao()).isEqualTo("Em andamento");
        assertThat(StatusRoteiro.FINALIZADO.getDescricao()).isEqualTo("Finalizado");
        assertThat(StatusRoteiro.CANCELADO.getDescricao()).isEqualTo("Cancelado");
    }

    @Test
    @DisplayName("Deve identificar status finalizados")
    void deveIdentificarStatusFinalizados() {
        assertThat(StatusRoteiro.FINALIZADO.isFinalizado()).isTrue();
        assertThat(StatusRoteiro.CANCELADO.isFinalizado()).isTrue();
        assertThat(StatusRoteiro.EM_ANDAMENTO.isFinalizado()).isFalse();
    }
}