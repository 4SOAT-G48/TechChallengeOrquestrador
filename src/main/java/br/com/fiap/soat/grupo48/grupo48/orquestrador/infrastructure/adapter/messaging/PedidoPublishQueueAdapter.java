package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.messaging;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoPublishQueueAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoPublishQueueAdapter implements IPedidoPublishQueueAdapter {

    @Value(value = "${message.pedido.recebido.route-key}")
    private String pedidosRecebidosRoutekey;

    @Value(value = "${message.pedido.recebido.exchange}")
    private String pedidosRecebidosExchange;

    @Value(value = "${message.pedido.recebido.erro.route-key}")
    private String pedidosRecebidosErroRoutekey;

    @Value(value = "${message.pedido.recebido.erro.exchange}")
    private String pedidosRecebidosErroExchange;

    @Value(value = "${message.pedido.registrado.route-key}")
    private String pedidosRegistradosRoutekey;

    @Value(value = "${message.pedido.registrado.exchange}")
    private String pedidosRegistradosExchange;

    @Value(value = "${message.pedido.registrado.erro.route-key}")
    private String pedidosRegistradosErroRoutekey;

    @Value(value = "${message.pedido.registrado.erro.exchange}")
    private String pedidosRegistradosErroExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishRecebido(@Payload String message) {
        this.rabbitTemplate.convertAndSend(pedidosRecebidosExchange, pedidosRecebidosRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos E:{} R:{} - {}", pedidosRecebidosExchange, pedidosRecebidosRoutekey, message);
    }

    @Override
    public void publishRecebidoErro(String message) {
        this.rabbitTemplate.convertAndSend(pedidosRecebidosErroExchange, pedidosRecebidosErroRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos com erro E:{} R:{} - {}", pedidosRecebidosErroExchange, pedidosRecebidosErroRoutekey, message);
    }

    @Override
    public void publishRegistrado(@Payload String message) {
        this.rabbitTemplate.convertAndSend(pedidosRegistradosExchange, pedidosRegistradosRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos E:{} R:{} - {}", pedidosRegistradosExchange, pedidosRegistradosRoutekey, message);
    }

    @Override
    public void publishRegistradoErro(String message) {
        this.rabbitTemplate.convertAndSend(pedidosRegistradosErroExchange, pedidosRegistradosErroRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos com erro E:{} R:{} - {}", pedidosRegistradosErroExchange, pedidosRecebidosErroRoutekey, message);
    }
}
