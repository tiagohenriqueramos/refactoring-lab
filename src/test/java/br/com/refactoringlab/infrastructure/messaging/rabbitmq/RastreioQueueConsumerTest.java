package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import br.com.refactoringlab.application.dto.RastreioPedidoEvent;
import br.com.refactoringlab.domain.enums.StatusOcorrencia;
import br.com.refactoringlab.domain.enums.StatusPedido;
import br.com.refactoringlab.infrastructure.db.mongodb.document.RastreioDocument;
import br.com.refactoringlab.infrastructure.db.mongodb.repository.MongoRastreioSpringRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RastreioQueueConsumerTest {

    @Mock
    private MongoRastreioSpringRepository rastreioRepository;

    @InjectMocks
    private RastreioQueueConsumer consumer;

    @Test
    @DisplayName("Deve mapear e salvar evento de rastreio")
    void deveMapearESalvarEventoDeRastreio() {
        var data = LocalDateTime.of(2026, 9, 8, 16, 0);
        var event = new RastreioPedidoEvent(
                "PED-10",
                StatusPedido.INSUCESSO,
                StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE,
                "Cliente ausente",
                "user-10",
                data
        );

        consumer.consumirRastreio(event);

        var captor = ArgumentCaptor.forClass(RastreioDocument.class);
        verify(rastreioRepository).save(captor.capture());

        var saved = captor.getValue();
        assertThat(saved.getPedidoId()).isEqualTo("PED-10");
        assertThat(saved.getStatusPedido()).isEqualTo(StatusPedido.INSUCESSO);
        assertThat(saved.getStatusOcorrencia()).isEqualTo(StatusOcorrencia.INSUCESSO_COLETA_REVERSA_AUSENTE);
        assertThat(saved.getDescricao()).isEqualTo("Cliente ausente");
        assertThat(saved.getUsuarioId()).isEqualTo("user-10");
        assertThat(saved.getDataHora()).isEqualTo(data);
    }

    @Test
    @DisplayName("Deve preencher data atual quando evento vier sem data")
    void devePreencherDataAtualQuandoEventoVierSemData() {
        var before = LocalDateTime.now().minusSeconds(1);
        var event = new RastreioPedidoEvent(
                "PED-11",
                StatusPedido.RECEBIDO,
                StatusOcorrencia.RECEBIDO_CD,
                "Recebido no CD",
                "user-11",
                null
        );

        consumer.consumirRastreio(event);

        var captor = ArgumentCaptor.forClass(RastreioDocument.class);
        verify(rastreioRepository).save(captor.capture());

        assertThat(captor.getValue().getDataHora()).isNotNull();
        assertThat(captor.getValue().getDataHora()).isAfter(before);
    }
}

