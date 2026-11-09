package com.miguelsouza.pedidos.controller;

import com.miguelsouza.pedidos.dto.AdicionarNovoPagamentoDTO;
import com.miguelsouza.pedidos.dto.NovoPedidoDTO;
import com.miguelsouza.pedidos.exception.ItemNaoEncontradoException;
import com.miguelsouza.pedidos.exception.ValidationException;
import com.miguelsouza.pedidos.mapper.PedidoMapper;
import com.miguelsouza.pedidos.model.ErroResposta;
import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.publisher.mapper.DetalhePedidoMapper;
import com.miguelsouza.pedidos.publisher.representation.DetalhePedidoDTO;
import com.miguelsouza.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.save(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro de validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarPagamento(@RequestBody AdicionarNovoPagamentoDTO dto) {
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dadosCartao(), dto.tipo());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoDTO> obterDetalhesPedido(@PathVariable Long codigo){
        return service.carregarDadosCompletosPedido(codigo)
                .map(pedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
