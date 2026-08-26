package br.com.refactoringlab.application.gateways;

import br.com.refactoringlab.application.dto.AlterarPedidosRoteiroInput;

public interface EncerrarRoteiroGateway {
    void enviarParaFilaAtualizacao(AlterarPedidosRoteiroInput input);
}