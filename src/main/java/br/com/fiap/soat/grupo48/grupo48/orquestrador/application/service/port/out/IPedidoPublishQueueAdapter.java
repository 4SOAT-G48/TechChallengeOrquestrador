package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out;

import org.springframework.messaging.handler.annotation.Payload;

public interface IPedidoPublishQueueAdapter {
    void publishRecebido(String message);

    void publishRecebidoErro(String message);

    void publishRegistrado(@Payload String message);

    void publishRegistradoErro(String message);

    void publishPagamentoIniciado(@Payload String message);

    void publishPagamentoIniciadoErro(@Payload String message);
}
