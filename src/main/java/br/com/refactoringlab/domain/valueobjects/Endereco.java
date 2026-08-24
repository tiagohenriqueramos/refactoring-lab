package br.com.refactoringlab.domain.valueobjects;

import java.util.Objects;

public record Endereco(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
) {
    public Endereco {
        Objects.requireNonNull(cidade, "A cidade é obrigatória");
        Objects.requireNonNull(uf, "O estado (UF) é obrigatório");
    }

    public String enderecoFormatado() {
        var comp = (complemento != null && !complemento.isBlank()) ? " - " + complemento : "";
        return String.format("%s, %s%s, %s - %s/%s, CEP: %s",
                logradouro, numero, comp, bairro, cidade, uf, cep);
    }
}