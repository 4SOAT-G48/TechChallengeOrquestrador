package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.SagaRouteTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoRegistradoRoute extends SagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pedido.registrado.route-key}")
    private String pedidoRegistradoRoutekey;
    @Value("${message.orquestrador.pedido.registrado.queues}")
    private String pedidoRegistradoQueues;

    @Override
    protected String getRoute() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pedidoRegistradoRoutekey, pedidoRegistradoQueues);
    }

    @Override
    protected String getLogMessage() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pedidoRegistradoRoutekey, pedidoRegistradoQueues);
    }

    @Override
    protected String getStatus() {
        return "Pedido Registrado";
    }

    @Override
    protected String getStatusMessage() {
        return "Pedido registrado e pode seguir para o pagamento. Order ID %s e identificador %s.";
    }
}
