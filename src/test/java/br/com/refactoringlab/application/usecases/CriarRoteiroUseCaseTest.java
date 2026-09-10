package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.CriarRoteiroInput;
import br.com.refactoringlab.application.gateways.RoteiroGateway;
import br.com.refactoringlab.domain.entities.Roteiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarRoteiroUseCaseTest {

    @Mock
    private RoteiroGateway roteiroGateway;

    @InjectMocks
    private CriarRoteiroUseCase useCase;

    @Test
    @DisplayName("Deve criar roteiro com dados do input e salvar no repositorio")
    void deveCriarRoteiroComDadosDoInputESalvarNoRepositorio() {
        // Arrange
        var pedidosIds = List.of("PED-001", "PED-002", "PED-003");
        var input = new CriarRoteiroInput(
                "ROT-2026-01",
                "MOT-123",
                "ABC-1234",
                pedidosIds
        );

        var roteiroSalvo = new Roteiro();
        roteiroSalvo.setId("ROT-ID-01");
        when(roteiroGateway.salvar(any(Roteiro.class))).thenReturn(roteiroSalvo);

        // Act
        var resultado = useCase.executar(input);

        // Assert
        assertThat(resultado).isSameAs(roteiroSalvo);

        var roteiroCaptor = ArgumentCaptor.forClass(Roteiro.class);
        verify(roteiroGateway).salvar(roteiroCaptor.capture());
        var roteiroCapturado = roteiroCaptor.getValue();

        assertThat(roteiroCapturado.getCodigoRoteiro()).isEqualTo("ROT-2026-01");
        assertThat(roteiroCapturado.getMotoristaId()).isEqualTo("MOT-123");
        assertThat(roteiroCapturado.getVeiculoPlaca()).isEqualTo("ABC-1234");
        assertThat(roteiroCapturado.getPedidosIds()).containsExactly("PED-001", "PED-002", "PED-003");
    }
}
