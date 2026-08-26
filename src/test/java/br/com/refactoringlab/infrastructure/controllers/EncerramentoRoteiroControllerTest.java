package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.dto.EncerrarRoteiroInput;
import br.com.refactoringlab.application.usecases.EncerrarRoteiroUseCase;
import br.com.refactoringlab.domain.enums.StatusPedido;
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

@WebMvcTest(EncerramentoRoteiroController.class)
class EncerramentoRoteiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EncerrarRoteiroUseCase encerrarRoteiroUseCase;

    @Test
    @DisplayName("Deve encerrar pedidos de roteiro e retornar 200")
    void deveEncerrarPedidosDeRoteiroERetornar200() throws Exception {
        var output = new EncerramentoPedidoOutput("PED-1", false, "ok", "PED-1", null, "ETAPA-1");
        when(encerrarRoteiroUseCase.executar(any())).thenReturn(List.of(output));

        var payload = """
                [
                  {
                    "pedidoEntregaId": "PED-1",
                    "novoStatus": "ENTREGUE",
                    "motivoInsucesso": null
                  }
                ]
                """;

        mockMvc.perform(post("/encerramento-roteiro/ROT-1")
                        .queryParam("usuarioId", "USR-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pedidoId").value("PED-1"))
                .andExpect(jsonPath("$[0].possuiErro").value(false))
                .andExpect(jsonPath("$[0].etapaAdicionarId").value("ETAPA-1"));

        var captor = ArgumentCaptor.forClass(EncerrarRoteiroInput.class);
        verify(encerrarRoteiroUseCase).executar(captor.capture());

        var input = captor.getValue();
        assertThat(input.roteiroId()).isEqualTo("ROT-1");
        assertThat(input.usuarioId()).isEqualTo("USR-1");
        assertThat(input.itens()).hasSize(1);
        assertThat(input.itens().get(0).pedidoEntregaId()).isEqualTo("PED-1");
        assertThat(input.itens().get(0).novoStatus()).isEqualTo(StatusPedido.ENTREGUE);
    }

    @Test
    @DisplayName("Deve retornar 500 quando ocorrer excecao no use case")
    void deveRetornar500QuandoOcorrerExcecaoNoUseCase() throws Exception {
        when(encerrarRoteiroUseCase.executar(any())).thenThrow(new RuntimeException("falha"));

        var payload = """
                [
                  {
                    "pedidoEntregaId": "PED-2",
                    "novoStatus": "CANCELADO",
                    "motivoInsucesso": "cliente ausente"
                  }
                ]
                """;

        mockMvc.perform(post("/encerramento-roteiro/ROT-2")
                        .queryParam("usuarioId", "USR-2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isInternalServerError());
    }
}

