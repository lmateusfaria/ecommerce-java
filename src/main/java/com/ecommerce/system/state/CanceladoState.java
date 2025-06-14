package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Estado concreto que representa um pedido cancelado.
 * Neste estado, nenhuma transição é permitida - estado final.
 */
public class CanceladoState implements PedidoState {
    
    @Override
    public boolean pagar(Pedido pedido) {
        // Transição não permitida: pedido cancelado não pode ser pago
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " foi cancelado e não pode ser pago.");
        return false;
    }
    
    @Override
    public boolean cancelar(Pedido pedido) {
        // Transição não permitida: pedido já está cancelado
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " já foi cancelado.");
        return false;
    }
    
    @Override
    public boolean enviar(Pedido pedido) {
        // Transição não permitida: pedido cancelado não pode ser enviado
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " foi cancelado e não pode ser enviado.");
        return false;
    }
    
    @Override
    public String getEstado() {
        return "CANCELADO";
    }
}

