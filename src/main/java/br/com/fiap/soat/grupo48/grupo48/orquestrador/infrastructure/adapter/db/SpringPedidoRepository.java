package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringPedidoRepository extends MongoRepository<PedidoEntity, String> {
    Optional<PedidoEntity> findByPedidoId(String pedidoId);

    Optional<PedidoEntity> findByIdentificacao(String identificacao);

    Optional<PedidoEntity> findByPedidoIdOrIdentificacao(String pedidoId, String identificacao);
}
