package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Estado concreto que representa um pedido enviado.
 * Neste estado, nenhuma transição é permitida - estado final.
 */
public class EnviadoState implements PedidoState {
    
    @Override
    public boolean pagar(Pedido pedido) {
        // Transição não permitida: pedido já foi enviado
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " já foi enviado e não pode ser alterado.");
        return false;
    }
    
    @Override
    public boolean cancelar(Pedido pedido) {
        // Transição não permitida: pedido enviado não pode ser cancelado
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " já foi enviado e não pode ser cancelado.");
        return false;
    }
    
    @Override
    public boolean enviar(Pedido pedido) {
        // Transição não permitida: pedido já foi enviado
        System.out.println("Erro: O pedido " + pedido.getNumeroPedido() + 
                          " já foi enviado.");
        return false;
    }
    
    @Override
    public String getEstado() {
        return "ENVIADO";
    }
}

