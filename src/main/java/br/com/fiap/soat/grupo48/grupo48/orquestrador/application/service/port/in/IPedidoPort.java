package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;

import java.util.UUID;

public interface IPedidoPort {
    Pedido salvarPasso(UUID pedidoId, String nomePasso, String situacao);
}
