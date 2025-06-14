package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Estado concreto que representa um pedido aguardando pagamento.
 * Neste estado, o pedido pode ser pago ou cancelado.
 */
public class AguardandoPagamentoState implements PedidoState {
    
    @Override
    public boolean pagar(Pedido pedido) {
        // Transição permitida: de AGUARDANDO_PAGAMENTO para PAGO
        pedido.setStatus("PAGO");
        System.out.println("Pedido " + pedido.getNumeroPedido() + " foi pago com sucesso!");
        return true;
    }
    
    @Override
    public boolean cancelar(Pedido pedido) {
        // Transição permitida: de AGUARDANDO_PAGAMENTO para CANCELADO
        pedido.setStatus("CANCELADO");
        System.out.println("Pedido " + pedido.getNumeroPedido() + " foi cancelado.");
        return true;
    }
    
    @Override
    public boolean enviar(Pedido pedido) {
        // Transição não permitida: não é possível enviar um pedido não pago
        System.out.println("Erro: Não é possível enviar o pedido " + pedido.getNumeroPedido() + 
                          " pois ainda não foi pago.");
        return false;
    }
    
    @Override
    public String getEstado() {
        return "AGUARDANDO_PAGAMENTO";
    }
}

