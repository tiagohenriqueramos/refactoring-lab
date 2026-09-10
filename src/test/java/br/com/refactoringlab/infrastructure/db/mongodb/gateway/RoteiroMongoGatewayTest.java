package br.com.refactoringlab.infrastructure.db.mongodb.gateway;

import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.infrastructure.db.mongodb.document.RoteiroDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.RoteiroDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoRoteiroSpringRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoteiroMongoGatewayTest {

    @Mock
    private MongoRoteiroSpringRepository mongoRepository;

    @Mock
    private RoteiroDocumentMapper mapper;

    @InjectMocks
    private RoteiroMongoGateway gateway;

    @Test
    @DisplayName("Deve salvar roteiro usando mapper e repositorio")
    void deveSalvarRoteiroUsandoMapperERepositorio() {
        var roteiroEntrada = new Roteiro();
        var document = new RoteiroDocument();
        var documentSalvo = new RoteiroDocument();
        var roteiroRetorno = new Roteiro();

        when(mapper.toDocument(roteiroEntrada)).thenReturn(document);
        when(mongoRepository.save(document)).thenReturn(documentSalvo);
        when(mapper.toDomain(documentSalvo)).thenReturn(roteiroRetorno);

        var resultado = gateway.salvar(roteiroEntrada);

        assertThat(resultado).isSameAs(roteiroRetorno);
        verify(mapper).toDocument(roteiroEntrada);
        verify(mongoRepository).save(document);
        verify(mapper).toDomain(documentSalvo);
    }

    @Test
    @DisplayName("Deve buscar roteiro por codigo e mapear quando encontrado")
    void deveBuscarPorCodigoEMapearQuandoEncontrado() {
        var codigo = "ROT-123";
        var document = new RoteiroDocument();
        var roteiro = new Roteiro();

        when(mongoRepository.findByCodigoRoteiro(codigo)).thenReturn(Optional.of(document));
        when(mapper.toDomain(document)).thenReturn(roteiro);

        var resultado = gateway.buscarPorCodigo(codigo);

        assertThat(resultado).contains(roteiro);
        verify(mongoRepository).findByCodigoRoteiro(codigo);
        verify(mapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar por codigo inexistente")
    void deveRetornarVazioAoBuscarPorCodigoInexistente() {
        var codigo = "ROT-404";
        when(mongoRepository.findByCodigoRoteiro(codigo)).thenReturn(Optional.empty());

        var resultado = gateway.buscarPorCodigo(codigo);

        assertThat(resultado).isEmpty();
        verify(mongoRepository).findByCodigoRoteiro(codigo);
        verifyNoInteractions(mapper);
    }
}