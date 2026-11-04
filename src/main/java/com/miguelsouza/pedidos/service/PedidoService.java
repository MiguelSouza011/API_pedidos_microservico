package com.miguelsouza.pedidos.service;

import com.miguelsouza.pedidos.client.ServicoBancarioClient;
import com.miguelsouza.pedidos.exception.ItemNaoEncontradoException;
import com.miguelsouza.pedidos.model.DadosPagamento;
import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.model.enums.StatusPedido;
import com.miguelsouza.pedidos.model.enums.TipoPagamento;
import com.miguelsouza.pedidos.repository.ItemPedidoRepository;
import com.miguelsouza.pedidos.repository.PedidoRepository;
import com.miguelsouza.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido save(Pedido pedido) {
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public List<Pedido> findAll(){
        return repository.findAll();
    }

    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {
        var pedidoEncontrado = repository.
                findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()){
            var msg = String.format("Pedido não encontrado para o codigo %d e chave de pagamento %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();
        if(sucesso) {
            pedido.setStatus(StatusPedido.PAGO);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo) {
        var pedidoEncontrado = repository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()) {
            throw new ItemNaoEncontradoException("Pedido não encontrado para o codigo " + codigoPedido);
        }

        var pedido = pedidoEncontrado.get();
        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setDados(dadosCartao);
        dadosPagamento.setTipo(tipo);
        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando confirmação");
        repository.save(pedido);
        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);
    }
}
