package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.adapter.db;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model.Pedido;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pedido")
public class PedidoEntity {
    @Id
    private String id;

    private String pedidoId;
    
    private String identificacao;

    private List<PassoPedido> passos;

    public Pedido toPedido() {
        List<Pedido.PassoPedido> passosPedido = this.passos.stream().map(PassoPedido::toPassosPedido).toList();
        return new Pedido(
            this.id,
            this.pedidoId,
            passosPedido);
    }

    public List<PassoPedido> getPassos() {
        if (Objects.isNull(this.passos)) {
            this.passos = new ArrayList<>();
        }
        return this.passos;
    }

    public void add(PassoPedido passo) {
        this.getPassos().add(passo);
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
