package com.miguelsouza.pedidos.publisher;

import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.publisher.mapper.DetalhePedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoPublisher {

    private final DetalhePedidoMapper mapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${miguelsouza.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar (Pedido pedido){
        log.info("Publicando pedido pago: {}", pedido.getCodigo());

        try {
            var representation = mapper.map(pedido);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados", json);
        } catch (JacksonException e) {
            log.error("Erro ao processar o Json ", e);
        } catch (RuntimeException e) {
            log.error("Erro técnico ao publicar no topico de pedidos ", e);
        }
    }
}
