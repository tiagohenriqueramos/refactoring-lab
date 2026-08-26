package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.gateways.RastreioInternoGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RastreioInternoRabbitMQGateway implements RastreioInternoGateway {

    private final RabbitTemplate rabbitTemplate;

    public RastreioInternoRabbitMQGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void registrarObservacao(String usuarioId, String pedidoId, String descricao, String evento) {
        // Lógica de montagem do payload e publicação via RabbitTemplate
    }
}