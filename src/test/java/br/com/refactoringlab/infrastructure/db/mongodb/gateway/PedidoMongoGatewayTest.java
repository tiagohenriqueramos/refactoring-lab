package br.com.refactoringlab.infrastructure.db.mongodb.gateway;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.PedidoDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoPedidoSpringRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoMongoGatewayTest {

    @Mock
    private MongoPedidoSpringRepository mongoRepository;

    @Mock
    private PedidoDocumentMapper mapper;

    @InjectMocks
    private PedidoMongoGateway repository;

    @Test
    @DisplayName("Deve salvar pedido usando mapper e repositorio")
    void deveSalvarPedidoUsandoMapperERepositorio() {
        var pedidoEntrada = new Pedido();
        pedidoEntrada.setId("PED-1");

        var document = new PedidoDocument();
        document.setId("PED-1");

        var documentSalvo = new PedidoDocument();
        documentSalvo.setId("PED-1");

        var pedidoRetorno = new Pedido();
        pedidoRetorno.setId("PED-1");

        when(mapper.toDocument(pedidoEntrada)).thenReturn(document);
        when(mongoRepository.save(document)).thenReturn(documentSalvo);
        when(mapper.toDomain(documentSalvo)).thenReturn(pedidoRetorno);

        var resultado = repository.salvar(pedidoEntrada);

        assertThat(resultado).isSameAs(pedidoRetorno);
        verify(mapper).toDocument(pedidoEntrada);
        verify(mongoRepository).save(document);
        verify(mapper).toDomain(documentSalvo);
    }

    @Test
    @DisplayName("Deve buscar pedido por id e mapear quando encontrado")
    void deveBuscarPorIdEMapearQuandoEncontrado() {
        var id = "PED-2";
        var document = new PedidoDocument();
        document.setId(id);

        var pedido = new Pedido();
        pedido.setId(id);

        when(mongoRepository.findById(id)).thenReturn(Optional.of(document));
        when(mapper.toDomain(document)).thenReturn(pedido);

        var resultado = repository.buscarPorId(id);

        assertThat(resultado).contains(pedido);
        verify(mongoRepository).findById(id);
        verify(mapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar por id inexistente")
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        var id = "PED-404";
        when(mongoRepository.findById(id)).thenReturn(Optional.empty());

        var resultado = repository.buscarPorId(id);

        assertThat(resultado).isEmpty();
        verify(mongoRepository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve buscar todos os pedidos usando findAll e mapear para dominio")
    void deveBuscarTodosOsPedidosUsandoFindAllEMapear() {
        var doc1 = new PedidoDocument();
        doc1.setId("PED-1");
        var doc2 = new PedidoDocument();
        doc2.setId("PED-2");

        var pedido1 = new Pedido();
        pedido1.setId("PED-1");
        var pedido2 = new Pedido();
        pedido2.setId("PED-2");

        when(mongoRepository.findAll()).thenReturn(List.of(doc1, doc2));
        when(mapper.toDomain(doc1)).thenReturn(pedido1);
        when(mapper.toDomain(doc2)).thenReturn(pedido2);

        var resultado = repository.buscarPorIds();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).containsExactly(pedido1, pedido2);
        verify(mongoRepository).findAll();
        verify(mapper).toDomain(doc1);
        verify(mapper).toDomain(doc2);
    }

    @Test
    @DisplayName("Deve buscar pedidos por codigo de roteiro e mapear para dominio")
    void deveBuscarPorCodigoRoteiroEMapear() {
        var codigoRoteiro = "ROT-2026-001";

        var doc1 = new PedidoDocument();
        doc1.setId("PED-1");
        doc1.setCodigoRoteiro(codigoRoteiro);

        var pedido1 = new Pedido();
        pedido1.setId("PED-1");

        when(mongoRepository.findByCodigoRoteiro(codigoRoteiro)).thenReturn(List.of(doc1));
        when(mapper.toDomain(doc1)).thenReturn(pedido1);

        var resultado = repository.buscarPorCodigoRoteiro(codigoRoteiro);

        assertThat(resultado).hasSize(1);
        assertThat(resultado).containsExactly(pedido1);
        verify(mongoRepository).findByCodigoRoteiro(codigoRoteiro);
        verify(mapper).toDomain(doc1);
    }
}



