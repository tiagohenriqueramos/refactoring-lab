package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
import br.com.refactoringlab.infrastructure.db.mongodb.document.RastreioDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoRastreioSpringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RastreioQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RastreioQueueConsumer.class);
    private final MongoRastreioSpringRepository rastreioRepository;

    public RastreioQueueConsumer(MongoRastreioSpringRepository rastreioRepository) {
        this.rastreioRepository = rastreioRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RASTREIO)
    public void consumirRastreio(RastreioPedidoEvent event) {
        logger.info("Recebido evento de rastreio para o pedido {}: {}", event.pedidoId(), event.descricao());

        RastreioDocument document = new RastreioDocument(); document.setPedidoId(event.pedidoId());
        document.setStatusPedido(event.statusPedido()); document.setStatusOcorrencia(event.statusOcorrencia());
        document.setDescricao(event.descricao()); document.setUsuarioId(event.usuarioId());
        document.setDataHora(event.dataHora() != null ? event.dataHora() : LocalDateTime.now());

        rastreioRepository.save(document);
    }
}