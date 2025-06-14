package com.ecommerce.system.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estratégia concreta para cálculo de frete terrestre (caminhão).
 * O frete terrestre custa 5% do valor do pedido.
 */
public class FreteTerrestreStrategy implements FreteStrategy {
    
    private static final BigDecimal PERCENTUAL_FRETE = new BigDecimal("0.05"); // 5%
    
    @Override
    public BigDecimal calcularFrete(BigDecimal valorPedido) {
        if (valorPedido == null || valorPedido.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pedido deve ser maior que zero");
        }
        
        return valorPedido.multiply(PERCENTUAL_FRETE).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String getTipoFrete() {
        return "TERRESTRE";
    }
    
    @Override
    public String getDescricao() {
        return "Frete Terrestre (Caminhão) - 5% do valor do pedido";
    }
}

