package br.com.refactoringlab.infrastructure.config;

import br.com.refactoringlab.application.factory.EncerramentoPedidoStrategyFactory;
import br.com.refactoringlab.application.gateways.OcorrenciaQueueGateway;
import br.com.refactoringlab.application.gateways.PedidoGateway;
import br.com.refactoringlab.application.gateways.RastreioQueueGateway;
import br.com.refactoringlab.application.gateways.RoteiroGateway;
import br.com.refactoringlab.application.strategy.EncerramentoInsucessoColetaStrategy;
import br.com.refactoringlab.application.strategy.EncerramentoPedidoStrategy;
import br.com.refactoringlab.application.strategy.EncerramentoSinistroStrategy;
import br.com.refactoringlab.application.strategy.EncerramentoSucessoEntregaStrategy;
import br.com.refactoringlab.application.usecases.*;
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
    public BuscarPedidosPorIdsUseCase buscarPedidosPorIdsUseCase(PedidoGateway pedidoGateway) {
        return new BuscarPedidosPorIdsUseCase(pedidoGateway);
    }

    @Bean
    public BuscarPedidosPorRoteiroUseCase buscarPedidosPorRoteiroUseCase(PedidoGateway pedidoGateway) {
        return new BuscarPedidosPorRoteiroUseCase(pedidoGateway);
    }

    @Bean
    public CriarRoteiroUseCase criarRoteiroUseCase(RoteiroGateway roteiroGateway) {
        return new CriarRoteiroUseCase(roteiroGateway);
    }

    @Bean
    public EncerramentoInsucessoColetaStrategy encerramentoInsucessoColetaStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        return new EncerramentoInsucessoColetaStrategy(pedidoGateway, ocorrenciaQueueGateway, rastreioQueueGateway);
    }

    @Bean
    public EncerramentoPedidoStrategyFactory encerramentoPedidoStrategyFactory(List<EncerramentoPedidoStrategy> strategies) {
        return new EncerramentoPedidoStrategyFactory(strategies);
    }

    @Bean
    public EncerrarRoteiroUseCase encerrarRoteiroUseCase(PedidoGateway pedidoGateway, EncerramentoPedidoStrategyFactory strategies) {
        return new EncerrarRoteiroUseCase(pedidoGateway, strategies);
    }

    @Bean
    public EncerramentoSinistroStrategy encerramentoSinistroStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        return new EncerramentoSinistroStrategy(pedidoGateway, ocorrenciaQueueGateway, rastreioQueueGateway);
    }

    @Bean
    public EncerramentoSucessoEntregaStrategy encerramentoSucessoEntregaStrategy(PedidoGateway pedidoGateway, OcorrenciaQueueGateway ocorrenciaQueueGateway, RastreioQueueGateway rastreioQueueGateway) {
        return new EncerramentoSucessoEntregaStrategy(pedidoGateway, ocorrenciaQueueGateway, rastreioQueueGateway);
    }
}

