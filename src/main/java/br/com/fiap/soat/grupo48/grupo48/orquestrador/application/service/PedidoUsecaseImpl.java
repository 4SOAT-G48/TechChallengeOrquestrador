package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PedidoUsecaseImpl implements IPedidoPort {
    private final IPedidoRepository pedidoRepository;

    public PedidoUsecaseImpl(IPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido salvarPasso(UUID pedidoId, String nomePasso, String situacao) {
        Pedido pedido = this.pedidoRepository.buscarPorId(pedidoId).orElse(new Pedido());
        pedido.setPedidoId(pedidoId);

        Pedido.PassoPedido passo = new Pedido.PassoPedido();
        passo.setNomePasso(nomePasso);
        passo.setSituacao(situacao);
        passo.setTimestamp(LocalDateTime.now());

        List<Pedido.PassoPedido> passos = pedido.getPassos() != null ? pedido.getPassos() : new ArrayList<>();
        passos.add(passo);
        pedido.setPassos(passos);

        return pedidoRepository.salvar(pedido);
    }
}
