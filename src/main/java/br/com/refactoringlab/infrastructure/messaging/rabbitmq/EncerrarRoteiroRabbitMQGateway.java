package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.AlterarPedidosRoteiroInput;
import br.com.refactoringlab.application.gateways.EncerrarRoteiroGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EncerrarRoteiroRabbitMQGateway implements EncerrarRoteiroGateway {

    private static final String EXCHANGE = "alterarPedidosRoteiro.direct.exchange";
    private static final String ROUTING_KEY = "alterarPedidosRoteiro";

    private final RabbitTemplate rabbitTemplate;

    public EncerrarRoteiroRabbitMQGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enviarParaFilaAtualizacao(AlterarPedidosRoteiroInput input) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, input);
    }
}