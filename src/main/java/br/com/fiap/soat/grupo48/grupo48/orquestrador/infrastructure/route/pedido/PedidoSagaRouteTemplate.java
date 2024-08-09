package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class PedidoSagaRouteTemplate extends RouteBuilder {

    @Autowired
    private IPedidoPort pedidoPort;

    private RouteDefinition routeDefinition;

    @Getter(AccessLevel.PROTECTED)
    private PedidoBody pedidoBody;

    protected static String getLogErroMessage() {
        return "Erro ao processar mensagem {} {} ";
    }

    private static String getLogWarnMessageJson() {
        return "Campo orderId não encontrado no JSON {}";
    }

    protected abstract String getRouteFrom();

    protected abstract String getRouteTo();

    protected abstract String getLogMessageFrom();

    protected abstract String getLogMessageTo();

    protected abstract String getStatusFrom();

    protected abstract String getStatusTo();

    protected abstract String getStatusMessageFrom(String pedidoId, String identificacao);

    protected abstract String getStatusMessageTo(String pedidoId, String identificacao);

    protected abstract Object getOutputBody();

    @Override
    public void configure() throws Exception {
        configureFrom();
        configureTo();
    }

    private void configureFrom() {
        routeDefinition = from(getRouteFrom())
            .log(getLogMessageFrom())
            .unmarshal().json(JsonLibrary.Jackson, PedidoBody.class)
            .process(exchange -> {
                this.pedidoBody = exchange.getIn().getBody(PedidoBody.class);
                String msg = getStatusMessageFrom(this.pedidoBody.getId(), this.pedidoBody.getIdentificacao());
                try {
                    log.info(msg);
                    pedidoPort.salvarPasso(
                        this.pedidoBody.getId(),
                        this.pedidoBody.getIdentificacao(),
                        getStatusFrom(),
                        msg
                    );
                } catch (Exception e) {
                    log.error(getLogErroMessage(), msg, this.pedidoBody, e);
                }
            });
    }

    public void configureTo() {
        if (Objects.nonNull(getRouteTo())) {

            routeDefinition
                .process(exchange -> {
                    // Convert PedidoBody to JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonPedidoBody = objectMapper.writeValueAsString(this.pedidoBody);
                    exchange.getIn().setBody(jsonPedidoBody);
                })
                .to(getRouteTo())
                .log(getLogMessageTo())
                .log(getRouteTo())
                .process(exchange -> {
                    String msg = getStatusMessageTo(this.pedidoBody.getId(), this.pedidoBody.getIdentificacao());
                    try {
                        log.info(msg);
                        pedidoPort.salvarPasso(
                            this.pedidoBody.getId(),
                            this.pedidoBody.getIdentificacao(),
                            getStatusTo(),
                            msg
                        );
                    } catch (Exception e) {
                        log.error(getLogErroMessage(), msg, this.pedidoBody, e);
                    }
                });
        }
    }
}
