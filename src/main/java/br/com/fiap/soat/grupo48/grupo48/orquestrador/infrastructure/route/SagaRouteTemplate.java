package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.route;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SagaRouteTemplate extends RouteBuilder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IPedidoPort pedidoPort;

    protected static String getLogErroMessage() {
        return "Erro ao processar mensagem {}";
    }

    private static String getLogWarnMessageJson() {
        return "Campo orderId não encontrado no JSON {}";
    }

    protected abstract String getRoute();

    protected abstract String getLogMessage();

    protected abstract String getStatus();

    protected abstract String getStatusMessage();

    @Override
    public void configure() throws Exception {
        from(getRoute())
            .log(getLogMessage())
            .process(exchange -> {
                String json = exchange.getIn().getBody(String.class);
                try {
                    String orderId = getPedidoIdFromJson(json);
                    String identificacao = getIdentificacaoFromJson(json);
                    log.info(getStatusMessage(), orderId, identificacao);
                    pedidoPort.salvarPasso(
                        orderId,
                        identificacao,
                        getStatus(),
                        String.format(getStatusMessage(), orderId, identificacao)
                    );
                } catch (Exception e) {
                    log.error(getLogErroMessage(), json, e);
                }
            });
    }

    private String getPedidoIdFromJson(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode pedidoIdNode = jsonNode.get("id");
        if (pedidoIdNode != null) {
            return pedidoIdNode.asText();
        } else {
            log.warn(getLogWarnMessageJson(), json);
            return null;
        }
    }

    private String getIdentificacaoFromJson(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode pedidoIdNode = jsonNode.get("identificacao");
        if (pedidoIdNode != null) {
            return pedidoIdNode.asText();
        } else {
            log.warn(getLogWarnMessageJson(), json);
            return null;
        }
    }
}
