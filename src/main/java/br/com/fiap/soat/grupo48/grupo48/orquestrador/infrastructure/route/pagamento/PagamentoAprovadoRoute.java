package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido.PedidoBody;
import org.springframework.beans.factory.annotation.Value;

public class PagamentoAprovadoRoute extends PagamentoSagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pagamento.aprovado.route-key}")
    private String pagamentoAprovadoRoutekey;
    @Value("${message.orquestrador.pagamento.aprovado.queues}")
    private String pagamentoAprovadoQueues;

    @Value("${message.publish.pedido.atualiza-situacao.exchange}")
    private String pedidoAtualizaSituacaoExchange;
    @Value("${message.publish.pedido.atualiza-situacao.route-key}")
    private String pedidoAtualizaSituacaoRoutekey;

    @Value("${message.publish.producao.envia.exchange}")
    private String producaoEnviaExchange;
    @Value("${message.publish.producao.envia.route-key}")
    private String producaoEnviaRoutekey;

    protected PagamentoAprovadoRoute(IPedidoPort pedidoPort) {
        super(pedidoPort);
    }

    @Override
    protected String getRouteFrom() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pagamentoAprovadoRoutekey, pagamentoAprovadoQueues);
    }

    @Override
    protected String getFirstRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pedidoAtualizaSituacaoExchange, pedidoAtualizaSituacaoRoutekey);
    }

    @Override
    protected String getSecondRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            producaoEnviaExchange, producaoEnviaRoutekey);
    }

    @Override
    protected String getLogMessageFrom() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pagamentoAprovadoRoutekey, pagamentoAprovadoQueues);
    }

    @Override
    protected String getLogMessageTo() {
        return String.format("Envio do pagamento aprovado para pedido exchange %s com routing key %s",
            pedidoAtualizaSituacaoExchange, pedidoAtualizaSituacaoRoutekey);
    }

    @Override
    protected String getStatusFrom() {
        return "Pagamento Aprovado";
    }

    @Override
    protected String getFirstRouteStatusTo() {
        return "Pedido mudando situação para EM_PREPARACAO";
    }

    @Override
    protected String getSecondRouteStatusTo() {
        return "Pedido enviado para produção";
    }

    @Override
    protected String getStatusMessageFrom(String pagamentoId, String pedidoId) {
        return String.format("Pagamento %s aprovado para pedido %s", pagamentoId, pedidoId);
    }

    @Override
    protected String getFirstStatusMessageTo(String pagamentoId, String pedidoId) {
        return String.format("Pedido %s mudando situação para EM_PREPARACAO", pedidoId);
    }

    @Override
    protected String getSecondStatusMessageTo(String pagamentoId, String pedidoId) {
        return String.format("Pedido %s enviado para produção", pedidoId);
    }

    @Override
    protected Object getBodyFirstRouteTo() {
        PedidoBody pedidoBody = new PedidoBody();
        pedidoBody.setId(this.getPagamentoBody().getPedidoId());
        pedidoBody.setSituacao("EM_PREPARACAO");
        pedidoBody.setPagamentoId(this.getPagamentoBody().getId());
        return pedidoBody;
    }

    @Override
    protected Object getBodySecondRouteTo() {
        //TODO: Teria que buscar o pedido e montar o body
        return null;
    }
}
