package br.com.refactoringlab.infrastructure.db.mongodb.mapper;

import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.infrastructure.db.mongodb.document.RoteiroDocument;
import org.springframework.stereotype.Component;

@Component
public class RoteiroDocumentMapper {

    public RoteiroDocument toDocument(Roteiro domain) {
        if (domain == null) return null;

        RoteiroDocument doc = new RoteiroDocument();
        doc.setId(domain.getId());
        doc.setCodigoRoteiro(domain.getCodigoRoteiro());
        doc.setMotoristaId(domain.getMotoristaId());
        doc.setVeiculoPlaca(domain.getVeiculoPlaca());
        doc.setStatusRoteiro(domain.getStatusRoteiro() != null ? domain.getStatusRoteiro().name() : null);
        doc.setPedidosIds(domain.getPedidosIds());
        doc.setDataCriacao(domain.getDataCriacao());

        return doc;
    }

    public Roteiro toDomain(RoteiroDocument doc) {
        if (doc == null) return null;

        Roteiro domain = new Roteiro();
        domain.setId(doc.getId());
        domain.setCodigoRoteiro(doc.getCodigoRoteiro());
        domain.setMotoristaId(doc.getMotoristaId());
        domain.setVeiculoPlaca(doc.getVeiculoPlaca());
        // Ajuste conforme o seu Enum de StatusRoteiro no domain
        domain.setPedidosIds(doc.getPedidosIds());
        domain.setDataCriacao(doc.getDataCriacao());

        return domain;
    }
}