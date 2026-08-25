package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.CriarPedidoInput;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.domain.entities.Pedido;
import br.com.refactoringlab.domain.ports.PedidoRepositoryPort;
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
    private final PedidoRepositoryPort pedidoRepositoryPort;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase, PedidoRepositoryPort pedidoRepositoryPort) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody CriarPedidoInput input) {
        Pedido pedidoCriado = criarPedidoUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable String id) {
        return pedidoRepositoryPort.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

