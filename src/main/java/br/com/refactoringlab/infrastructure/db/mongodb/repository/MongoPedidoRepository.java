package br.com.refactoringlab.infrastructure.db.mongodb.repository;

import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPedidoRepository extends MongoRepository<PedidoDocument, String> {
}
