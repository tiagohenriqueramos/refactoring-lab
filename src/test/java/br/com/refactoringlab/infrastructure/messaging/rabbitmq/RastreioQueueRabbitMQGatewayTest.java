package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
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
class RastreioQueueRabbitMQGatewayTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RastreioQueueRabbitMQGateway gateway;

    @Test
    @DisplayName("Deve publicar rastreio na exchange e routing esperados")
    void devePublicarRastreioNaExchangeERoutingEsperados() {
        var evento = new RastreioPedidoEvent(
                "PED-321",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Cliente ausente",
                "user-7",
                LocalDateTime.now()
        );

        gateway.publicarRastreio(evento);

        verify(rabbitTemplate).convertAndSend("pedidos.exchange", "pedido.rastreio.created", evento);
    }
}

