package br.com.refactoringlab.infrastructure.db.mongodb.repository;

import br.com.refactoringlab.infrastructure.db.mongodb.document.RoteiroDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoRoteiroSpringRepository extends MongoRepository<RoteiroDocument, String> {
    Optional<RoteiroDocument> findByCodigoRoteiro(String codigoRoteiro);
}
