package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoRepository;


public class PedidoUsecaseImpl implements IPedidoPort {
    private final IPedidoRepository pedidoRepository;

    public PedidoUsecaseImpl(IPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido salvarPasso(String pedidoId, String identificacao, String nomePasso, String situacao) {
        return pedidoRepository.salvar(pedidoId, identificacao, nomePasso, situacao);
    }
}
