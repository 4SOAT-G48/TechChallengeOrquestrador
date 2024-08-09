package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.messaging;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoPublishQueueAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoPublishQueueAdapter implements IPedidoPublishQueueAdapter {

    @Autowired
    private MessageParameter msgParam;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishRecebido(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPedidosRecebidosExchange(), msgParam.getPedidosRecebidosRoutekey(), message);
        log.info("Mensagem enviada para a fila de pedidos recebidos E:{} R:{} - {}", msgParam.getPedidosRecebidosExchange(), msgParam.getPedidosRecebidosRoutekey(), message);
    }

    @Override
    public void publishRecebidoErro(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPedidosRecebidosErroExchange(), msgParam.getPedidosRecebidosErroRoutekey(), message);
        log.info("Mensagem enviada para a fila de pedidos recebidos com erro E:{} R:{} - {}", msgParam.getPedidosRecebidosErroExchange(), msgParam.getPedidosRecebidosErroRoutekey(), message);
    }

    @Override
    public void publishRegistrado(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPedidosRegistradosExchange(), msgParam.getPedidosRegistradosRoutekey(), message);
        log.info("Mensagem enviada para a fila de pedidos registrados E:{} R:{} - {}", msgParam.getPedidosRegistradosExchange(), msgParam.getPedidosRegistradosRoutekey(), message);
    }

    @Override
    public void publishRegistradoErro(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPedidosRegistradosErroExchange(), msgParam.getPedidosRegistradosErroRoutekey(), message);
        log.info("Mensagem enviada para a fila de pedidos registrados com erro E:{} R:{} - {}", msgParam.getPedidosRegistradosErroExchange(), msgParam.getPedidosRegistradosErroRoutekey(), message);
    }


    @Override
    public void publishPagamentoIniciado(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPagamentoCriaExchange(), msgParam.getPagamentoCriaRoutekey(), message);
        log.info("Mensagem enviada para a fila de pagamento para iniciar E:{} R:{} - {}", msgParam.getPagamentoCriaExchange(), msgParam.getPagamentoCriaRoutekey(), message);
    }

    @Override
    public void publishPagamentoIniciadoErro(@Payload String message) {
        this.rabbitTemplate.convertAndSend(msgParam.getPagamentoCriaErroExchange(), msgParam.getPagamentoCriaErroRoutekey(), message);
        log.info("Mensagem enviada para a fila de pagamento para iniciar com erro E:{} R:{} - {}", msgParam.getPagamentoCriaErroExchange(), msgParam.getPagamentoCriaErroRoutekey(), message);
    }
}
