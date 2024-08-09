package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.messaging;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageParameter {
    @Value(value = "${message.publish.pedido.recebido.route-key}")
    private String pedidosRecebidosRoutekey;

    @Value(value = "${message.publish.pedido.recebido.exchange}")
    private String pedidosRecebidosExchange;

    @Value(value = "${message.publish.pedido.recebido.erro.route-key}")
    private String pedidosRecebidosErroRoutekey;

    @Value(value = "${message.publish.pedido.recebido.erro.exchange}")
    private String pedidosRecebidosErroExchange;

    @Value(value = "${message.publish.pedido.registrado.route-key}")
    private String pedidosRegistradosRoutekey;

    @Value(value = "${message.publish.pedido.registrado.exchange}")
    private String pedidosRegistradosExchange;

    @Value(value = "${message.publish.pedido.registrado.erro.route-key}")
    private String pedidosRegistradosErroRoutekey;

    @Value(value = "${message.publish.pedido.registrado.erro.exchange}")
    private String pedidosRegistradosErroExchange;


    @Value(value = "${message.publish.pagamento.cria.route-key}")
    private String pagamentoCriaRoutekey;

    @Value(value = "${message.publish.pagamento.cria.exchange}")
    private String pagamentoCriaExchange;

    @Value(value = "${message.publish.pagamento.cria.erro.route-key}")
    private String pagamentoCriaErroRoutekey;

    @Value(value = "${message.publish.pagamento.cria.erro.exchange}")
    private String pagamentoCriaErroExchange;
}
