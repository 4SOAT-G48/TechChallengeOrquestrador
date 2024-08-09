package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pedido;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoBody {
    private String id;
    private String clienteId;
    private String situacao;
    private String identificacao;
    private BigDecimal total;
    private String pagamentoId;
    private Date dataCriacao;
    private Date dataAtualizacao;
}
