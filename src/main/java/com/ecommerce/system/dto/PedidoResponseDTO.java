package com.ecommerce.system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta de pedidos.
 */
public class PedidoResponseDTO {
    
    private Long id;
    private String numeroPedido;
    private BigDecimal valorTotal;
    private BigDecimal valorFrete;
    private String tipoFrete;
    private LocalDateTime dataCriacao;
    private String status;
    private ClienteDTO cliente;
    private List<ItemPedidoResponseDTO> itens;
    
    // Construtores
    public PedidoResponseDTO() {}
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroPedido() {
        return numeroPedido;
    }
    
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public BigDecimal getValorFrete() {
        return valorFrete;
    }
    
    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }
    
    public String getTipoFrete() {
        return tipoFrete;
    }
    
    public void setTipoFrete(String tipoFrete) {
        this.tipoFrete = tipoFrete;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public ClienteDTO getCliente() {
        return cliente;
    }
    
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemPedidoResponseDTO> itens) {
        this.itens = itens;
    }
    
    /**
     * DTO para cliente.
     */
    public static class ClienteDTO {
        private Long id;
        private String nome;
        private String email;
        
        // Construtores
        public ClienteDTO() {}
        
        public ClienteDTO(Long id, String nome, String email) {
            this.id = id;
            this.nome = nome;
            this.email = email;
        }
        
        // Getters e Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getNome() {
            return nome;
        }
        
        public void setNome(String nome) {
            this.nome = nome;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    /**
     * DTO para item do pedido.
     */
    public static class ItemPedidoResponseDTO {
        private Long id;
        private String nomeProduto;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal subtotal;
        
        // Construtores
        public ItemPedidoResponseDTO() {}
        
        // Getters e Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getNomeProduto() {
            return nomeProduto;
        }
        
        public void setNomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
        }
        
        public Integer getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
        
        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }
        
        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }
        
        public BigDecimal getSubtotal() {
            return subtotal;
        }
        
        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }
}

