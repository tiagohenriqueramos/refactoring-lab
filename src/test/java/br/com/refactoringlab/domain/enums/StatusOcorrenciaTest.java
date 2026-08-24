
package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusOcorrenciaTest {

    @Test
    @DisplayName("Deve retornar descrições configuradas")
    void deveRetornarDescricoes() {
        assertThat(StatusOcorrencia.RECEBIDO_CD.getDescricao()).isEqualTo("Recebido no centro de distribuição");
        assertThat(StatusOcorrencia.EM_TRANSITO.getDescricao()).isEqualTo("Em trânsito");
        assertThat(StatusOcorrencia.ENTREGUE.getDescricao()).isEqualTo("Entrega realizada com sucesso");
        assertThat(StatusOcorrencia.EXTRAVIO.getDescricao()).isEqualTo("Extravio de carga");
        assertThat(StatusOcorrencia.AVARIA.getDescricao()).isEqualTo("Avaria no produto");
        assertThat(StatusOcorrencia.FURTADO.getDescricao()).isEqualTo("Carga furtada");
        assertThat(StatusOcorrencia.DEVOLUCAO.getDescricao()).isEqualTo("Pedido devolvido ao remetente");
        assertThat(StatusOcorrencia.INSUCESSO_ENDERECO_NAO_ENCONTRADO.getDescricao()).isEqualTo("Endereço não encontrado");
        assertThat(StatusOcorrencia.INSUCESSO_AUSENTE.getDescricao()).isEqualTo("Cliente ausente");
        assertThat(StatusOcorrencia.INSUCESSO_RECUSADO.getDescricao()).isEqualTo("Entrega recusada pelo destinatário");
    }
}

