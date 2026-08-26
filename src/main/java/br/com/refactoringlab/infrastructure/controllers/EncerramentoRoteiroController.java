package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.EncerrarPedidoItemInput;
import br.com.refactoringlab.application.dto.EncerrarRoteiroInput;
import br.com.refactoringlab.application.dto.EncerramentoPedidoOutput;
import br.com.refactoringlab.application.usecases.EncerrarRoteiroUseCase;
import br.com.refactoringlab.infrastructure.controllers.dto.EncerrarPedidoRoteiroRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encerramento-roteiro")
public class EncerramentoRoteiroController {

    private static final Logger logger = LoggerFactory.getLogger(EncerramentoRoteiroController.class);
    private final EncerrarRoteiroUseCase encerrarRoteiroUseCase;

    public EncerramentoRoteiroController(EncerrarRoteiroUseCase encerrarRoteiroUseCase) {
        this.encerrarRoteiroUseCase = encerrarRoteiroUseCase;
    }

    @PostMapping("/{id}")
    public ResponseEntity<List<EncerramentoPedidoOutput>> encerrarPedidosRoteiro(
            @PathVariable String id,
            @RequestParam String usuarioId,
            @RequestBody List<EncerrarPedidoRoteiroRequest> request) {
        try {
            List<EncerrarPedidoItemInput> itens = request.stream()
                    .map(item -> new EncerrarPedidoItemInput(
                            item.pedidoEntregaId(),
                            item.novoStatus(),
                            item.motivoInsucesso()
                    ))
                    .toList();

            EncerrarRoteiroInput input = new EncerrarRoteiroInput(id, usuarioId, itens);

            List<EncerramentoPedidoOutput> response = encerrarRoteiroUseCase.executar(input);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("Erro ao encerrar pedidos do roteiro {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}