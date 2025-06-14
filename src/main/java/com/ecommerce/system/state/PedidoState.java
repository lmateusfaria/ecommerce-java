package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Interface que define o padrão State para o gerenciamento de estados do pedido.
 * Cada estado concreto implementa esta interface definindo as transições permitidas.
 */
public interface PedidoState {
    
    /**
     * Tenta processar o pagamento do pedido.
     * @param pedido O pedido a ser pago
     * @return true se o pagamento foi processado com sucesso, false caso contrário
     */
    boolean pagar(Pedido pedido);
    
    /**
     * Tenta cancelar o pedido.
     * @param pedido O pedido a ser cancelado
     * @return true se o cancelamento foi processado com sucesso, false caso contrário
     */
    boolean cancelar(Pedido pedido);
    
    /**
     * Tenta enviar o pedido.
     * @param pedido O pedido a ser enviado
     * @return true se o envio foi processado com sucesso, false caso contrário
     */
    boolean enviar(Pedido pedido);
    
    /**
     * Retorna o nome do estado atual.
     * @return String representando o estado
     */
    String getEstado();
}

