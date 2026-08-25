package br.com.refactoringlab.infrastructure.db.mongodb.mapper;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import org.springframework.stereotype.Component;

@Component
public class PedidoDocumentMapper {

    public PedidoDocument toDocument(Pedido domain) {
        if (domain == null) {
            return null;
        }

        PedidoDocument doc = new PedidoDocument();
        doc.setId(domain.getId());
        doc.setCodigoInterno(domain.getCodigoInterno());
        doc.setClienteGuid(domain.getClienteGuid());
        doc.setClienteNome(domain.getClienteNome());
        doc.setCodigoRoteiro(domain.getCodigoRoteiro());
        doc.setCodigoRastreio(domain.getCodigoRastreio());
        doc.setNumeroNotaFiscal(domain.getNumeroNotaFiscal());
        doc.setChaveNfe(domain.getChaveNfe());
        doc.setStatusPedido(domain.getStatusPedido());
        doc.setStatusUltimaOcorrencia(domain.getStatusUltimaOcorrencia());
        doc.setNomeDestinatario(domain.getNomeDestinatario());
        doc.setCpfCnpjDestinatario(domain.getCpfCnpjDestinatario());
        doc.setEnderecoDestinatario(domain.getEnderecoDestinatario());
        doc.setNomeRemetente(domain.getNomeRemetente());
        doc.setCpfCnpjRemetente(domain.getCpfCnpjRemetente());
        doc.setEnderecoRemetente(domain.getEnderecoRemetente());
        doc.setCodigoCentroDistribuicao(domain.getCodigoCentroDistribuicao());
        doc.setNomeCentroDistribuicao(domain.getNomeCentroDistribuicao());
        doc.setEnderecoCentroDistribuicao(domain.getEnderecoCentroDistribuicao());
        doc.setDataCriacao(domain.getDataCriacao());
        doc.setDataPrometidaEntrega(domain.getDataPrometidaEntrega());
        doc.setDataUltimoStatus(domain.getDataUltimoStatus());
        doc.setQuantidadeTentativasEntrega(domain.getQuantidadeTentativasEntrega());

        return doc;
    }

    public Pedido toDomain(PedidoDocument doc) {
        if (doc == null) {
            return null;
        }

        Pedido domain = new Pedido();
        domain.setId(doc.getId());
        domain.setCodigoInterno(doc.getCodigoInterno());
        domain.setClienteGuid(doc.getClienteGuid());
        domain.setClienteNome(doc.getClienteNome());
        domain.setCodigoRoteiro(doc.getCodigoRoteiro());
        domain.setCodigoRastreio(doc.getCodigoRastreio());
        domain.setNumeroNotaFiscal(doc.getNumeroNotaFiscal());
        domain.setChaveNfe(doc.getChaveNfe());
        domain.setStatusPedido(doc.getStatusPedido());
        domain.setStatusUltimaOcorrencia(doc.getStatusUltimaOcorrencia());
        domain.setNomeDestinatario(doc.getNomeDestinatario());
        domain.setCpfCnpjDestinatario(doc.getCpfCnpjDestinatario());
        domain.setEnderecoDestinatario(doc.getEnderecoDestinatario());
        domain.setNomeRemetente(doc.getNomeRemetente());
        domain.setCpfCnpjRemetente(doc.getCpfCnpjRemetente());
        domain.setEnderecoRemetente(doc.getEnderecoRemetente());
        domain.setCodigoCentroDistribuicao(doc.getCodigoCentroDistribuicao());
        domain.setNomeCentroDistribuicao(doc.getNomeCentroDistribuicao());
        domain.setEnderecoCentroDistribuicao(doc.getEnderecoCentroDistribuicao());
        domain.setDataCriacao(doc.getDataCriacao());
        domain.setDataPrometidaEntrega(doc.getDataPrometidaEntrega());
        domain.setDataUltimoStatus(doc.getDataUltimoStatus());
        domain.setQuantidadeTentativasEntrega(doc.getQuantidadeTentativasEntrega());

        return domain;
    }
}

