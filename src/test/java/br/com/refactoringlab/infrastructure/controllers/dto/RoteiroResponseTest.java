package br.com.refactoringlab.infrastructure.controllers.dto;

import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.domain.enums.StatusRoteiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoteiroResponseTest {

    @Test
    @DisplayName("Deve criar RoteiroResponse a partir de um Roteiro")
    void deveCriarRoteiroResponseAPartirDeUmRoteiro() {
        var roteiro = new Roteiro();
        roteiro.setCodigoRoteiro("ROT-001");
        roteiro.setStatusRoteiro(StatusRoteiro.EM_ANDAMENTO);

        var response = RoteiroResponse.from(roteiro);

        assertThat(response.codigoRoteiro()).isEqualTo("ROT-001");
        assertThat(response.statusRoteiro()).isEqualTo(StatusRoteiro.EM_ANDAMENTO);
    }
}