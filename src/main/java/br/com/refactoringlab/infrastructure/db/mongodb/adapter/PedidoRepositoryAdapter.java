package br.com.refactoringlab.infrastructure.db.mongodb.adapter;

import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.ports.PedidoRepositoryPort;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoPedidoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final MongoPedidoRepository mongoRepository;

    public PedidoRepositoryAdapter(MongoPedidoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        // Mapeia da Entidade de Domínio para o Documento Mongo e salva
        return pedido;
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return Optional.empty();
    }
}