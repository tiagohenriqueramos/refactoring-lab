package br.com.refactoringlab.infrastructure.controllers.dto;

import br.com.refactoringlab.domain.entities.Roteiro;
import br.com.refactoringlab.domain.enums.StatusRoteiro;

public record RoteiroResponse (String codigoRoteiro, StatusRoteiro statusRoteiro){

    public static RoteiroResponse from(Roteiro roteiro) {
        return  new RoteiroResponse(roteiro.getCodigoRoteiro(), roteiro.getStatusRoteiro());
    }
}
