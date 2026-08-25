package br.com.refactoringlab.infrastructure.db.mongodb.adapter;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.ports.PedidoRepositoryPort;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.PedidoDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoPedidoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final MongoPedidoRepository mongoRepository;
    private final PedidoDocumentMapper mapper;

    public PedidoRepositoryAdapter(MongoPedidoRepository mongoRepository, PedidoDocumentMapper mapper) {
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
}