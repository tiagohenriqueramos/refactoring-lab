package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.CriarPedidoInput;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarPedidoUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private CriarPedidoUseCase useCase;

    @Test
    @DisplayName("Deve criar pedido com dados do input e salvar no repositorio")
    void deveCriarPedidoComDadosDoInputESalvarNoRepositorio() {
        var endereco = new Endereco("Rua A", "10", "Ap 11", "Centro", "Sao Paulo", "SP", "01000-000");
        var dataPrometida = LocalDateTime.of(2026, 8, 30, 18, 0);

        var input = new CriarPedidoInput(
                999L,
                "GUID-999",
                "Cliente XPTO",
                "ROT-77",
                "RAST-77",
                "NF-123",
                "CHAVE-123",
                StatusPedido.RECEBIDO,
                StatusOcorrencia.RECEBIDO_CD,
                "Destinatario",
                "12345678900",
                endereco,
                "Remetente",
                "00987654321",
                endereco,
                "CD-01",
                "Centro Oeste",
                endereco,
                dataPrometida
        );

        var pedidoSalvo = new Pedido();
        pedidoSalvo.setId("PED-1");
        when(pedidoGateway.salvar(any(Pedido.class))).thenReturn(pedidoSalvo);

        var resultado = useCase.executar(input);

        assertThat(resultado).isSameAs(pedidoSalvo);

        var pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoGateway).salvar(pedidoCaptor.capture());
        var pedidoCapturado = pedidoCaptor.getValue();

        assertThat(pedidoCapturado.getCodigoInterno()).isEqualTo(999L);
        assertThat(pedidoCapturado.getClienteGuid()).isEqualTo("GUID-999");
        assertThat(pedidoCapturado.getClienteNome()).isEqualTo("Cliente XPTO");
        assertThat(pedidoCapturado.getCodigoRoteiro()).isEqualTo("ROT-77");
        assertThat(pedidoCapturado.getCodigoRastreio()).isEqualTo("RAST-77");
        assertThat(pedidoCapturado.getNumeroNotaFiscal()).isEqualTo("NF-123");
        assertThat(pedidoCapturado.getChaveNfe()).isEqualTo("CHAVE-123");
        assertThat(pedidoCapturado.getStatusPedido()).isEqualTo(StatusPedido.RECEBIDO);
        assertThat(pedidoCapturado.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.RECEBIDO_CD);
        assertThat(pedidoCapturado.getNomeDestinatario()).isEqualTo("Destinatario");
        assertThat(pedidoCapturado.getCpfCnpjDestinatario()).isEqualTo("12345678900");
        assertThat(pedidoCapturado.getEnderecoDestinatario()).isEqualTo(endereco);
        assertThat(pedidoCapturado.getNomeRemetente()).isEqualTo("Remetente");
        assertThat(pedidoCapturado.getCpfCnpjRemetente()).isEqualTo("00987654321");
        assertThat(pedidoCapturado.getEnderecoRemetente()).isEqualTo(endereco);
        assertThat(pedidoCapturado.getCodigoCentroDistribuicao()).isEqualTo("CD-01");
        assertThat(pedidoCapturado.getNomeCentroDistribuicao()).isEqualTo("Centro Oeste");
        assertThat(pedidoCapturado.getEnderecoCentroDistribuicao()).isEqualTo(endereco);
        assertThat(pedidoCapturado.getDataPrometidaEntrega()).isEqualTo(dataPrometida);

        assertThat(pedidoCapturado.getDataCriacao()).isNotNull();
        assertThat(pedidoCapturado.getDataUltimoStatus()).isNotNull();
        assertThat(pedidoCapturado.getQuantidadeTentativasEntrega()).isZero();
    }
}

