package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoRecebidoRoute extends PedidoSagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pedido.recebido.route-key}")
    private String pedidoRecebidoRoutekey;
    @Value("${message.orquestrador.pedido.recebido.queues}")
    private String pedidoRecebidoQueues;


    @Value("${message.publish.pagamento.cria.exchange}")
    private String pagamentoCriaExchange;
    @Value("${message.publish.pagamento.cria.route-key}")
    private String pagamentoCriaRoutekey;


    @Override
    protected String getRouteFrom() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pedidoRecebidoRoutekey, pedidoRecebidoQueues);
    }

    @Override
    protected String getRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pagamentoCriaExchange, pagamentoCriaRoutekey);
    }

    @Override
    protected String getLogMessageFrom() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pedidoRecebidoRoutekey, pedidoRecebidoQueues);
    }

    @Override
    protected String getLogMessageTo() {
        return String.format("Envio do pedido para pagamento exchange %s com routing key %s",
            pagamentoCriaExchange, pagamentoCriaRoutekey);
    }

    @Override
    protected String getStatusFrom() {
        return "Pedido Recebido";
    }

    @Override
    protected String getStatusTo() {
        return "Pedido enviado para pagamento";
    }

    @Override
    protected String getStatusMessageFrom(String pedidoId, String identificacao) {
        return "Cliente terminou o pedido e o sistema recebeu este pedido. Order ID %s e identificador %s."
            .formatted(pedidoId, identificacao);
    }

    @Override
    protected String getStatusMessageTo(String pedidoId, String identificacao) {
        return "O sistema recebeu o pedido e enviou para o pagamento. Order ID %s e identificador %s."
            .formatted(pedidoId, identificacao);
    }

    @Override
    protected Object getOutputBody() {
        return null;
    }
}
