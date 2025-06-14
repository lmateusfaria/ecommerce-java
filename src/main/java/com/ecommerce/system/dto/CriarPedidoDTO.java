package com.ecommerce.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para criação de pedidos.
 */
public class CriarPedidoDTO {
    
    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;
    
    @NotBlank(message = "Tipo de frete é obrigatório")
    private String tipoFrete;
    
    @NotNull(message = "Lista de itens é obrigatória")
    private List<ItemPedidoDTO> itens;
    
    // Construtores
    public CriarPedidoDTO() {}
    
    public CriarPedidoDTO(Long clienteId, String tipoFrete, List<ItemPedidoDTO> itens) {
        this.clienteId = clienteId;
        this.tipoFrete = tipoFrete;
        this.itens = itens;
    }
    
    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getTipoFrete() {
        return tipoFrete;
    }
    
    public void setTipoFrete(String tipoFrete) {
        this.tipoFrete = tipoFrete;
    }
    
    public List<ItemPedidoDTO> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
    
    /**
     * DTO para itens do pedido.
     */
    public static class ItemPedidoDTO {
        
        @NotNull(message = "ID do produto é obrigatório")
        private Long produtoId;
        
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        private Integer quantidade;
        
        // Construtores
        public ItemPedidoDTO() {}
        
        public ItemPedidoDTO(Long produtoId, Integer quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }
        
        // Getters e Setters
        public Long getProdutoId() {
            return produtoId;
        }
        
        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }
        
        public Integer getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}

