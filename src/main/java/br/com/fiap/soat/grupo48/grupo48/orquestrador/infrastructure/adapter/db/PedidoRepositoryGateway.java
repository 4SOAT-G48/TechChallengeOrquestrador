package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PedidoRepositoryGateway implements IPedidoRepository {

    private final SpringPedidoRepository springPedidoRepository;

    public PedidoRepositoryGateway(SpringPedidoRepository springPedidoRepository) {
        this.springPedidoRepository = springPedidoRepository;
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID pedidoId) {
        return this.springPedidoRepository.findById(pedidoId).map(PedidoEntity::toPedido);
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return null;
    }
}
