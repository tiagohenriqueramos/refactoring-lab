package br.com.refactoringlab.infrastructure.db.mongodb.gateway;

import br.com.refactoringlab.application.gateways.RoteiroGateway;
import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.infrastructure.db.mongodb.document.RoteiroDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.RoteiroDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoRoteiroSpringRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoteiroMongoGateway implements RoteiroGateway {

    private final MongoRoteiroSpringRepository mongoRepository;
    private final RoteiroDocumentMapper mapper;

    public RoteiroMongoGateway(MongoRoteiroSpringRepository mongoRepository, RoteiroDocumentMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Roteiro salvar(Roteiro roteiro) {
        RoteiroDocument document = mapper.toDocument(roteiro);
        RoteiroDocument saved = mongoRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Roteiro> buscarPorCodigo(String codigoRoteiro) {
        return mongoRepository.findByCodigoRoteiro(codigoRoteiro).map(mapper::toDomain);
    }
}