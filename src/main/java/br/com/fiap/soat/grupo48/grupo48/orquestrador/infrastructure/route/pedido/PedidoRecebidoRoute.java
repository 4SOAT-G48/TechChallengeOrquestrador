package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.SagaRouteTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoRecebidoRoute extends SagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pedido.recebido.route-key}")
    private String pedidoRecebidoRoutekey;
    @Value("${message.orquestrador.pedido.recebido.queues}")
    private String pedidoRecebidoQueues;

    @Override
    protected String getRoute() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pedidoRecebidoRoutekey, pedidoRecebidoQueues);
    }

    @Override
    protected String getLogMessage() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pedidoRecebidoRoutekey, pedidoRecebidoQueues);
    }

    @Override
    protected String getStatus() {
        return "Pedido Recebido";
    }

    @Override
    protected String getStatusMessage() {
        return "Cliente terminou o pedido e o sistema recebeu este pedido. Order ID %s e identificador %s.";
    }
}
