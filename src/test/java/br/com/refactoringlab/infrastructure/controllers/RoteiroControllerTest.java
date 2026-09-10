package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.CriarRoteiroInput;
import br.com.refactoringlab.application.usecases.CriarRoteiroUseCase;
import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.domain.enums.StatusRoteiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoteiroController.class)
class RoteiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CriarRoteiroUseCase criarRoteiroUseCase;

    @Test
    @DisplayName("Deve criar roteiro e retornar 201 com dados mapeados")
    void deveCriarRoteiroERetornar201() throws Exception {
        var roteiroCriado = new Roteiro();
        roteiroCriado.setCodigoRoteiro("ROT-2026-001");
        roteiroCriado.setStatusRoteiro(StatusRoteiro.EM_ANDAMENTO);

        when(criarRoteiroUseCase.executar(any(CriarRoteiroInput.class))).thenReturn(roteiroCriado);

        var payload = """
                {
                  "codigoRoteiro": "ROT-2026-001",
                  "motoristaId": "MOT-123",
                  "veiculoPlaca": "ABC-1234",
                  "pedidosIds": ["PED-1", "PED-2"]
                }
                """;

        mockMvc.perform(post("/v1/roteiros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoRoteiro").value("ROT-2026-001"))
                .andExpect(jsonPath("$.statusRoteiro").value("EM_ANDAMENTO"));

        var captor = ArgumentCaptor.forClass(CriarRoteiroInput.class);
        verify(criarRoteiroUseCase).executar(captor.capture());

        var input = captor.getValue();
        assertThat(input.codigoRoteiro()).isEqualTo("ROT-2026-001");
        assertThat(input.motoristaId()).isEqualTo("MOT-123");
        assertThat(input.veiculoPlaca()).isEqualTo("ABC-1234");
        assertThat(input.pedidosIds()).containsExactly("PED-1", "PED-2");
    }
}