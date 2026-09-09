package br.com.refactoringlab.application.dto;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RastreioPedidoEventTest {

    @Test
    @DisplayName("Deve expor campos do evento de rastreio")
    void deveExporCamposDoEventoDeRastreio() {
        var data = LocalDateTime.of(2026, 9, 8, 15, 0);
        var event = new RastreioPedidoEvent(
                "PED-9",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Cliente ausente",
                "user-9",
                data
        );

        assertThat(event.pedidoId()).isEqualTo("PED-9");
        assertThat(event.statusPedido()).isEqualTo(StatusPedido.INSUCESSO);
        assertThat(event.statusOcorrencia()).isEqualTo(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE);
        assertThat(event.descricao()).isEqualTo("Cliente ausente");
        assertThat(event.usuarioId()).isEqualTo("user-9");
        assertThat(event.dataHora()).isEqualTo(data);
    }
}

