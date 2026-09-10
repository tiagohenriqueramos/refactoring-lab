package br.com.refactoringlab.application.dto;

import java.util.List;

public record CriarRoteiroInput(
        String codigoRoteiro,
        String motoristaId,
        String veiculoPlaca,
        List<String> pedidosIds
) {}