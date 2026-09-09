package br.com.refactoringlab.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    @DisplayName("Deve setar e obter campos do usuario")
    void deveSetarEObterCamposDoUsuario() {
        var usuario = new Usuario();

        usuario.setId("U-1");
        usuario.setNome("Maria");
        usuario.setCpf("12345678900");

        assertThat(usuario.getId()).isEqualTo("U-1");
        assertThat(usuario.getNome()).isEqualTo("Maria");
        assertThat(usuario.getCpf()).isEqualTo("12345678900");
    }
}

