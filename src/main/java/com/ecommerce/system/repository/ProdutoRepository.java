package com.ecommerce.system.repository;

import com.ecommerce.system.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    /**
     * Busca produtos por nome (busca parcial, case insensitive).
     * @param nome O nome ou parte do nome do produto
     * @return Lista de produtos que contêm o nome especificado
     */
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca produtos com estoque disponível.
     * @return Lista de produtos com estoque maior que zero
     */
    @Query("SELECT p FROM Produto p WHERE p.estoque > 0")
    List<Produto> findProdutosComEstoque();
}

