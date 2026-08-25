package br.com.refactoringlab.application.dto;

import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.domain.valueobjects.Endereco;

import java.time.LocalDateTime;

public record CriarPedidoInput(
        Long codigoInterno,
        String clienteGuid,
        String clienteNome,
        String codigoRoteiro,
        String codigoRastreio,
        String numeroNotaFiscal,
        String chaveNfe,
        StatusPedido statusPedido,
        StatusOcorrencia statusUltimaOcorrencia,
        String nomeDestinatario,
        String cpfCnpjDestinatario,
        Endereco enderecoDestinatario,
        String nomeRemetente,
        String cpfCnpjRemetente,
        Endereco enderecoRemetente,
        String codigoCentroDistribuicao,
        String nomeCentroDistribuicao,
        Endereco enderecoCentroDistribuicao,
        LocalDateTime dataPrometidaEntrega
) {
}

