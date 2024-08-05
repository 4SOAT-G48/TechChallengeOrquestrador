package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido.PedidoBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PagamentoCriadoRoute extends PagamentoSagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pagamento.criado.route-key}")
    private String pagamentoCriadoRoutekey;
    @Value("${message.orquestrador.pagamento.criado.queues}")
    private String pagamentoCriadoQueues;

    @Value("${message.publish.pagamento.envia.exchange}")
    private String pagamentoEnviaExchange;
    @Value("${message.publish.pagamento.envia.route-key}")
    private String pagamentoEnviaRoutekey;

    @Value("${message.publish.pedido.atualiza-situacao.exchange}")
    private String pedidoAtualizaSituacaoExchange;

    @Value("${message.publish.pedido.atualiza-situacao.route-key}")
    private String pedidoAtualizaSituacaoRoutekey;

    protected PagamentoCriadoRoute(IPedidoPort pedidoPort) {
        super(pedidoPort);
    }

    @Override
    protected String getRouteFrom() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pagamentoCriadoRoutekey, pagamentoCriadoQueues);
    }

    @Override
    protected String getFirstRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pagamentoEnviaExchange, pagamentoEnviaRoutekey);
    }

    @Override
    protected String getSecondRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pedidoAtualizaSituacaoExchange, pedidoAtualizaSituacaoRoutekey);
    }

    @Override
    protected String getLogMessageFrom() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pagamentoCriadoRoutekey, pagamentoCriadoQueues);
    }

    @Override
    protected String getLogMessageTo() {
        return String.format("Envio do pagamento criado para pagamento exchange %s com routing key %s",
            pagamentoEnviaExchange, pagamentoEnviaRoutekey);
    }

    @Override
    protected String getStatusFrom() {
        return "Pagamento Criado";
    }

    @Override
    protected String getFirstRouteStatusTo() {
        return "Pagamento aguardando pagamento";
    }

    @Override
    protected String getSecondRouteStatusTo() {
        return "Pedido mudando situacao para AGUARDANDO_PAGAMENTO";
    }

    @Override
    protected String getStatusMessageFrom(String pagamentoId, String pedidoId) {
        return "Pagamento criado com sucesso. Pagamento ID %s e Pedido ID %s.".formatted(pagamentoId, pedidoId);
    }

    @Override
    protected String getFirstStatusMessageTo(String pagamentoId, String pedidoId) {
        return "Pagamento criado enviando para a fonte pagadora. Pagamento ID %s e Pedido ID %s.".formatted(pagamentoId, pedidoId);
    }

    @Override
    protected String getSecondStatusMessageTo(String pagamentoId, String pedidoId) {
        return "Pagamento criado mudando o status do pedido para AGUARDANDO_PAGAMENTO. Pagamento ID %s e Pedido ID %s.".formatted(pagamentoId, pedidoId);
    }

    @Override
    protected Object getBodyFirstRouteTo() {
        PagamentoBody pagamentoBody = this.getPagamentoBody();
        pagamentoBody.setSituacaoPagamento("AGUARDANDO_PAGAMENTO");
        return pagamentoBody;
    }

    @Override
    protected Object getBodySecondRouteTo() {
        PedidoBody pedidoBody = new PedidoBody();
        pedidoBody.setId(this.getPagamentoBody().getPedidoId());
        pedidoBody.setSituacao("AGUARDANDO_PAGAMENTO");
        pedidoBody.setPagamentoId(this.getPagamentoBody().getId());
        return pedidoBody;
    }
}
