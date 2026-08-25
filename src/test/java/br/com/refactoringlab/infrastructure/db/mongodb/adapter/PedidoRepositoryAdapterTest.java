package br.com.refactoringlab.infrastructure.db.mongodb.adapter;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.PedidoDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.mapper.PedidoDocumentMapper;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoPedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoRepositoryAdapterTest {

    @Mock
    private MongoPedidoRepository mongoRepository;

    @Mock
    private PedidoDocumentMapper mapper;

    @InjectMocks
    private PedidoRepositoryAdapter adapter;

    @Test
    @DisplayName("Deve salvar pedido usando mapper e repositório")
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

        var resultado = adapter.salvar(pedidoEntrada);

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

        var resultado = adapter.buscarPorId(id);

        assertThat(resultado).contains(pedido);
        verify(mongoRepository).findById(id);
        verify(mapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar por id inexistente")
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        var id = "PED-404";
        when(mongoRepository.findById(id)).thenReturn(Optional.empty());

        var resultado = adapter.buscarPorId(id);

        assertThat(resultado).isEmpty();
        verify(mongoRepository).findById(id);
        verifyNoInteractions(mapper);
    }
}

