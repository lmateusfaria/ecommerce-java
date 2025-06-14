package com.ecommerce.system.config;

import com.ecommerce.system.domain.Cliente;
import com.ecommerce.system.domain.Produto;
import com.ecommerce.system.repository.ClienteRepository;
import com.ecommerce.system.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Classe para inicializar dados de teste no banco de dados.
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Criar clientes de teste
        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente("João Silva", "joao@email.com", "(11) 99999-9999", "Rua A, 123");
            Cliente cliente2 = new Cliente("Maria Santos", "maria@email.com", "(11) 88888-8888", "Rua B, 456");
            Cliente cliente3 = new Cliente("Pedro Oliveira", "pedro@email.com", "(11) 77777-7777", "Rua C, 789");
            
            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
        }
        
        // Criar produtos de teste
        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto("Notebook Dell", "Notebook Dell Inspiron 15", new BigDecimal("2500.00"), 10);
            Produto produto2 = new Produto("Mouse Logitech", "Mouse sem fio Logitech MX Master", new BigDecimal("350.00"), 25);
            Produto produto3 = new Produto("Teclado Mecânico", "Teclado mecânico RGB", new BigDecimal("450.00"), 15);
            Produto produto4 = new Produto("Monitor 24\"", "Monitor LED 24 polegadas Full HD", new BigDecimal("800.00"), 8);
            Produto produto5 = new Produto("Smartphone Samsung", "Samsung Galaxy S23", new BigDecimal("3200.00"), 12);
            
            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
            produtoRepository.save(produto4);
            produtoRepository.save(produto5);
        }
    }
}

