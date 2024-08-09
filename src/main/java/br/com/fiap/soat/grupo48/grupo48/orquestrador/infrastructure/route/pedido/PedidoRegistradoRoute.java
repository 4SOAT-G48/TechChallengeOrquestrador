package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento.PagamentoBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoRegistradoRoute extends PedidoSagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pedido.registrado.route-key}")
    private String pedidoRegistradoRoutekey;
    @Value("${message.orquestrador.pedido.registrado.queues}")
    private String pedidoRegistradoQueues;

    @Value(value = "${message.publish.pagamento.cria.route-key}")
    private String pagamentoCriaRoutekey;

    @Value(value = "${message.publish.pagamento.cria.exchange}")
    private String pagamentoCriaExchange;

    @Override
    protected String getRouteFrom() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pedidoRegistradoRoutekey, pedidoRegistradoQueues);
    }

    @Override
    protected String getRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pagamentoCriaExchange, pagamentoCriaRoutekey);
    }

    @Override
    protected String getLogMessageFrom() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pedidoRegistradoRoutekey, pedidoRegistradoQueues);
    }

    @Override
    protected String getLogMessageTo() {
        return String.format("Iniciando o envio de mensagens para exchange %s com routing key %s ",
            pagamentoCriaExchange, pagamentoCriaRoutekey);
    }

    @Override
    protected String getStatusFrom() {
        return "Pedido Registrado";
    }

    @Override
    protected String getStatusTo() {
        return "Pedido enviado para criação do pagamento";
    }

    @Override
    protected String getStatusMessageFrom(String pedidoId, String identificacao) {
        return "Pedido registrado e pode seguir para o pagamento. Order ID %s e identificador %s."
            .formatted(pedidoId, identificacao);
    }

    @Override
    protected String getStatusMessageTo(String pedidoId, String identificacao) {
        return "Pedido enviado para criação do pagamento. Order ID %s e identificador %s."
            .formatted(pedidoId, identificacao);
    }

    @Override
    protected Object getOutputBody() {
        PedidoBody pedido = this.getPedidoBody();
        PagamentoBody pagamento = new PagamentoBody();
        pagamento.toBuilder()
            .pedidoId(pedido.getId())
            .clienteId(pedido.getClienteId())
            .valor(pedido.getTotal())
            .build();

        return pagamento;
    }
}
