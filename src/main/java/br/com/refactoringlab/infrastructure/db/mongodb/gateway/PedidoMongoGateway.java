package br.com.refactoringlab.infrastructure.db.mongodb.gateway;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.PedidoDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoPedidoSpringRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PedidoMongoGateway implements PedidoGateway {

    private final MongoPedidoSpringRepository mongoRepository;
    private final PedidoDocumentMapper mapper;

    public PedidoMongoGateway(MongoPedidoSpringRepository mongoRepository, PedidoDocumentMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoDocument document = mapper.toDocument(pedido);
        PedidoDocument savedDocument = mongoRepository.save(document);
        return mapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Pedido> buscarPorIds(List<String> ids) {
        List<PedidoDocument> documents = mongoRepository.findAllById(ids);
        return documents.stream()
                .map(mapper::toDomain)
                .toList();
    }
}



