package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;

import java.util.UUID;

@AllArgsConstructor
public class PedidoSagaRoute extends RouteBuilder {

    private IPedidoPort pedidoPort;

    @Override
    public void configure() throws Exception {
        // só recebe para registro do passo
        from("rabbitmq:pedido.exchange?queue=pedido.queue&autoDelete=false")
            .process(exchange -> {
                UUID orderId = exchange.getIn().getBody(UUID.class);
                pedidoPort.salvarPasso(orderId, "Recebido", "Cliente terminou o pedido e o sistema recebeu este pedido");
            });
    }
}
