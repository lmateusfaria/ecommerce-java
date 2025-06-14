package com.ecommerce.system.repository;

import com.ecommerce.system.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca cliente por email.
     * @param email O email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Verifica se existe um cliente com o email especificado.
     * @param email O email a ser verificado
     * @return true se existe um cliente com o email, false caso contrário
     */
    boolean existsByEmail(String email);
}

