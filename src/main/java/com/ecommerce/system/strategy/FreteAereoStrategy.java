package com.ecommerce.system.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estratégia concreta para cálculo de frete aéreo (avião).
 * O frete aéreo custa 10% do valor do pedido.
 */
public class FreteAereoStrategy implements FreteStrategy {
    
    private static final BigDecimal PERCENTUAL_FRETE = new BigDecimal("0.10"); // 10%
    
    @Override
    public BigDecimal calcularFrete(BigDecimal valorPedido) {
        if (valorPedido == null || valorPedido.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pedido deve ser maior que zero");
        }
        
        return valorPedido.multiply(PERCENTUAL_FRETE).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String getTipoFrete() {
        return "AEREO";
    }
    
    @Override
    public String getDescricao() {
        return "Frete Aéreo (Avião) - 10% do valor do pedido";
    }
}

