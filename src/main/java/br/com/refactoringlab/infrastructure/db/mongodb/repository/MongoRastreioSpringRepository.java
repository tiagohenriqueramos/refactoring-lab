package br.com.refactoringlab.infrastructure.db.mongodb.repository;

import br.com.refactoringlab.infrastructure.db.mongodb.document.RastreioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRastreioSpringRepository extends MongoRepository<RastreioDocument, String> {
}

