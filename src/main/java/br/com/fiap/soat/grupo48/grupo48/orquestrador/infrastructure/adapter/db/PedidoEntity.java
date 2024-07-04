package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "pedido")
public class PedidoEntity {
    @Id
    private UUID id;

    private UUID pedidoId;

    private List<PassoPedido> passos;

    public Pedido toPedido() {
        List<Pedido.PassoPedido> passosPedido = this.passos.stream().map(PassoPedido::toPassosPedido).toList();
        return new Pedido(
            this.id,
            this.pedidoId,
            passosPedido);
    }

    @Getter
    @Setter
    public static class PassoPedido {
        private String nomePasso;
        private String situacao;
        private LocalDateTime timestamp;

        public Pedido.PassoPedido toPassosPedido() {
            return new Pedido.PassoPedido(
                this.nomePasso,
                this.situacao,
                this.timestamp);
        }
    }
}
