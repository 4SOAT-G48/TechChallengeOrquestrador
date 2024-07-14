package br.com.fiap.soat.grupo48.grupo48.orquestrador.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private String id;

    private String pedidoId;

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
