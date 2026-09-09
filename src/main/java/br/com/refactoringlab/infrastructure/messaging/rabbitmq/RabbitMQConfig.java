package br.com.refactoringlab.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_PEDIDOS = "pedidos.exchange";
    public static final String QUEUE_RASTREIO = "pedido.rastreio.queue";
    public static final String QUEUE_OCORRENCIA = "pedido.ocorrencia.queue";
    public static final String ROUTING_RASTREIO = "pedido.rastreio.created";
    public static final String ROUTING_OCORRENCIA = "pedido.ocorrencia.created";

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Queue rastreioQueue() {
        return QueueBuilder.durable(QUEUE_RASTREIO).build();
    }

    @Bean
    public Queue ocorrenciaQueue() {
        return QueueBuilder.durable(QUEUE_OCORRENCIA).build();
    }

    @Bean
    public Binding rastreioBinding(Queue rastreioQueue, TopicExchange pedidosExchange) {
        return BindingBuilder.bind(rastreioQueue).to(pedidosExchange).with(ROUTING_RASTREIO);
    }

    @Bean
    public Binding ocorrenciaBinding(Queue ocorrenciaQueue, TopicExchange pedidosExchange) {
        return BindingBuilder.bind(ocorrenciaQueue).to(pedidosExchange).with(ROUTING_OCORRENCIA);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}