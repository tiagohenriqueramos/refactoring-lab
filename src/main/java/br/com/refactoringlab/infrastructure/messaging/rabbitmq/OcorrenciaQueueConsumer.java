package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.infrastructure.db.mongodb.document.OcorrenciaDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoOcorrenciaSpringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OcorrenciaQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OcorrenciaQueueConsumer.class);
    private final MongoOcorrenciaSpringRepository ocorrenciaRepository;

    public OcorrenciaQueueConsumer(MongoOcorrenciaSpringRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_OCORRENCIA)
    public void consumirOcorrencia(OcorrenciaPedidoEvent event) {
        try {
            logger.info("Consumindo e salvando ocorrência de auditoria para o pedido {}", event.pedidoId());

            OcorrenciaDocument document = new OcorrenciaDocument(event.pedidoId(), event.statusPedido(), event.statusOcorrencia(), event.motivo(), event.usuarioId(), event.dataOcorrencia());

            ocorrenciaRepository.save(document);
            logger.info("Ocorrência salva com sucesso no Mongo para o pedido {}", event.pedidoId());
        } catch (Exception ex) {
            logger.error("Falha ao persistir ocorrência do pedido {}: {}", event.pedidoId(), ex.getMessage(), ex);
            throw new AmqpRejectAndDontRequeueException("Falha ao persistir ocorrência", ex);
        }
    }
}