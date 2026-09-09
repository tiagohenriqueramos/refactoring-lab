package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OcorrenciaQueueRabbitMQGatewayTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OcorrenciaQueueRabbitMQGateway gateway;

    @Test
    @DisplayName("Deve publicar ocorrência na exchange e routing configurados")
    void devePublicarOcorrenciaNaExchangeERoutingConfigurados() {
        var evento = new OcorrenciaPedidoEvent(
                "PED-123",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_DESTINATARIO_AUSENTE,
                "Destinatario ausente",
                "user-1",
                LocalDateTime.now()
        );

        gateway.publicarOcorrencia(evento);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE_PEDIDOS,
                RabbitMQConfig.ROUTING_OCORRENCIA,
                evento
        );
    }
}

