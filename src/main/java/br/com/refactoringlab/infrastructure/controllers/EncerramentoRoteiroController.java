package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.usecases.EncerrarRoteiroUseCase;
import br.com.refactoringlab.infrastructure.controllers.dto.EncerrarPedidoRoteiroRequest;
import br.com.refactoringlab.infrastructure.controllers.dto.EncerramentoPedidoRoteiroResponse;
import br.com.refactoringlab.infrastructure.controllers.mapper.EncerramentoRoteiroControllerMapper;
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
    public ResponseEntity<List<EncerramentoPedidoRoteiroResponse>> encerrarPedidosRoteiro(
            @PathVariable String id,
            @RequestParam String usuarioId,
            @RequestBody List<EncerrarPedidoRoteiroRequest> request) {
        try {
            var input = EncerramentoRoteiroControllerMapper.toUseCaseInput(id, usuarioId, request);
            var output = encerrarRoteiroUseCase.executar(input);
            List<EncerramentoPedidoRoteiroResponse> response = EncerramentoRoteiroControllerMapper.toResponseList(output);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("Erro ao encerrar pedidos do roteiro {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}