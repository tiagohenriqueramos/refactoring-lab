package br.com.refactoringlab.infrastructure.controllers.mapper;

import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.dto.EncerrarPedidoItemInput;
import br.com.refactoringlab.application.dto.EncerrarRoteiroInput;
import br.com.refactoringlab.infrastructure.controllers.dto.EncerrarPedidoRoteiroRequest;
import br.com.refactoringlab.infrastructure.controllers.dto.EncerramentoPedidoRoteiroResponse;

import java.util.List;

public final class EncerramentoRoteiroControllerMapper {

    private EncerramentoRoteiroControllerMapper() {
    }

    public static EncerrarRoteiroInput toUseCaseInput(
            String roteiroId,
            String usuarioId,
            List<EncerrarPedidoRoteiroRequest> request
    ) {
        List<EncerrarPedidoItemInput> itens = request.stream()
                .map(item -> new EncerrarPedidoItemInput(
                        item.pedidoEntregaId(),
                        item.novoStatus(),
                        item.motivoInsucesso()
                ))
                .toList();

        return new EncerrarRoteiroInput(roteiroId, usuarioId, itens);
    }

    public static List<EncerramentoPedidoRoteiroResponse> toResponseList(List<EncerramentoPedidoOutput> output) {
        return output.stream()
                .map(item -> new EncerramentoPedidoRoteiroResponse(
                        item.pedidoId(),
                        item.possuiErro(),
                        item.mensagem(),
                        item.pedidoRemoverRoteiroId(),
                        item.pedidosRemoverRoteiroId(),
                        item.etapaAdicionarId()
                ))
                .toList();
    }
}

