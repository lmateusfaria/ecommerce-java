package com.ecommerce.system.repository;

import com.ecommerce.system.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    /**
     * Busca pedidos por número do pedido.
     * @param numeroPedido O número do pedido
     * @return Optional contendo o pedido se encontrado
     */
    Optional<Pedido> findByNumeroPedido(String numeroPedido);
    
    /**
     * Busca pedidos por cliente.
     * @param clienteId O ID do cliente
     * @return Lista de pedidos do cliente
     */
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId ORDER BY p.dataCriacao DESC")
    List<Pedido> findByClienteId(@Param("clienteId") Long clienteId);
    
    /**
     * Busca pedidos por status.
     * @param status O status do pedido
     * @return Lista de pedidos com o status especificado
     */
    List<Pedido> findByStatusOrderByDataCriacaoDesc(String status);
    
    /**
     * Busca pedidos por cliente e status.
     * @param clienteId O ID do cliente
     * @param status O status do pedido
     * @return Lista de pedidos do cliente com o status especificado
     */
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId AND p.status = :status ORDER BY p.dataCriacao DESC")
    List<Pedido> findByClienteIdAndStatus(@Param("clienteId") Long clienteId, @Param("status") String status);
}

