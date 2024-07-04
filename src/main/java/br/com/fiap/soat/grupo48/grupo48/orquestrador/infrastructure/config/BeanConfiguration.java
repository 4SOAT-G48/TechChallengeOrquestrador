package br.com.fiap.soat.grupo48.grupo48.orquestrador.infrastructure.config;

import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.PedidoUsecaseImpl;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.in.IPedidoPort;
import br.com.fiap.soat.grupo48.grupo48.orquestrador.application.service.port.out.IPedidoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    IPedidoPort pedidoUsecase(IPedidoRepository pedidoRepository) {
        return new PedidoUsecaseImpl(pedidoRepository);

    }
}
