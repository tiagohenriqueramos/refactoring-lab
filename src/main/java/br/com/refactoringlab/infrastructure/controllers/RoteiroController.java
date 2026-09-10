package br.com.refactoringlab.infrastructure.controllers;

import br.com.refactoringlab.application.dto.CriarRoteiroInput;
import br.com.refactoringlab.application.usecases.CriarRoteiroUseCase;
import br.com.refactoringlab.infrastructure.controllers.dto.RoteiroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/roteiros")
public class RoteiroController {

    private final CriarRoteiroUseCase criarRoteiroUseCase;

    public RoteiroController(CriarRoteiroUseCase criarRoteiroUseCase) {
        this.criarRoteiroUseCase = criarRoteiroUseCase;
    }

    @PostMapping
    public ResponseEntity<RoteiroResponse> criar(@RequestBody CriarRoteiroInput input) {
        var roteiro = criarRoteiroUseCase.executar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoteiroResponse.from(roteiro));
    }
}