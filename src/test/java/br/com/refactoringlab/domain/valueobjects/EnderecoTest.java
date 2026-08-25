package br.com.refactoringlab.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnderecoTest {

    @Test
    @DisplayName("Deve formatar endereço com complemento")
    void deveFormatarEnderecoComComplemento() {
        var endereco = new Endereco("Rua das Flores", "123", "Apto 11", "Centro", "Sao Paulo", "SP", "01000-000");

        assertThat(endereco.enderecoFormatado())
                .isEqualTo("Rua das Flores, 123 - Apto 11, Centro - Sao Paulo/SP, CEP: 01000-000");
    }

    @Test
    @DisplayName("Deve formatar endereço sem complemento")
    void deveFormatarEnderecoSemComplemento() {
        var endereco = new Endereco("Rua das Flores", "123", "   ", "Centro", "Sao Paulo", "SP", "01000-000");

        assertThat(endereco.enderecoFormatado())
                .isEqualTo("Rua das Flores, 123, Centro - Sao Paulo/SP, CEP: 01000-000");
    }

    @Test
    @DisplayName("Deve lançar exceção quando cidade for nula")
    void deveLancarExcecaoQuandoCidadeNula() {
        assertThatThrownBy(() -> new Endereco("Rua", "1", null, "Centro", null, "SP", "01000-000"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("A cidade é obrigatória");
    }

    @Test
    @DisplayName("Deve lançar exceção quando UF for nula")
    void deveLancarExcecaoQuandoUfNula() {
        assertThatThrownBy(() -> new Endereco("Rua", "1", null, "Centro", "Sao Paulo", null, "01000-000"))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("O estado (UF) é obrigatório");
    }

    @Test
    @DisplayName("Deve formatar endereço quando complemento for nulo")
    void deveFormatarEnderecoComComplementoNulo() {
        var endereco = new Endereco("Rua das Flores", "123", null, "Centro", "Sao Paulo", "SP", "01000-000");

        assertThat(endereco.enderecoFormatado())
                .isEqualTo("Rua das Flores, 123, Centro - Sao Paulo/SP, CEP: 01000-000");
    }
}

