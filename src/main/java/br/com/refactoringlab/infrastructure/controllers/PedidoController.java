package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.CriarPedidoInput;
import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.infrastructure.controllers.dto.PedidoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase,
                            BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.buscarPedidoPorIdUseCase = buscarPedidoPorIdUseCase;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody CriarPedidoInput input) {
        var pedidoCriado = criarPedidoUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponse.from(pedidoCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable String id) {
        return buscarPedidoPorIdUseCase.executar(id)
                .map(PedidoResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

