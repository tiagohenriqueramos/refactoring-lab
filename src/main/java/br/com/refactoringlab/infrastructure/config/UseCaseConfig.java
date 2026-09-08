package br.com.refactoringlab.infrastructure.config;

import br.com.refactoringlab.application.factory.EncerramentoPedidoStrategyFactory;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.application.usecases.EncerrarRoteiroUseCase;
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

    @Bean
    public EncerrarRoteiroUseCase encerrarRoteiroUseCase(PedidoGateway pedidoGateway, EncerramentoPedidoStrategyFactory strategies) {
        return new EncerrarRoteiroUseCase(pedidoGateway, strategies);
    }
}

