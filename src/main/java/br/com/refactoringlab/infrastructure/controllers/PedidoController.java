package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.CriarPedidoInput;
import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.BuscarPedidosPorIdsUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.infrastructure.controllers.dto.PedidoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;
    private final BuscarPedidosPorIdsUseCase buscarPedidosPorIdsUseCase;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase, BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase, BuscarPedidosPorIdsUseCase buscarPedidosPorIdsUseCase) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.buscarPedidoPorIdUseCase = buscarPedidoPorIdUseCase;
        this.buscarPedidosPorIdsUseCase = buscarPedidosPorIdsUseCase;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody CriarPedidoInput input) {
        var pedidoCriado = criarPedidoUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponse.from(pedidoCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable String id) {
        return buscarPedidoPorIdUseCase.executar(id).map(PedidoResponse::from).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> buscarTodos() {
        return ResponseEntity.ok(buscarPedidosPorIdsUseCase.executar().stream().map(PedidoResponse::from).toList());
    }

}

