package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RastreioPedidoTest {

    @Test
    @DisplayName("Deve criar rastreio com data informada")
    void deveCriarRastreioComDataInformada() {
        var data = LocalDateTime.of(2026, 9, 8, 12, 0);

        var rastreio = new RastreioPedido(
                "PED-1",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Cliente ausente",
                "user-1",
                data
        );

        assertThat(rastreio.getId()).isNull();
        assertThat(rastreio.getPedidoId()).isEqualTo("PED-1");
        assertThat(rastreio.getStatusPedido()).isEqualTo(StatusPedido.INSUCESSO);
        assertThat(rastreio.getStatusOcorrencia()).isEqualTo(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE);
        assertThat(rastreio.getDescricao()).isEqualTo("Cliente ausente");
        assertThat(rastreio.getUsuarioId()).isEqualTo("user-1");
        assertThat(rastreio.getDataHora()).isEqualTo(data);
    }

    @Test
    @DisplayName("Deve usar data atual quando data nao for informada")
    void deveUsarDataAtualQuandoDataNaoForInformada() {
        var antes = LocalDateTime.now().minusSeconds(1);

        var rastreio = new RastreioPedido(
                "PED-2",
                StatusPedido.RECEBIDO,
                StatusOcorrencia.RECEBIDO_CD,
                "Recebido",
                "user-2",
                null
        );

        assertThat(rastreio.getDataHora()).isAfter(antes);
        assertThat(rastreio.getDataHora()).isNotNull();
    }
}

