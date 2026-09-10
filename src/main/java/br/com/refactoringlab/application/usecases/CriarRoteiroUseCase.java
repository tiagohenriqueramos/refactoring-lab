package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.CriarRoteiroInput;
import br.com.refactoringlab.application.gateways.RoteiroGateway;
import br.com.refactoringlab.domain.entities.Roteiro;

public class CriarRoteiroUseCase {

    private final RoteiroGateway roteiroGateway;

    public CriarRoteiroUseCase(RoteiroGateway roteiroGateway) {
        this.roteiroGateway = roteiroGateway;
    }

    public Roteiro executar(CriarRoteiroInput input) {
        Roteiro novoRoteiro = new Roteiro(
                input.codigoRoteiro(),
                input.motoristaId(),
                input.veiculoPlaca(),
                input.pedidosIds()
        );

        return roteiroGateway.salvar(novoRoteiro);
    }
}