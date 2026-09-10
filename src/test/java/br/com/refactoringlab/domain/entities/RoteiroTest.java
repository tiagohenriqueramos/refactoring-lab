package br.com.refactoringlab.domain.entities;

import br.com.refactoringlab.domain.enums.StatusRoteiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoteiroTest {

    @Test
    @DisplayName("Deve inicializar com valores padroes usando o construtor padrao")
    void deveInicializarComValoresPadroesNoConstrutorPadrao() {
        var roteiro = new Roteiro();

        assertThat(roteiro.getDataCriacao()).isNotNull();
        assertThat(roteiro.getStatusRoteiro()).isEqualTo(StatusRoteiro.EM_ANDAMENTO);
        assertThat(roteiro.getPedidosIds()).isEmpty();
    }

    @Test
    @DisplayName("Deve inicializar com valores informados no construtor parametrizado")
    void deveInicializarComValoresParametrizados() {
        var pedidos = List.of("PED-01", "PED-02");
        var roteiro = new Roteiro("ROT-001", "MOT-12", "ABC-123", pedidos);

        assertThat(roteiro.getCodigoRoteiro()).isEqualTo("ROT-001");
        assertThat(roteiro.getMotoristaId()).isEqualTo("MOT-12");
        assertThat(roteiro.getVeiculoPlaca()).isEqualTo("ABC-123");
        assertThat(roteiro.getPedidosIds()).containsExactly("PED-01", "PED-02");
    }

    @Test
    @DisplayName("Deve setar e obter campos simples")
    void deveSetarEObterCamposSimples() {
        var roteiro = new Roteiro();
        var data = LocalDateTime.of(2026, 9, 10, 15, 0);

        roteiro.setId("ID123");
        roteiro.setCodigoRoteiro("ROT-02");
        roteiro.setMotoristaId("MOT-99");
        roteiro.setVeiculoPlaca("XYZ-9999");
        roteiro.setStatusRoteiro(StatusRoteiro.FINALIZADO);
        roteiro.setDataCriacao(data);

        assertThat(roteiro.getId()).isEqualTo("ID123");
        assertThat(roteiro.getCodigoRoteiro()).isEqualTo("ROT-02");
        assertThat(roteiro.getMotoristaId()).isEqualTo("MOT-99");
        assertThat(roteiro.getVeiculoPlaca()).isEqualTo("XYZ-9999");
        assertThat(roteiro.getStatusRoteiro()).isEqualTo(StatusRoteiro.FINALIZADO);
        assertThat(roteiro.getDataCriacao()).isEqualTo(data);
    }

    @Test
    @DisplayName("Deve lidar corretamente com pedidos nulos ou vazios no getter e setter")
    void deveLidarComPedidosNulosOuVaziosNoGetterESetter() {
        var roteiro = new Roteiro();

        roteiro.setPedidosIds(null);
        assertThat(roteiro.getPedidosIds()).isEmpty();

        // Se a lista interna for nula diretamente (ex: construído por vias de deserialização)
        var roteiroSemLista = new Roteiro("R-1", "M-1", "V-1", null);
        assertThat(roteiroSemLista.getPedidosIds()).isEmpty();
    }

    @Test
    @DisplayName("Deve adicionar pedido valido")
    void deveAdicionarPedidoValido() {
        var roteiro = new Roteiro();
        roteiro.adicionarPedido("PED-123");

        assertThat(roteiro.getPedidosIds()).containsExactly("PED-123");
    }

    @Test
    @DisplayName("Deve ignorar ao adicionar pedido nulo, branco ou duplicado")
    void deveIgnorarAoAdicionarPedidoValoresInvalidos() {
        var roteiro = new Roteiro();

        roteiro.adicionarPedido(null);
        roteiro.adicionarPedido("");
        roteiro.adicionarPedido("   ");
        assertThat(roteiro.getPedidosIds()).isEmpty();

        roteiro.adicionarPedido("PED-1");
        roteiro.adicionarPedido("PED-1"); // Duplicado
        assertThat(roteiro.getPedidosIds()).containsExactly("PED-1");
    }

    @Test
    @DisplayName("Deve remover pedido do roteiro")
    void deveRemoverPedidoDoRoteiro() {
        var roteiro = new Roteiro();
        roteiro.adicionarPedido("PED-1");
        roteiro.adicionarPedido("PED-2");

        roteiro.removerPedido("PED-1");

        assertThat(roteiro.getPedidosIds()).containsExactly("PED-2");
    }

    @Test
    @DisplayName("Deve finalizar roteiro ativo com sucesso")
    void deveFinalizarRoteiroAtivoComSucesso() {
        var roteiro = new Roteiro();
        roteiro.finalizarRoteiro();

        assertThat(roteiro.getStatusRoteiro()).isEqualTo(StatusRoteiro.FINALIZADO);
    }

    @Test
    @DisplayName("Deve lancar exceção ao finalizar roteiro cancelado")
    void deveLancarExcecaoAoFinalizarRoteiroCancelado() {
        var roteiro = new Roteiro();
        roteiro.cancelarRoteiro();

        assertThatThrownBy(roteiro::finalizarRoteiro)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Roteiros cancelados não podem ser finalizados.");
    }

    @Test
    @DisplayName("Deve cancelar roteiro ativo com sucesso")
    void deveCancelarRoteiroAtivoComSucesso() {
        var roteiro = new Roteiro();
        roteiro.cancelarRoteiro();

        assertThat(roteiro.getStatusRoteiro()).isEqualTo(StatusRoteiro.CANCELADO);
    }

    @Test
    @DisplayName("Deve lancar exceção ao cancelar roteiro já finalizado")
    void deveLancarExcecaoAoCancelarRoteiroFinalizado() {
        var roteiro = new Roteiro();
        roteiro.finalizarRoteiro();

        assertThatThrownBy(roteiro::cancelarRoteiro)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Roteiros já finalizados não podem ser cancelados.");
    }

    @Test
    @DisplayName("Deve lancar exceção ao tentar modificar roteiro finalizado")
    void deveLancarExcecaoAoModificarRoteiroFinalizado() {
        var roteiro = new Roteiro();
        roteiro.setCodigoRoteiro("ROT-ABC");
        roteiro.finalizarRoteiro();

        assertThatThrownBy(() -> roteiro.adicionarPedido("PED-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O roteiro ROT-ABC já está finalizado ou cancelado.");

        assertThatThrownBy(() -> roteiro.removerPedido("PED-2"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O roteiro ROT-ABC já está finalizado ou cancelado.");
    }

    @Test
    @DisplayName("Deve lancar exceção ao tentar modificar roteiro cancelado")
    void deveLancarExcecaoAoModificarRoteiroCancelado() {
        var roteiro = new Roteiro();
        roteiro.setCodigoRoteiro("ROT-XYZ");
        roteiro.cancelarRoteiro();

        assertThatThrownBy(() -> roteiro.adicionarPedido("PED-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O roteiro ROT-XYZ já está finalizado ou cancelado.");

        assertThatThrownBy(() -> roteiro.removerPedido("PED-2"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O roteiro ROT-XYZ já está finalizado ou cancelado.");
    }

    @Test
    @DisplayName("Testes de equals e hashCode")
    void testEqualsAndHashCode() {
        var r1 = new Roteiro();
        r1.setId("1");
        r1.setCodigoRoteiro("ROT-A");

        var r2 = new Roteiro();
        r2.setId("1");
        r2.setCodigoRoteiro("ROT-A");

        var r3 = new Roteiro();
        r3.setId("1");
        r3.setCodigoRoteiro("ROT-B");

        var r4 = new Roteiro();
        r4.setId("2");
        r4.setCodigoRoteiro("ROT-A");

        assertThat(r1).isEqualTo(r1);
        assertThat(r1).isEqualTo(r2);
        assertThat(r1).isNotEqualTo(null);
        assertThat(r1).isNotEqualTo("String de teste");
        assertThat(r1).isNotEqualTo(r3);
        assertThat(r1).isNotEqualTo(r4);

        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
        assertThat(r1.hashCode()).isNotEqualTo(r3.hashCode());
    }
}