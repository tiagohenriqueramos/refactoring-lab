package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.OcorrenciaPedidoEvent;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.OcorrenciaDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoOcorrenciaSpringRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OcorrenciaQueueConsumerTest {

    @Mock
    private MongoOcorrenciaSpringRepository ocorrenciaRepository;

    @InjectMocks
    private OcorrenciaQueueConsumer consumer;

    @Test
    @DisplayName("Deve salvar ocorrência no Mongo ao consumir evento")
    void deveSalvarOcorrenciaNoMongoAoConsumirEvento() {
        var data = LocalDateTime.now();
        var evento = new OcorrenciaPedidoEvent(
                "PED-123",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_DESTINATARIO_AUSENTE,
                "Destinatario ausente",
                "user-1",
                data
        );

        consumer.consumirOcorrencia(evento);

        ArgumentCaptor<OcorrenciaDocument> captor = ArgumentCaptor.forClass(OcorrenciaDocument.class);
        verify(ocorrenciaRepository).save(captor.capture());

        var salvo = captor.getValue();
        assertThat(salvo.getPedidoId()).isEqualTo("PED-123");
        assertThat(salvo.getStatusPedido()).isEqualTo(StatusPedido.INSUCESSO);
        assertThat(salvo.getStatusOcorrencia()).isEqualTo(StatusOcorrencia.INSUCESSO_DESTINATARIO_AUSENTE);
        assertThat(salvo.getMotivo()).isEqualTo("Destinatario ausente");
        assertThat(salvo.getUsuarioId()).isEqualTo("user-1");
        assertThat(salvo.getDataOcorrencia()).isEqualTo(data);
    }

    @Test
    @DisplayName("Deve lançar erro não-reenfileirável quando falhar ao salvar ocorrência")
    void deveLancarErroNaoReenfileiravelQuandoFalharAoSalvarOcorrencia() {
        var evento = new OcorrenciaPedidoEvent(
                "PED-500",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_DESTINATARIO_AUSENTE,
                "Falha de banco",
                "user-2",
                LocalDateTime.now()
        );

        when(ocorrenciaRepository.save(any(OcorrenciaDocument.class)))
                .thenThrow(new RuntimeException("mongo indisponivel"));

        assertThatThrownBy(() -> consumer.consumirOcorrencia(evento))
                .isInstanceOf(AmqpRejectAndDontRequeueException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }
}

