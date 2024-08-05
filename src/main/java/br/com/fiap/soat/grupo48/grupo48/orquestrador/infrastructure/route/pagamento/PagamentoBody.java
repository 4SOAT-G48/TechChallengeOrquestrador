package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route.pagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoBody {
    private String id;
    private UUID codigo;
    private String clienteId;
    private String pedidoId;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private String situacaoPagamento;
    private String metodoPagamento;
}
