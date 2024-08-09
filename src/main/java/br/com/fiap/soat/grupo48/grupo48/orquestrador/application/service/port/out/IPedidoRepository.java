package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;

import java.util.Optional;

public interface IPedidoRepository {
    Optional<Pedido> buscarPorId(String id);

    Optional<Pedido> buscarPorPedidoId(String pedidoId);

    Optional<Pedido> buscarPorIdentificacao(String identificacao);

    Optional<Pedido> buscarPorPedidoIdOuIdentificacao(String pedidoId, String identificacao);

    Pedido salvar(String pedidoId, String identificacao, String nomePasso, String situacao);
}
