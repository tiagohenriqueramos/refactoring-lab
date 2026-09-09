package br.com.refactoringlab.infrastructure.db.mongodb.document;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RastreioDocumentTest {

    @Test
    @DisplayName("Deve setar e obter todos os campos")
    void deveSetarEObterTodosOsCampos() {
        var data = LocalDateTime.of(2026, 9, 8, 14, 30);
        var document = new RastreioDocument();

        document.setId("R-1");
        document.setPedidoId("PED-1");
        document.setStatusPedido(StatusPedido.INSUCESSO);
        document.setStatusOcorrencia(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE);
        document.setDescricao("Cliente ausente");
        document.setUsuarioId("user-1");
        document.setDataHora(data);

        assertThat(document.getId()).isEqualTo("R-1");
        assertThat(document.getPedidoId()).isEqualTo("PED-1");
        assertThat(document.getStatusPedido()).isEqualTo(StatusPedido.INSUCESSO);
        assertThat(document.getStatusOcorrencia()).isEqualTo(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE);
        assertThat(document.getDescricao()).isEqualTo("Cliente ausente");
        assertThat(document.getUsuarioId()).isEqualTo("user-1");
        assertThat(document.getDataHora()).isEqualTo(data);
    }
}

