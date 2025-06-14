package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Estado concreto que representa um pedido pago.
 * Neste estado, o pedido pode ser enviado ou cancelado.
 */
public class PagoState implements PedidoState {
    
    @Override
    public boolean pagar(Pedido pedido) {
        // Transição não permitida: pedido já está pago
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " já foi pago e não pode ser pago novamente.");
        return false;
    }
    
    @Override
    public boolean cancelar(Pedido pedido) {
        // Transição permitida: de PAGO para CANCELADO
        pedido.setStatus("CANCELADO");
        System.out.println("Pedido " + pedido.getNumeroPedido() + " foi cancelado.");
        return true;
    }
    
    @Override
    public boolean enviar(Pedido pedido) {
        // Transição permitida: de PAGO para ENVIADO
        pedido.setStatus("ENVIADO");
        System.out.println("Pedido " + pedido.getNumeroPedido() + " foi enviado!");
        return true;
    }
    
    @Override
    public String getEstado() {
        return "PAGO";
    }
}

