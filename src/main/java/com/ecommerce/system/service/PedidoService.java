package com.ecommerce.system.service;

import com.ecommerce.system.domain.Cliente;
import com.ecommerce.system.domain.ItemPedido;
import com.ecommerce.system.domain.Pedido;
import com.ecommerce.system.domain.Produto;
import com.ecommerce.system.dto.CriarPedidoDTO;
import com.ecommerce.system.dto.PedidoResponseDTO;
import com.ecommerce.system.factory.FreteStrategyFactory;
import com.ecommerce.system.repository.ClienteRepository;
import com.ecommerce.system.repository.PedidoRepository;
import com.ecommerce.system.repository.ProdutoRepository;
import com.ecommerce.system.state.PedidoStateManager;
import com.ecommerce.system.strategy.FreteCalculator;
import com.ecommerce.system.strategy.FreteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    /**
     * Cria um novo pedido.
     * @param criarPedidoDTO DTO com os dados do pedido
     * @return DTO com os dados do pedido criado
     */
    public PedidoResponseDTO criarPedido(CriarPedidoDTO criarPedidoDTO) {
        // Validar cliente
        Cliente cliente = clienteRepository.findById(criarPedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        // Validar tipo de frete
        if (!FreteStrategyFactory.isTipoFreteSuportado(criarPedidoDTO.getTipoFrete())) {
            throw new RuntimeException("Tipo de frete não suportado: " + criarPedidoDTO.getTipoFrete());
        }
        
        // Criar pedido
        String numeroPedido = gerarNumeroPedido();
        Pedido pedido = new Pedido(numeroPedido, BigDecimal.ZERO, cliente);
        
        // Calcular valor total dos itens
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (CriarPedidoDTO.ItemPedidoDTO itemDTO : criarPedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));
            
            // Verificar estoque
            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            
            ItemPedido item = new ItemPedido(pedido, produto, itemDTO.getQuantidade(), produto.getPreco());
            pedido.getItens().add(item);
            valorTotal = valorTotal.add(item.getSubtotal());
            
            // Atualizar estoque
            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);
        }
        
        pedido.setValorTotal(valorTotal);
        
        // Calcular frete
        FreteStrategy freteStrategy = FreteStrategyFactory.criarFreteStrategy(criarPedidoDTO.getTipoFrete());
        FreteCalculator freteCalculator = new FreteCalculator(freteStrategy);
        BigDecimal valorFrete = freteCalculator.calcularFrete(valorTotal);
        
        pedido.setValorFrete(valorFrete);
        pedido.setTipoFrete(criarPedidoDTO.getTipoFrete());
        
        // Salvar pedido
        pedido = pedidoRepository.save(pedido);
        
        return converterParaDTO(pedido);
    }
    
    /**
     * Busca pedido por ID.
     * @param id ID do pedido
     * @return DTO com os dados do pedido
     */
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return converterParaDTO(pedido);
    }
    
    /**
     * Lista todos os pedidos.
     * @return Lista de DTOs com os dados dos pedidos
     */
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Processa o pagamento de um pedido.
     * @param id ID do pedido
     * @return DTO com os dados do pedido atualizado
     */
    public PedidoResponseDTO pagarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        boolean sucesso = PedidoStateManager.pagar(pedido);
        if (!sucesso) {
            throw new RuntimeException("Não foi possível processar o pagamento do pedido");
        }
        
        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }
    
    /**
     * Cancela um pedido.
     * @param id ID do pedido
     * @return DTO com os dados do pedido atualizado
     */
    public PedidoResponseDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        boolean sucesso = PedidoStateManager.cancelar(pedido);
        if (!sucesso) {
            throw new RuntimeException("Não foi possível cancelar o pedido");
        }
        
        // Devolver itens ao estoque se o pedido ainda não foi enviado
        if (!"ENVIADO".equals(pedido.getStatus())) {
            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();
                produto.setEstoque(produto.getEstoque() + item.getQuantidade());
                produtoRepository.save(produto);
            }
        }
        
        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }
    
    /**
     * Envia um pedido.
     * @param id ID do pedido
     * @return DTO com os dados do pedido atualizado
     */
    public PedidoResponseDTO enviarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        boolean sucesso = PedidoStateManager.enviar(pedido);
        if (!sucesso) {
            throw new RuntimeException("Não foi possível enviar o pedido");
        }
        
        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }
    
    /**
     * Gera um número único para o pedido.
     * @return Número do pedido
     */
    private String gerarNumeroPedido() {
        LocalDateTime agora = LocalDateTime.now();
        String timestamp = agora.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "PED" + timestamp;
    }
    
    /**
     * Converte uma entidade Pedido para DTO.
     * @param pedido Entidade Pedido
     * @return DTO do pedido
     */
    private PedidoResponseDTO converterParaDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setNumeroPedido(pedido.getNumeroPedido());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setValorFrete(pedido.getValorFrete());
        dto.setTipoFrete(pedido.getTipoFrete());
        dto.setDataCriacao(pedido.getDataCriacao());
        dto.setStatus(pedido.getStatus());
        
        // Cliente
        PedidoResponseDTO.ClienteDTO clienteDTO = new PedidoResponseDTO.ClienteDTO();
        clienteDTO.setId(pedido.getCliente().getId());
        clienteDTO.setNome(pedido.getCliente().getNome());
        clienteDTO.setEmail(pedido.getCliente().getEmail());
        dto.setCliente(clienteDTO);
        
        // Itens
        List<PedidoResponseDTO.ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(item -> {
                    PedidoResponseDTO.ItemPedidoResponseDTO itemDTO = new PedidoResponseDTO.ItemPedidoResponseDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setNomeProduto(item.getProduto().getNome());
                    itemDTO.setQuantidade(item.getQuantidade());
                    itemDTO.setPrecoUnitario(item.getPrecoUnitario());
                    itemDTO.setSubtotal(item.getSubtotal());
                    return itemDTO;
                })
                .collect(Collectors.toList());
        dto.setItens(itensDTO);
        
        return dto;
    }
}

