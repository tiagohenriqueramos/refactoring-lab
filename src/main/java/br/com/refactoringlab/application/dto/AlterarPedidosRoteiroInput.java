package br.com.refactoringlab.application.dto;

import java.util.Set;

public record AlterarPedidosRoteiroInput(
        String roteiroGuid,
        Set<String> removerPedidoGuidList,
        Set<String> adicionarEtapaGuidList,
        String origemAlteracao
) {}