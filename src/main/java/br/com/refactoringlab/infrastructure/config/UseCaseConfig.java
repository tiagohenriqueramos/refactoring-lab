package br.com.refactoringlab.infrastructure.config;

import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarPedidoUseCase criarPedidoUseCase(PedidoGateway pedidoGateway) {
        return new CriarPedidoUseCase(pedidoGateway);
    }

    @Bean
    public BuscarPedidoPorIdUseCase buscarPedidoPorIdUseCase(PedidoGateway pedidoGateway) {
        return new BuscarPedidoPorIdUseCase(pedidoGateway);
    }
}

