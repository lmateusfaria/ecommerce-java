package com.ecommerce.system.controller;

import com.ecommerce.system.dto.CriarPedidoDTO;
import com.ecommerce.system.dto.PedidoResponseDTO;
import com.ecommerce.system.factory.FreteStrategyFactory;
import com.ecommerce.system.service.PedidoService;
import com.ecommerce.system.strategy.FreteCalculator;
import com.ecommerce.system.strategy.FreteStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com os itens especificados")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody CriarPedidoDTO criarPedidoDTO) {
        try {
            PedidoResponseDTO pedido = pedidoService.criarPedido(criarPedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes de um pedido específico")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    @PutMapping("/{id}/pagar")
    @Operation(summary = "Pagar pedido", description = "Processa o pagamento de um pedido")
    public ResponseEntity<PedidoResponseDTO> pagarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.pagarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.cancelarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/enviar")
    @Operation(summary = "Enviar pedido", description = "Marca um pedido como enviado")
    public ResponseEntity<PedidoResponseDTO> enviarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.enviarPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/frete/calcular")
    @Operation(summary = "Calcular frete", description = "Calcula o valor do frete para um pedido")
    public ResponseEntity<Map<String, Object>> calcularFrete(
            @Parameter(description = "Valor do pedido") @RequestParam BigDecimal valorPedido,
            @Parameter(description = "Tipo de frete (TERRESTRE ou AEREO)") @RequestParam String tipoFrete) {
        try {
            if (!FreteStrategyFactory.isTipoFreteSuportado(tipoFrete)) {
                return ResponseEntity.badRequest().build();
            }
            
            FreteStrategy strategy = FreteStrategyFactory.criarFreteStrategy(tipoFrete);
            FreteCalculator calculator = new FreteCalculator(strategy);
            BigDecimal valorFrete = calculator.calcularFrete(valorPedido);
            
            Map<String, Object> response = new HashMap<>();
            response.put("valorPedido", valorPedido);
            response.put("tipoFrete", tipoFrete);
            response.put("valorFrete", valorFrete);
            response.put("descricao", strategy.getDescricao());
            response.put("valorTotal", valorPedido.add(valorFrete));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/frete/tipos")
    @Operation(summary = "Listar tipos de frete", description = "Retorna os tipos de frete disponíveis")
    public ResponseEntity<Map<String, Object>> listarTiposFrete() {
        String[] tipos = FreteStrategyFactory.getTiposFreteDisponiveis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("tiposDisponiveis", tipos);
        
        Map<String, String> descricoes = new HashMap<>();
        for (String tipo : tipos) {
            FreteStrategy strategy = FreteStrategyFactory.criarFreteStrategy(tipo);
            descricoes.put(tipo, strategy.getDescricao());
        }
        response.put("descricoes", descricoes);
        
        return ResponseEntity.ok(response);
    }
}

