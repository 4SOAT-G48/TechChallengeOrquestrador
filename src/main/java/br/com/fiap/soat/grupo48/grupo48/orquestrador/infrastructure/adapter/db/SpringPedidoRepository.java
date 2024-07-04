package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SpringPedidoRepository extends MongoRepository<PedidoEntity, UUID> {
}
