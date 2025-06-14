package com.ecommerce.system.strategy;

import java.math.BigDecimal;

/**
 * Context do padrão Strategy que utiliza diferentes estratégias de frete.
 * Esta classe encapsula o uso das estratégias de cálculo de frete.
 */
public class FreteCalculator {
    
    private FreteStrategy strategy;
    
    /**
     * Construtor que recebe a estratégia de frete a ser utilizada.
     * @param strategy A estratégia de frete
     */
    public FreteCalculator(FreteStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Define uma nova estratégia de frete.
     * @param strategy A nova estratégia de frete
     */
    public void setStrategy(FreteStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Calcula o frete usando a estratégia atual.
     * @param valorPedido O valor total do pedido
     * @return O valor do frete calculado
     */
    public BigDecimal calcularFrete(BigDecimal valorPedido) {
        if (strategy == null) {
            throw new IllegalStateException("Estratégia de frete não foi definida");
        }
        return strategy.calcularFrete(valorPedido);
    }
    
    /**
     * Retorna o tipo de frete da estratégia atual.
     * @return String representando o tipo de frete
     */
    public String getTipoFrete() {
        if (strategy == null) {
            throw new IllegalStateException("Estratégia de frete não foi definida");
        }
        return strategy.getTipoFrete();
    }
    
    /**
     * Retorna a descrição da estratégia atual.
     * @return String com a descrição do frete
     */
    public String getDescricao() {
        if (strategy == null) {
            throw new IllegalStateException("Estratégia de frete não foi definida");
        }
        return strategy.getDescricao();
    }
}

