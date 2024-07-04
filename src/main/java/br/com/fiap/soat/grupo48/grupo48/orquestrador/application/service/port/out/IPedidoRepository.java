package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;

import java.util.Optional;
import java.util.UUID;

public interface IPedidoRepository {
    Optional<Pedido> buscarPorId(UUID pedidoId);

    Pedido salvar(Pedido pedido);
}
