package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido.PedidoBody;
import org.springframework.beans.factory.annotation.Value;

public class PagamentoReprovadoRoute extends PagamentoSagaRouteTemplate {

    @Value("${message.orquestrador.exchange}")
    private String orquestradorExchange;
    @Value("${message.orquestrador.pagamento.reprovado.route-key}")
    private String pagamentoReprovadoRoutekey;
    @Value("${message.orquestrador.pagamento.reprovado.queues}")
    private String pagamentoReprovadoQueues;

    @Value("${message.publish.pedido.atualiza-situacao.exchange}")
    private String pedidoAtualizaSituacaoExchange;
    @Value("${message.publish.pedido.atualiza-situacao.route-key}")
    private String pedidoAtualizaSituacaoRoutekey;

    @Value("${message.publish.notifica-usuario.mensagem.exchange}")
    private String notificaUsuarioMensagemExchange;
    @Value("${message.publish.notifica-usuario.mensagem.route-key}")
    private String notificaUsuarioMensagemRoutekey;

    protected PagamentoReprovadoRoute(IPedidoPort pedidoPort) {
        super(pedidoPort);
    }

    @Override
    protected String getRouteFrom() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&queues=%s&exchangeType=direct",
            orquestradorExchange, pagamentoReprovadoRoutekey, pagamentoReprovadoQueues);
    }

    @Override
    protected String getFirstRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            pedidoAtualizaSituacaoExchange, pedidoAtualizaSituacaoRoutekey);
    }

    @Override
    protected String getSecondRouteTo() {
        return String.format("spring-rabbitmq:%s?routingKey=%s&exchangeType=direct",
            notificaUsuarioMensagemExchange, notificaUsuarioMensagemRoutekey);
    }

    @Override
    protected String getLogMessageFrom() {
        return String.format("Iniciando consumo de mensagens do exchange %s com routing key %s vinculada as filas %s",
            orquestradorExchange, pagamentoReprovadoRoutekey, pagamentoReprovadoQueues);
    }

    @Override
    protected String getLogMessageTo() {
        return String.format("Envio do pagamento reprovado para pedido exchange %s com routing key %s",
            pedidoAtualizaSituacaoExchange, pedidoAtualizaSituacaoRoutekey);
    }

    @Override
    protected String getStatusFrom() {
        return "Pagamento Reprovado";
    }

    @Override
    protected String getFirstRouteStatusTo() {
        return "Pedido mudando situação para FALHA_PAGAMENTO";
    }

    @Override
    protected String getSecondRouteStatusTo() {
        return "Pedido enviado reprovado";
    }

    @Override
    protected String getStatusMessageFrom(String pagamentoId, String pedidoId) {
        return String.format("Pagamento %s reprovado para pedido %s", pagamentoId, pedidoId);
    }

    @Override
    protected String getFirstStatusMessageTo(String pagamentoId, String pedidoId) {
        return String.format("Pedido %s mudando situação para FALHA_PAGAMENTO", pedidoId);
    }

    @Override
    protected String getSecondStatusMessageTo(String pagamentoId, String pedidoId) {
        return String.format("Cliente avisado sobre a recusa do pagamento do pedido %s", pedidoId);
    }

    @Override
    protected Object getBodyFirstRouteTo() {
        PedidoBody pedidoBody = new PedidoBody();
        pedidoBody.setId(this.getPagamentoBody().getPedidoId());
        pedidoBody.setSituacao("FALHA_PAGAMENTO");
        pedidoBody.setPagamentoId(this.getPagamentoBody().getId());
        return pedidoBody;
    }

    @Override
    protected Object getBodySecondRouteTo() {
        //TODO: Teria que buscar o pedido e montar o body com o identificador do pedido
        return null;
    }
}
