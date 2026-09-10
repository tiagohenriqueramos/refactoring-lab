package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.BuscarPedidosPorIdsUseCase;
import br.com.refactoringlab.application.usecases.BuscarPedidosPorRoteiroUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private CriarPedidoUseCase criarPedidoUseCase;

    @MockitoBean
    private BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;

    @MockitoBean
    private BuscarPedidosPorIdsUseCase buscarPedidosPorIdsUseCase;

    @MockitoBean
    private BuscarPedidosPorRoteiroUseCase buscarPedidosPorRoteiroUseCase;

    @Test
    @DisplayName("Deve criar pedido e retornar 201")
    void deveCriarPedidoERetornar201() throws Exception {
        var pedidoCriado = new Pedido();
        pedidoCriado.setId("PED-100");
        pedidoCriado.setCodigoInterno(100L);
        pedidoCriado.setStatusPedido(StatusPedido.RECEBIDO);

        when(criarPedidoUseCase.executar(any())).thenReturn(pedidoCriado);

        var payload = """
                {
                  "codigoInterno": 100,
                  "clienteGuid": "GUID-100",
                  "clienteNome": "Cliente 100",
                  "codigoRoteiro": "ROT-100",
                  "codigoRastreio": "RAST-100",
                  "numeroNotaFiscal": "NF-100",
                  "chaveNfe": "CHAVE-100",
                  "statusPedido": "RECEBIDO",
                  "statusUltimaOcorrencia": "RECEBIDO_CD",
                  "nomeDestinatario": "Destinatario 100",
                  "cpfCnpjDestinatario": "12345678900",
                  "enderecoDestinatario": {
                    "logradouro": "Rua A",
                    "numero": "10",
                    "complemento": "Ap 11",
                    "bairro": "Centro",
                    "cidade": "Sao Paulo",
                    "uf": "SP",
                    "cep": "01000-000"
                  },
                  "nomeRemetente": "Remetente 100",
                  "cpfCnpjRemetente": "00987654321",
                  "enderecoRemetente": {
                    "logradouro": "Rua B",
                    "numero": "20",
                    "complemento": "Casa",
                    "bairro": "Bairro B",
                    "cidade": "Rio",
                    "uf": "RJ",
                    "cep": "20000-000"
                  },
                  "codigoCentroDistribuicao": "CD-100",
                  "nomeCentroDistribuicao": "Centro 100",
                  "enderecoCentroDistribuicao": {
                    "logradouro": "Rua C",
                    "numero": "30",
                    "complemento": "Galpao",
                    "bairro": "Bairro C",
                    "cidade": "Curitiba",
                    "uf": "PR",
                    "cep": "80000-000"
                  },
                  "dataPrometidaEntrega": "2026-08-30T18:00:00"
                }
                """;

        mockMvc.perform(post("/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("PED-100"))
                .andExpect(jsonPath("$.codigoInterno").value(100))
                .andExpect(jsonPath("$.statusPedido").value("RECEBIDO"));
    }

    @Test
    @DisplayName("Deve buscar pedido por id com sucesso")
    void deveBuscarPedidoPorIdComSucesso() throws Exception {
        var pedido = new Pedido();
        pedido.setId("PED-200");
        pedido.setCodigoInterno(200L);

        when(buscarPedidoPorIdUseCase.executar("PED-200")).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/v1/pedidos/PED-200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("PED-200"))
                .andExpect(jsonPath("$.codigoInterno").value(200));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar pedido inexistente")
    void deveRetornar404AoBuscarPedidoInexistente() throws Exception {
        when(buscarPedidoPorIdUseCase.executar("PED-404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/pedidos/PED-404"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar todos os pedidos com sucesso")
    void deveBuscarTodosOsPedidosComSucesso() throws Exception {
        var pedido1 = new Pedido();
        pedido1.setId("PED-100");
        pedido1.setCodigoInterno(100L);

        var pedido2 = new Pedido();
        pedido2.setId("PED-200");
        pedido2.setCodigoInterno(200L);

        when(buscarPedidosPorIdsUseCase.executar()).thenReturn(List.of(pedido1, pedido2));

        mockMvc.perform(get("/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("PED-100"))
                .andExpect(jsonPath("$[0].codigoInterno").value(100))
                .andExpect(jsonPath("$[1].id").value("PED-200"))
                .andExpect(jsonPath("$[1].codigoInterno").value(200));
    }

    @Test
    @DisplayName("Deve buscar pedidos por roteiro com sucesso")
    void deveBuscarPedidosPorRoteiroComSucesso() throws Exception {
        var pedido = new Pedido();
        pedido.setId("PED-300");
        pedido.setCodigoInterno(300L);

        when(buscarPedidosPorRoteiroUseCase.executar("ROT-ABC")).thenReturn(List.of(pedido));

        mockMvc.perform(get("/v1/pedidos/roteiro/ROT-ABC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value("PED-300"))
                .andExpect(jsonPath("$[0].codigoInterno").value(300));
    }
}

