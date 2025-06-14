package com.ecommerce.system.factory;

import com.ecommerce.system.strategy.FreteStrategy;
import com.ecommerce.system.strategy.FreteTerrestreStrategy;
import com.ecommerce.system.strategy.FreteAereoStrategy;

/**
 * Factory Method para criação de estratégias de frete.
 * Este padrão permite a criação de diferentes tipos de frete de forma centralizada
 * e facilita a adição de novos tipos de frete no futuro.
 */
public class FreteStrategyFactory {
    
    /**
     * Cria uma estratégia de frete baseada no tipo especificado.
     * @param tipoFrete O tipo de frete desejado
     * @return A estratégia de frete correspondente
     * @throws IllegalArgumentException se o tipo de frete não for suportado
     */
    public static FreteStrategy criarFreteStrategy(String tipoFrete) {
        if (tipoFrete == null || tipoFrete.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de frete não pode ser nulo ou vazio");
        }
        
        switch (tipoFrete.toUpperCase()) {
            case "TERRESTRE":
                return new FreteTerrestreStrategy();
            case "AEREO":
                return new FreteAereoStrategy();
            default:
                throw new IllegalArgumentException("Tipo de frete não suportado: " + tipoFrete);
        }
    }
    
    /**
     * Retorna todos os tipos de frete disponíveis.
     * @return Array com os tipos de frete suportados
     */
    public static String[] getTiposFreteDisponiveis() {
        return new String[]{"TERRESTRE", "AEREO"};
    }
    
    /**
     * Verifica se um tipo de frete é suportado.
     * @param tipoFrete O tipo de frete a ser verificado
     * @return true se o tipo de frete é suportado, false caso contrário
     */
    public static boolean isTipoFreteSuportado(String tipoFrete) {
        if (tipoFrete == null || tipoFrete.trim().isEmpty()) {
            return false;
        }
        
        String[] tiposDisponiveis = getTiposFreteDisponiveis();
        for (String tipo : tiposDisponiveis) {
            if (tipo.equalsIgnoreCase(tipoFrete.trim())) {
                return true;
            }
        }
        return false;
    }
}

