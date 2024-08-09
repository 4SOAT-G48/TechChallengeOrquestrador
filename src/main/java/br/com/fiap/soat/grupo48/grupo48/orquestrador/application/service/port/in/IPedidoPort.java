package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;

public interface IPedidoPort {
    Pedido salvarPasso(String pedidoId, String identificacao, String nomePasso, String situacao);
}
