package br.com.refactoringlab.infrastructure.db.mongodb.repository;

import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPedidoSpringRepository extends MongoRepository<PedidoDocument, String> {
}


