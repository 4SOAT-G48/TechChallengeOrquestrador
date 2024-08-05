package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Objects;

public abstract class PagamentoSagaRouteTemplate extends RouteBuilder {

    public static final String CAMPO_PAGAMENTO_ID_NAO_ENCONTRADO_NO_JSON = "Campo pagamentoId não encontrado no JSON {}";
    public static final String ERRO_AO_PROCESSAR_MENSAGEM = "Erro ao processar mensagem {} {} ";

    private IPedidoPort pedidoPort;

    private RouteDefinition routeDefinition;

    @Getter(AccessLevel.PROTECTED)
    private PagamentoBody pagamentoBody;

    protected PagamentoSagaRouteTemplate(IPedidoPort pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    protected abstract String getRouteFrom();

    protected abstract String getFirstRouteTo();

    protected abstract String getSecondRouteTo();

    protected abstract String getLogMessageFrom();

    protected abstract String getLogMessageTo();

    protected abstract String getStatusFrom();

    protected abstract String getFirstRouteStatusTo();

    protected abstract String getSecondRouteStatusTo();

    protected abstract String getStatusMessageFrom(String pagamentoId, String pedidoId);

    protected abstract String getFirstStatusMessageTo(String pagamentoId, String pedidoId);

    protected abstract String getSecondStatusMessageTo(String pagamentoId, String pedidoId);

    protected abstract Object getBodyFirstRouteTo();

    protected abstract Object getBodySecondRouteTo();

    @Override
    public void configure() throws Exception {
        configureFrom();
        configureFirstTo();
        configureSecondTo();
    }

    private void configureFrom() {
        routeDefinition = from(getRouteFrom())
            .log(getLogMessageFrom())
            .unmarshal().json(JsonLibrary.Jackson, PagamentoBody.class)
            .process(exchange -> {
                this.pagamentoBody = exchange.getIn().getBody(PagamentoBody.class);
                String msg = getStatusMessageFrom(this.pagamentoBody.getId(), this.pagamentoBody.getPedidoId());
                try {
                    log.info(msg);
                    pedidoPort.salvarPasso(
                        this.pagamentoBody.getId(),
                        this.pagamentoBody.getPedidoId(),
                        getStatusFrom(),
                        msg
                    );
                } catch (Exception e) {
                    log.error(ERRO_AO_PROCESSAR_MENSAGEM, msg, this.pagamentoBody, e);
                }
            });
    }

    public void configureFirstTo() {
        if (Objects.nonNull(getFirstRouteTo())) {

            routeDefinition
                .process(exchange -> {
                    // Convert PagamentoBody to JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonPedidoBody = objectMapper.writeValueAsString(getBodyFirstRouteTo());
                    exchange.getIn().setBody(jsonPedidoBody);
                })
                .to(getFirstRouteTo())
                .log(getLogMessageTo())
                .log(getFirstRouteTo())
                .process(exchange -> {
                    String msg = getFirstStatusMessageTo(this.pagamentoBody.getId(), this.pagamentoBody.getPedidoId());
                    try {
                        log.info(msg);
                        pedidoPort.salvarPasso(
                            this.pagamentoBody.getId(),
                            this.pagamentoBody.getPedidoId(),
                            getFirstRouteStatusTo(),
                            msg
                        );
                    } catch (Exception e) {
                        log.error(ERRO_AO_PROCESSAR_MENSAGEM, msg, this.pagamentoBody, e);
                    }
                });
        }
    }

    public void configureSecondTo() {
        if (Objects.nonNull(getSecondRouteTo())) {

            routeDefinition
                .process(exchange -> {
                    // Convert PagamentoBody to JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonPedidoBody = objectMapper.writeValueAsString(getBodySecondRouteTo());
                    exchange.getIn().setBody(jsonPedidoBody);
                })
                .to(getSecondRouteTo())
                .log(getLogMessageTo())
                .log(getSecondRouteTo())
                .process(exchange -> {
                    String msg = getSecondStatusMessageTo(this.pagamentoBody.getId(), this.pagamentoBody.getPedidoId());
                    try {
                        log.info(msg);
                        pedidoPort.salvarPasso(
                            this.pagamentoBody.getId(),
                            this.pagamentoBody.getPedidoId(),
                            getFirstRouteStatusTo(),
                            msg
                        );
                    } catch (Exception e) {
                        log.error(ERRO_AO_PROCESSAR_MENSAGEM, msg, this.pagamentoBody, e);
                    }
                });
        }
    }
}
