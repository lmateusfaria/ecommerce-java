package com.ecommerce.system.strategy;

import java.math.BigDecimal;

/**
 * Interface que define o padrão Strategy para cálculo de frete.
 * Diferentes estratégias de frete implementam esta interface.
 */
public interface FreteStrategy {
    
    /**
     * Calcula o valor do frete baseado no valor total do pedido.
     * @param valorPedido O valor total do pedido
     * @return O valor do frete calculado
     */
    BigDecimal calcularFrete(BigDecimal valorPedido);
    
    /**
     * Retorna o tipo de frete.
     * @return String representando o tipo de frete
     */
    String getTipoFrete();
    
    /**
     * Retorna a descrição do método de frete.
     * @return String com a descrição do frete
     */
    String getDescricao();
}

