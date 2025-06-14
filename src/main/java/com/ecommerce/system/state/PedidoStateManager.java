package com.ecommerce.system.state;

import com.ecommerce.system.domain.Pedido;

/**
 * Context do padrão State que gerencia as transições de estado do pedido.
 * Esta classe encapsula a lógica de mudança de estados e delega as operações
 * para o estado atual do pedido.
 */
public class PedidoStateManager {
    
    /**
     * Obtém o estado atual do pedido baseado no seu status.
     * @param pedido O pedido para obter o estado
     * @return O estado correspondente ao status do pedido
     */
    public static PedidoState getState(Pedido pedido) {
        switch (pedido.getStatus()) {
            case "AGUARDANDO_PAGAMENTO":
                return new AguardandoPagamentoState();
            case "PAGO":
                return new PagoState();
            case "ENVIADO":
                return new EnviadoState();
            case "CANCELADO":
                return new CanceladoState();
            default:
                throw new IllegalStateException("Estado inválido: " + pedido.getStatus());
        }
    }
    
    /**
     * Processa o pagamento do pedido usando o padrão State.
     * @param pedido O pedido a ser pago
     * @return true se o pagamento foi processado com sucesso
     */
    public static boolean pagar(Pedido pedido) {
        PedidoState state = getState(pedido);
        return state.pagar(pedido);
    }
    
    /**
     * Cancela o pedido usando o padrão State.
     * @param pedido O pedido a ser cancelado
     * @return true se o cancelamento foi processado com sucesso
     */
    public static boolean cancelar(Pedido pedido) {
        PedidoState state = getState(pedido);
        return state.cancelar(pedido);
    }
    
    /**
     * Envia o pedido usando o padrão State.
     * @param pedido O pedido a ser enviado
     * @return true se o envio foi processado com sucesso
     */
    public static boolean enviar(Pedido pedido) {
        PedidoState state = getState(pedido);
        return state.enviar(pedido);
    }
}

