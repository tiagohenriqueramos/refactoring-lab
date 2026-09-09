package br.com.refactoringlab.infrastructure.db.mongodb.repository;

import br.com.refactoringlab.infrastructure.db.mongodb.document.OcorrenciaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoOcorrenciaSpringRepository extends MongoRepository<OcorrenciaDocument, String> {
    List<OcorrenciaDocument> findByPedidoId(String pedidoId);
}