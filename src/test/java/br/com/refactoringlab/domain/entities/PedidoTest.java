package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PedidoTest {

    @Test
    @DisplayName("Deve atualizar o status do pedido e registrar a ocorrência com sucesso")
    void deveAtualizarStatusEOcorrencia() {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.RECEBIDO);

        // Act
        pedido.atualizarStatus(StatusPedido.EM_TRANSITO, StatusOcorrencia.EM_TRANSITO);

        // Assert
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.EM_TRANSITO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.EM_TRANSITO);
        assertThat(pedido.getDataUltimoStatus()).isNotNull();
        assertThat(pedido.getQuantidadeTentativasEntrega()).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = StatusOcorrencia.class, names = {"INSUCESSO_ENDERECO_NAO_ENCONTRADO", "INSUCESSO_AUSENTE", "INSUCESSO_RECUSADO"})
    @DisplayName("Deve incrementar a quantidade de tentativas para ocorrências de insucesso")
    void deveIncrementarTentativasParaOcorrenciasDeInsucesso(StatusOcorrencia ocorrenciaInsucesso) {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.EM_TRANSITO);

        // Act
        pedido.atualizarStatus(StatusPedido.EM_TRANSITO, ocorrenciaInsucesso);

        // Assert
        assertThat(pedido.getQuantidadeTentativasEntrega()).isEqualTo(1);
    }

    @Test
    @DisplayName("Não deve incrementar tentativas de entrega para ocorrências que não sejam de insucesso")
    void naoDeveIncrementarTentativasParaOutrasOcorrencias() {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.EM_TRANSITO);

        // Act
        pedido.atualizarStatus(StatusPedido.ENTREGUE, StatusOcorrencia.ENTREGUE);

        // Assert
        assertThat(pedido.getQuantidadeTentativasEntrega()).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"ENTREGUE", "DEVOLVIDO", "CANCELADO", "SINISTRO"})
    @DisplayName("Deve lançar exceção ao tentar atualizar um pedido que já está em status finalizado")
    void deveLancarExcecaoAoAtualizarPedidoFinalizado(StatusPedido statusFinal) {
        // Arrange
        var pedido = new Pedido("123", 1001L, "GUID-123", statusFinal);

        // Act & Assert
        assertThatThrownBy(() -> pedido.atualizarStatus(StatusPedido.EM_TRANSITO, StatusOcorrencia.RECEBIDO_CD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O pedido já está finalizado.");
    }

    @Test
    @DisplayName("Deve permitir atualizar status quando status inicial é nulo e ocorrência é nula")
    void deveAtualizarComStatusInicialEOcorrenciaNulos() {
        var pedido = new Pedido();

        pedido.atualizarStatus(StatusPedido.RECEBIDO, null);

        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.RECEBIDO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isNull();
        assertThat(pedido.getQuantidadeTentativasEntrega()).isZero();
        assertThat(pedido.getDataUltimoStatus()).isNotNull();
    }

    @Test
    @DisplayName("Deve registrar tratativa de encerramento incrementando tentativas")
    void deveRegistrarTratativaEncerramento() {
        var pedido = new Pedido("123", 1001L, "GUID-123", StatusPedido.EM_TRANSITO);

        pedido.registrarTratativaEncerramento(StatusPedido.DEVOLVIDO, StatusOcorrencia.DEVOLUCAO);

        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.DEVOLVIDO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.DEVOLUCAO);
        assertThat(pedido.getQuantidadeTentativasEntrega()).isEqualTo(1);
        assertThat(pedido.getDataUltimoStatus()).isNotNull();
    }

    @Test
    @DisplayName("Deve setar e obter todos os campos do pedido")
    void deveSetarEObterTodosOsCampos() {
        var pedido = new Pedido();
        var endereco = new Endereco("Rua A", "10", "Ap 11", "Centro", "Sao Paulo", "SP", "01000-000");
        var dataCriacao = LocalDateTime.now().minusDays(2);
        var dataPrometida = LocalDateTime.now().plusDays(2);
        var dataStatus = LocalDateTime.now();

        pedido.setId("PED-1");
        pedido.setCodigoInterno(999L);
        pedido.setClienteGuid("GUID-999");
        pedido.setClienteNome("Cliente XPTO");
        pedido.setCodigoRoteiro("ROT-77");
        pedido.setCodigoRastreio("RAST-77");
        pedido.setNumeroNotaFiscal("NF-123");
        pedido.setChaveNfe("CHAVE-123");
        pedido.setStatusPedido(StatusPedido.EM_RETORNO);
        pedido.setStatusUltimaOcorrencia(StatusOcorrencia.AVARIA);
        pedido.setNomeDestinatario("Destinatario");
        pedido.setCpfCnpjDestinatario("12345678900");
        pedido.setEnderecoDestinatario(endereco);
        pedido.setNomeRemetente("Remetente");
        pedido.setCpfCnpjRemetente("00987654321");
        pedido.setEnderecoRemetente(endereco);
        pedido.setCodigoCentroDistribuição("CD-01");
        pedido.setNomeCentroDistribuição("Centro Oeste");
        pedido.setEnderecoCentroDistribuição(endereco);
        pedido.setDataCriacao(dataCriacao);
        pedido.setDataPrometidaEntrega(dataPrometida);
        pedido.setDataUltimoStatus(dataStatus);
        pedido.setQuantidadeTentativasEntrega(4);

        assertThat(pedido.getId()).isEqualTo("PED-1");
        assertThat(pedido.getCodigoInterno()).isEqualTo(999L);
        assertThat(pedido.getClienteGuid()).isEqualTo("GUID-999");
        assertThat(pedido.getClienteNome()).isEqualTo("Cliente XPTO");
        assertThat(pedido.getCodigoRoteiro()).isEqualTo("ROT-77");
        assertThat(pedido.getCodigoRastreio()).isEqualTo("RAST-77");
        assertThat(pedido.getNumeroNotaFiscal()).isEqualTo("NF-123");
        assertThat(pedido.getChaveNfe()).isEqualTo("CHAVE-123");
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.EM_RETORNO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.AVARIA);
        assertThat(pedido.getNomeDestinatario()).isEqualTo("Destinatario");
        assertThat(pedido.getCpfCnpjDestinatario()).isEqualTo("12345678900");
        assertThat(pedido.getEnderecoDestinatario()).isEqualTo(endereco);
        assertThat(pedido.getNomeRemetente()).isEqualTo("Remetente");
        assertThat(pedido.getCpfCnpjRemetente()).isEqualTo("00987654321");
        assertThat(pedido.getEnderecoRemetente()).isEqualTo(endereco);
        assertThat(pedido.getCodigoCentroDistribuição()).isEqualTo("CD-01");
        assertThat(pedido.getNomeCentroDistribuição()).isEqualTo("Centro Oeste");
        assertThat(pedido.getEnderecoCentroDistribuição()).isEqualTo(endereco);
        assertThat(pedido.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(pedido.getDataPrometidaEntrega()).isEqualTo(dataPrometida);
        assertThat(pedido.getDataUltimoStatus()).isEqualTo(dataStatus);
        assertThat(pedido.getQuantidadeTentativasEntrega()).isEqualTo(4);
    }

    @Test
    @DisplayName("Deve considerar igualdade por id")
    void deveCompararPedidoPorId() {
        var pedido1 = new Pedido();
        pedido1.setId("PED-IGUAL");
        var pedido2 = new Pedido();
        pedido2.setId("PED-IGUAL");
        var pedido3 = new Pedido();
        pedido3.setId("PED-DIFERENTE");

        assertThat(pedido1)
                .isNotNull()
                .isEqualTo(pedido2)
                .isNotEqualTo(pedido3)
                .isNotEqualTo(null);
        assertThat(pedido1.hashCode()).isEqualTo(pedido2.hashCode());
    }
}