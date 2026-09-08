package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
import br.com.refactoringlab.application.gateways.RastreioQueueGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RastreioQueueRabbitMQGateway implements RastreioQueueGateway {

    private final RabbitTemplate rabbitTemplate;

    public RastreioQueueRabbitMQGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarRastreio(RastreioPedidoEvent event) {
        // Envia para a exchange/fila de rastreio unificada
        rabbitTemplate.convertAndSend("pedidos.exchange", "pedido.rastreio.created", event);
    }
}