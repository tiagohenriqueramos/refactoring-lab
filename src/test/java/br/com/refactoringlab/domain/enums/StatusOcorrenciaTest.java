package br.com.refactoringlab.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusOcorrenciaTest {

    @Test
    @DisplayName("Deve retornar descrições configuradas")
    void deveRetornarDescricoes() {
        assertThat(StatusOcorrencia.RECEBIDO_CD.getDescricao()).isEqualTo("Recebido no centro de distribuição");
        assertThat(StatusOcorrencia.EM_TRANSITO.getDescricao()).isEqualTo("Em trânsito para entrega");
        assertThat(StatusOcorrencia.ENTREGUE_PROPRIO_DESTINATARIO.getDescricao()).isEqualTo("Entrega realizada ao próprio destinatário");
        assertThat(StatusOcorrencia.EXTRAVIO.getDescricao()).isEqualTo("Extravio de carga");
        assertThat(StatusOcorrencia.AVARIA.getDescricao()).isEqualTo("Avaria no produto");
        assertThat(StatusOcorrencia.FURTADO.getDescricao()).isEqualTo("Carga furtada");
        assertThat(StatusOcorrencia.DEVOLVIDO_CD_ORIGEM.getDescricao()).isEqualTo("Devolvido ao centro de distribuição de origem");
        assertThat(StatusOcorrencia.INSUCESSO_ENDERECO_NAO_ENCONTRADO.getDescricao()).isEqualTo("Endereço não encontrado");
        assertThat(StatusOcorrencia.INSUCESSO_DESTINATARIO_AUSENTE.getDescricao()).isEqualTo("Cliente ausente na tentativa de entrega");
        assertThat(StatusOcorrencia.INSUCESSO_RECUSADO_DESTINATARIO.getDescricao()).isEqualTo("Entrega recusada pelo destinatário");
    }
}
