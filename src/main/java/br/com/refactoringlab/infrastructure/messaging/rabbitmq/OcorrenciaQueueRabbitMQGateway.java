package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.application.gateways.OcorrenciaQueueGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OcorrenciaQueueRabbitMQGateway implements OcorrenciaQueueGateway {

    private final RabbitTemplate rabbitTemplate;

    public OcorrenciaQueueRabbitMQGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarOcorrencia(OcorrenciaPedidoEvent event) {
        rabbitTemplate.convertAndSend("pedidos.exchange", "pedido.ocorrencia.created", event);
    }
}