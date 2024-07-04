package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private UUID id;

    private UUID pedidoId;

    private List<PassoPedido> passos;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PassoPedido {
        private String nomePasso;
        private String situacao;
        private LocalDateTime timestamp;
    }
}
