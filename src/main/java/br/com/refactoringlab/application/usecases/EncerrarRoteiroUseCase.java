package br.com.refactoringlab.application.usecases;

import br.com.refactoringlab.application.dto.*;
import br.com.refactoringlab.application.gateways.*;
import br.com.refactoringlab.domain.entities.Pedido;

import java.util.*;

public class EncerrarRoteiroUseCase {

    private final PedidoGateway pedidoGateway;
    private final EncerrarRoteiroGateway encerrarRoteiroGateway;
    private final RastreioInternoGateway rastreioInternoGateway;

    public EncerrarRoteiroUseCase(PedidoGateway pedidoGateway,
                                  EncerrarRoteiroGateway encerrarRoteiroGateway,
                                  RastreioInternoGateway rastreioInternoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.encerrarRoteiroGateway = encerrarRoteiroGateway;
        this.rastreioInternoGateway = rastreioInternoGateway;
    }

    public List<EncerramentoPedidoOutput> executar(EncerrarRoteiroInput input) {
        List<EncerramentoPedidoOutput> erros = validarEntrada(input.itens());
        if (!erros.isEmpty()) {
            return erros;
        }

        // Busca entidades via Gateway de Pedido
        List<String> ids = input.itens().stream().map(EncerrarPedidoItemInput::pedidoEntregaId).toList();
        List<Pedido> pedidos = pedidoGateway.buscarPorIds(ids);

        // Processa as alterações de negócio e gera saídas
        List<EncerramentoPedidoOutput> resultados = processarPedidos(pedidos, input);

        // Dispara notificação de alteração de roteiro
        notificarFilaAtualizacao(resultados, input.roteiroId());

        return resultados;
    }

    private List<EncerramentoPedidoOutput> validarEntrada(List<EncerrarPedidoItemInput> itens) {
        List<EncerramentoPedidoOutput> erros = new ArrayList<>();
        for (int i = 0; i < itens.size(); i++) {
            EncerrarPedidoItemInput item = itens.get(i);
            if (item.pedidoEntregaId() == null || item.pedidoEntregaId().isBlank()) {
                erros.add(new EncerramentoPedidoOutput(null, true, "Item " + i + ": pedidoEntregaId obrigatório", null, null, null));
            } else if (item.novoStatus() == null) {
                erros.add(new EncerramentoPedidoOutput(item.pedidoEntregaId(), true, "Item " + i + ": statusTratativa obrigatório", null, null, null));
            }
        }
        return erros;
    }

    private List<EncerramentoPedidoOutput> processarPedidos(List<Pedido> pedidos, EncerrarRoteiroInput input) {
        // Regras do fluxo de encerramento por item
        return new ArrayList<>();
    }

    private void notificarFilaAtualizacao(List<EncerramentoPedidoOutput> resultados, String roteiroId) {
        Set<String> pedidosParaRemover = new HashSet<>();
        Set<String> etapasParaAdicionar = new HashSet<>();

        for (EncerramentoPedidoOutput res : resultados) {
            if (!res.possuiErro()) {
                if (res.pedidoRemoverRoteiroId() != null) pedidosParaRemover.add(res.pedidoRemoverRoteiroId());
                if (res.pedidosRemoverRoteiroId() != null) pedidosParaRemover.addAll(res.pedidosRemoverRoteiroId());
                if (res.etapaAdicionarId() != null && !res.etapaAdicionarId().isBlank()) etapasParaAdicionar.add(res.etapaAdicionarId());
            }
        }

        if (!pedidosParaRemover.isEmpty() || !etapasParaAdicionar.isEmpty()) {
            AlterarPedidosRoteiroInput payload = new AlterarPedidosRoteiroInput(
                    roteiroId,
                    pedidosParaRemover,
                    etapasParaAdicionar,
                    "ALTERACAO_MANUAL_CANCELAMENTO"
            );
            encerrarRoteiroGateway.enviarParaFilaAtualizacao(payload);
        }
    }
}