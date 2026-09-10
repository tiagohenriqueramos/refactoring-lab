package br.com.refactoringlab.application.gateways;

import br.com.refactoringlab.domain.entities.Roteiro;

import java.util.Optional;

public interface RoteiroGateway {
    Roteiro salvar(Roteiro roteiro);
    Optional<Roteiro> buscarPorCodigo(String codigoRoteiro);
}
