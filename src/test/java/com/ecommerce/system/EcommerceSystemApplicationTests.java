package com.ecommerce.system;

import com.ecommerce.system.factory.FreteStrategyFactory;
import com.ecommerce.system.strategy.FreteCalculator;
import com.ecommerce.system.strategy.FreteStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EcommerceSystemApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void testFreteTerrestre() {
		FreteStrategy strategy = FreteStrategyFactory.criarFreteStrategy("TERRESTRE");
		FreteCalculator calculator = new FreteCalculator(strategy);
		
		BigDecimal valorPedido = new BigDecimal("1000.00");
		BigDecimal valorFrete = calculator.calcularFrete(valorPedido);
		
		assertEquals(new BigDecimal("50.00"), valorFrete);
		assertEquals("TERRESTRE", calculator.getTipoFrete());
	}
	
	@Test
	void testFreteAereo() {
		FreteStrategy strategy = FreteStrategyFactory.criarFreteStrategy("AEREO");
		FreteCalculator calculator = new FreteCalculator(strategy);
		
		BigDecimal valorPedido = new BigDecimal("1000.00");
		BigDecimal valorFrete = calculator.calcularFrete(valorPedido);
		
		assertEquals(new BigDecimal("100.00"), valorFrete);
		assertEquals("AEREO", calculator.getTipoFrete());
	}
	
	@Test
	void testFreteStrategyFactory() {
		String[] tiposDisponiveis = FreteStrategyFactory.getTiposFreteDisponiveis();
		assertEquals(2, tiposDisponiveis.length);
		
		assertTrue(FreteStrategyFactory.isTipoFreteSuportado("TERRESTRE"));
		assertTrue(FreteStrategyFactory.isTipoFreteSuportado("AEREO"));
		assertFalse(FreteStrategyFactory.isTipoFreteSuportado("MARITIMO"));
	}

}

