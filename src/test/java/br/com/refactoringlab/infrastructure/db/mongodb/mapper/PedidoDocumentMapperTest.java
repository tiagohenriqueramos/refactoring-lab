package br.com.refactoringlab.infrastructure.db.mongodb.mapper;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoDocumentMapperTest {

    private final PedidoDocumentMapper mapper = new PedidoDocumentMapper();

    @Test
    @DisplayName("Deve retornar nulo ao converter domínio nulo para documento")
    void deveRetornarNuloAoConverterDominioNuloParaDocumento() {
        assertThat(mapper.toDocument(null)).isNull();
    }

    @Test
    @DisplayName("Deve retornar nulo ao converter documento nulo para domínio")
    void deveRetornarNuloAoConverterDocumentoNuloParaDominio() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    @DisplayName("Deve mapear todos os campos de Pedido para PedidoDocument")
    void deveMapearTodosOsCamposParaDocumento() {
        var dataCriacao = LocalDateTime.of(2026, 8, 25, 10, 0);
        var dataPrometida = LocalDateTime.of(2026, 8, 27, 18, 0);
        var dataUltimoStatus = LocalDateTime.of(2026, 8, 26, 9, 30);
        var endereco = new Endereco("Rua A", "10", "Casa", "Centro", "Sao Paulo", "SP", "01000-000");

        var pedido = new Pedido();
        pedido.setId("PED-1");
        pedido.setCodigoInterno(1001L);
        pedido.setClienteGuid("GUID-1");
        pedido.setClienteNome("Cliente 1");
        pedido.setCodigoRoteiro("ROT-1");
        pedido.setCodigoRastreio("RAST-1");
        pedido.setNumeroNotaFiscal("NF-1");
        pedido.setChaveNfe("CHAVE-1");
        pedido.setStatusPedido(StatusPedido.EM_TRANSITO);
        pedido.setStatusUltimaOcorrencia(StatusOcorrencia.RECEBIDO_CD);
        pedido.setNomeDestinatario("Destinatario 1");
        pedido.setCpfCnpjDestinatario("12345678900");
        pedido.setEnderecoDestinatario(endereco);
        pedido.setNomeRemetente("Remetente 1");
        pedido.setCpfCnpjRemetente("00987654321");
        pedido.setEnderecoRemetente(endereco);
        pedido.setCodigoCentroDistribuicao("CD-01");
        pedido.setNomeCentroDistribuicao("Centro Oeste");
        pedido.setEnderecoCentroDistribuicao(endereco);
        pedido.setDataCriacao(dataCriacao);
        pedido.setDataPrometidaEntrega(dataPrometida);
        pedido.setDataUltimoStatus(dataUltimoStatus);
        pedido.setQuantidadeTentativasEntrega(2);

        var document = mapper.toDocument(pedido);

        assertThat(document).isNotNull();
        assertThat(document.getId()).isEqualTo("PED-1");
        assertThat(document.getCodigoInterno()).isEqualTo(1001L);
        assertThat(document.getClienteGuid()).isEqualTo("GUID-1");
        assertThat(document.getClienteNome()).isEqualTo("Cliente 1");
        assertThat(document.getCodigoRoteiro()).isEqualTo("ROT-1");
        assertThat(document.getCodigoRastreio()).isEqualTo("RAST-1");
        assertThat(document.getNumeroNotaFiscal()).isEqualTo("NF-1");
        assertThat(document.getChaveNfe()).isEqualTo("CHAVE-1");
        assertThat(document.getStatusPedido()).isEqualTo(StatusPedido.EM_TRANSITO);
        assertThat(document.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.RECEBIDO_CD);
        assertThat(document.getNomeDestinatario()).isEqualTo("Destinatario 1");
        assertThat(document.getCpfCnpjDestinatario()).isEqualTo("12345678900");
        assertThat(document.getEnderecoDestinatario()).isEqualTo(endereco);
        assertThat(document.getNomeRemetente()).isEqualTo("Remetente 1");
        assertThat(document.getCpfCnpjRemetente()).isEqualTo("00987654321");
        assertThat(document.getEnderecoRemetente()).isEqualTo(endereco);
        assertThat(document.getCodigoCentroDistribuicao()).isEqualTo("CD-01");
        assertThat(document.getNomeCentroDistribuicao()).isEqualTo("Centro Oeste");
        assertThat(document.getEnderecoCentroDistribuicao()).isEqualTo(endereco);
        assertThat(document.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(document.getDataPrometidaEntrega()).isEqualTo(dataPrometida);
        assertThat(document.getDataUltimoStatus()).isEqualTo(dataUltimoStatus);
        assertThat(document.getQuantidadeTentativasEntrega()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve mapear todos os campos de PedidoDocument para Pedido")
    void deveMapearTodosOsCamposParaDominio() {
        var dataCriacao = LocalDateTime.of(2026, 8, 25, 10, 0);
        var dataPrometida = LocalDateTime.of(2026, 8, 27, 18, 0);
        var dataUltimoStatus = LocalDateTime.of(2026, 8, 26, 9, 30);
        var endereco = new Endereco("Rua B", "20", "Apto 2", "Bairro B", "Rio", "RJ", "20000-000");

        var document = new PedidoDocument();
        document.setId("PED-2");
        document.setCodigoInterno(2002L);
        document.setClienteGuid("GUID-2");
        document.setClienteNome("Cliente 2");
        document.setCodigoRoteiro("ROT-2");
        document.setCodigoRastreio("RAST-2");
        document.setNumeroNotaFiscal("NF-2");
        document.setChaveNfe("CHAVE-2");
        document.setStatusPedido(StatusPedido.RECEBIDO);
        document.setStatusUltimaOcorrencia(StatusOcorrencia.EM_TRANSITO);
        document.setNomeDestinatario("Destinatario 2");
        document.setCpfCnpjDestinatario("11111111111");
        document.setEnderecoDestinatario(endereco);
        document.setNomeRemetente("Remetente 2");
        document.setCpfCnpjRemetente("22222222222");
        document.setEnderecoRemetente(endereco);
        document.setCodigoCentroDistribuicao("CD-02");
        document.setNomeCentroDistribuicao("Centro Sul");
        document.setEnderecoCentroDistribuicao(endereco);
        document.setDataCriacao(dataCriacao);
        document.setDataPrometidaEntrega(dataPrometida);
        document.setDataUltimoStatus(dataUltimoStatus);
        document.setQuantidadeTentativasEntrega(1);

        var pedido = mapper.toDomain(document);

        assertThat(pedido).isNotNull();
        assertThat(pedido.getId()).isEqualTo("PED-2");
        assertThat(pedido.getCodigoInterno()).isEqualTo(2002L);
        assertThat(pedido.getClienteGuid()).isEqualTo("GUID-2");
        assertThat(pedido.getClienteNome()).isEqualTo("Cliente 2");
        assertThat(pedido.getCodigoRoteiro()).isEqualTo("ROT-2");
        assertThat(pedido.getCodigoRastreio()).isEqualTo("RAST-2");
        assertThat(pedido.getNumeroNotaFiscal()).isEqualTo("NF-2");
        assertThat(pedido.getChaveNfe()).isEqualTo("CHAVE-2");
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.RECEBIDO);
        assertThat(pedido.getStatusUltimaOcorrencia()).isEqualTo(StatusOcorrencia.EM_TRANSITO);
        assertThat(pedido.getNomeDestinatario()).isEqualTo("Destinatario 2");
        assertThat(pedido.getCpfCnpjDestinatario()).isEqualTo("11111111111");
        assertThat(pedido.getEnderecoDestinatario()).isEqualTo(endereco);
        assertThat(pedido.getNomeRemetente()).isEqualTo("Remetente 2");
        assertThat(pedido.getCpfCnpjRemetente()).isEqualTo("22222222222");
        assertThat(pedido.getEnderecoRemetente()).isEqualTo(endereco);
        assertThat(pedido.getCodigoCentroDistribuicao()).isEqualTo("CD-02");
        assertThat(pedido.getNomeCentroDistribuicao()).isEqualTo("Centro Sul");
        assertThat(pedido.getEnderecoCentroDistribuicao()).isEqualTo(endereco);
        assertThat(pedido.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(pedido.getDataPrometidaEntrega()).isEqualTo(dataPrometida);
        assertThat(pedido.getDataUltimoStatus()).isEqualTo(dataUltimoStatus);
        assertThat(pedido.getQuantidadeTentativasEntrega()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve manter consistencia no round-trip dominio documento dominio")
    void deveManterConsistenciaNoRoundTrip() {
        var endereco = new Endereco("Rua C", "30", "Bloco 1", "Bairro C", "Curitiba", "PR", "80000-000");
        var dataCriacao = LocalDateTime.of(2026, 8, 25, 8, 0);
        var dataPrometida = LocalDateTime.of(2026, 8, 29, 18, 0);
        var dataUltimoStatus = LocalDateTime.of(2026, 8, 26, 12, 15);

        var original = new Pedido();
        original.setId("PED-ROUND");
        original.setCodigoInterno(3003L);
        original.setClienteGuid("GUID-ROUND");
        original.setClienteNome("Cliente Round");
        original.setCodigoRoteiro("ROT-ROUND");
        original.setCodigoRastreio("RAST-ROUND");
        original.setNumeroNotaFiscal("NF-ROUND");
        original.setChaveNfe("CHAVE-ROUND");
        original.setStatusPedido(StatusPedido.EM_RETORNO);
        original.setStatusUltimaOcorrencia(StatusOcorrencia.DEVOLUCAO);
        original.setNomeDestinatario("Destinatario Round");
        original.setCpfCnpjDestinatario("33333333333");
        original.setEnderecoDestinatario(endereco);
        original.setNomeRemetente("Remetente Round");
        original.setCpfCnpjRemetente("44444444444");
        original.setEnderecoRemetente(endereco);
        original.setCodigoCentroDistribuicao("CD-ROUND");
        original.setNomeCentroDistribuicao("Centro Round");
        original.setEnderecoCentroDistribuicao(endereco);
        original.setDataCriacao(dataCriacao);
        original.setDataPrometidaEntrega(dataPrometida);
        original.setDataUltimoStatus(dataUltimoStatus);
        original.setQuantidadeTentativasEntrega(3);

        var documento = mapper.toDocument(original);
        var convertido = mapper.toDomain(documento);

        assertThat(convertido).isNotNull();
        assertThat(convertido.getId()).isEqualTo(original.getId());
        assertThat(convertido.getCodigoInterno()).isEqualTo(original.getCodigoInterno());
        assertThat(convertido.getClienteGuid()).isEqualTo(original.getClienteGuid());
        assertThat(convertido.getClienteNome()).isEqualTo(original.getClienteNome());
        assertThat(convertido.getCodigoRoteiro()).isEqualTo(original.getCodigoRoteiro());
        assertThat(convertido.getCodigoRastreio()).isEqualTo(original.getCodigoRastreio());
        assertThat(convertido.getNumeroNotaFiscal()).isEqualTo(original.getNumeroNotaFiscal());
        assertThat(convertido.getChaveNfe()).isEqualTo(original.getChaveNfe());
        assertThat(convertido.getStatusPedido()).isEqualTo(original.getStatusPedido());
        assertThat(convertido.getStatusUltimaOcorrencia()).isEqualTo(original.getStatusUltimaOcorrencia());
        assertThat(convertido.getNomeDestinatario()).isEqualTo(original.getNomeDestinatario());
        assertThat(convertido.getCpfCnpjDestinatario()).isEqualTo(original.getCpfCnpjDestinatario());
        assertThat(convertido.getEnderecoDestinatario()).isEqualTo(original.getEnderecoDestinatario());
        assertThat(convertido.getNomeRemetente()).isEqualTo(original.getNomeRemetente());
        assertThat(convertido.getCpfCnpjRemetente()).isEqualTo(original.getCpfCnpjRemetente());
        assertThat(convertido.getEnderecoRemetente()).isEqualTo(original.getEnderecoRemetente());
        assertThat(convertido.getCodigoCentroDistribuicao()).isEqualTo(original.getCodigoCentroDistribuicao());
        assertThat(convertido.getNomeCentroDistribuicao()).isEqualTo(original.getNomeCentroDistribuicao());
        assertThat(convertido.getEnderecoCentroDistribuicao()).isEqualTo(original.getEnderecoCentroDistribuicao());
        assertThat(convertido.getDataCriacao()).isEqualTo(original.getDataCriacao());
        assertThat(convertido.getDataPrometidaEntrega()).isEqualTo(original.getDataPrometidaEntrega());
        assertThat(convertido.getDataUltimoStatus()).isEqualTo(original.getDataUltimoStatus());
        assertThat(convertido.getQuantidadeTentativasEntrega()).isEqualTo(original.getQuantidadeTentativasEntrega());
    }
}

