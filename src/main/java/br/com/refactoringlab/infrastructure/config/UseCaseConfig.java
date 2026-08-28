package br.com.refactoringlab.infrastructure.config;

import br.com.refactoringlab.application.gateways.EncerrarRoteiroGateway;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.gateways.RastreioInternoGateway;
import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.application.usecases.BuscarPedidoPorIdUseCase;
import br.com.refactoringlab.application.usecases.CriarPedidoUseCase;
import br.com.refactoringlab.application.usecases.EncerrarRoteiroUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    public EncerrarRoteiroUseCase encerrarRoteiroUseCase(
            PedidoGateway pedidoGateway,
            EncerrarRoteiroGateway encerrarRoteiroGateway,
            RastreioInternoGateway rastreioInternoGateway,
            List<EncerramentoPedidoStrategy> strategies) {
        return new EncerrarRoteiroUseCase(pedidoGateway, encerrarRoteiroGateway, rastreioInternoGateway, strategies);
    }
}

