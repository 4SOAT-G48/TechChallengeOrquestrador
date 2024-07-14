package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PedidoRepositoryGateway implements IPedidoRepository {

    private final SpringPedidoRepository springPedidoRepository;

    public PedidoRepositoryGateway(SpringPedidoRepository springPedidoRepository) {
        this.springPedidoRepository = springPedidoRepository;
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return this.springPedidoRepository.findById(id).map(PedidoEntity::toPedido);
    }

    @Override
    public Optional<Pedido> buscarPorPedidoId(String pedidoId) {
        return this.springPedidoRepository.findByPedidoId(pedidoId).map(PedidoEntity::toPedido);
    }

    @Override
    public Optional<Pedido> buscarPorIdentificacao(String identificacao) {
        return this.springPedidoRepository.findByIdentificacao(identificacao).map(PedidoEntity::toPedido);
    }

    @Override
    public Optional<Pedido> buscarPorPedidoIdOuIdentificacao(String pedidoId, String identificacao) {
        return this.springPedidoRepository.findByPedidoIdOrIdentificacao(pedidoId, identificacao).map(PedidoEntity::toPedido);
    }

    @Override
    public Pedido salvar(String pedidoId, String identificacao, String nomePasso, String situacao) {
        Optional<PedidoEntity> pedidoRecuperado = this.springPedidoRepository.findByIdentificacao(identificacao);
        if (!pedidoRecuperado.isPresent()) {
            pedidoRecuperado = this.springPedidoRepository.findByPedidoId(pedidoId);
        }

        PedidoEntity pedido = pedidoRecuperado
            .orElseGet(() -> PedidoEntity.builder()
                .pedidoId(pedidoId)
                .identificacao(identificacao)
                .build());


        PedidoEntity.PassoPedido passo = new PedidoEntity.PassoPedido();
        passo.setNomePasso(nomePasso);
        passo.setSituacao(situacao);
        passo.setTimestamp(LocalDateTime.now());

        pedido.add(passo);

        return this.springPedidoRepository.save(pedido).toPedido();
    }
}
