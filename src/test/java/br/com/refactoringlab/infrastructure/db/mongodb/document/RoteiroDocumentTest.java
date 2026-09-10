package br.com.refactoringlab.infrastructure.db.mongodb.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoteiroDocumentTest {

    @Test
    @DisplayName("Deve setar e obter todos os campos")
    void deveSetarEObterTodosOsCampos() {
        var data = LocalDateTime.of(2026, 9, 10, 10, 0);
        var document = new RoteiroDocument();

        document.setId("ROT-ID-01");
        document.setCodigoRoteiro("ROT-2026-01");
        document.setMotoristaId("MOT-123");
        document.setVeiculoPlaca("ABC-1234");
        document.setStatusRoteiro("EM_ANDAMENTO");
        document.setPedidosIds(List.of("PED-001", "PED-002"));
        document.setDataCriacao(data);

        assertThat(document.getId()).isEqualTo("ROT-ID-01");
        assertThat(document.getCodigoRoteiro()).isEqualTo("ROT-2026-01");
        assertThat(document.getMotoristaId()).isEqualTo("MOT-123");
        assertThat(document.getVeiculoPlaca()).isEqualTo("ABC-1234");
        assertThat(document.getStatusRoteiro()).isEqualTo("EM_ANDAMENTO");
        assertThat(document.getPedidosIds()).containsExactly("PED-001", "PED-002");
        assertThat(document.getDataCriacao()).isEqualTo(data);
    }
}