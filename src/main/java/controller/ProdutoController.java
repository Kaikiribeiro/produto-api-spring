package com.exemplo.produto.controller;

import com.exemplo.produto.model.Produto;
import com.exemplo.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para buscar todos os produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.findAll();
    }

    // Endpoint para buscar um produto pelo código
    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> getProdutoByCodigo(@PathVariable("codigo") Long codigo) {  // Alterado para Long
        return produtoService.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo produto
    @PostMapping
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoService.save(produto);
    }

    // Endpoint para atualizar um produto existente
    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> updateProduto(@PathVariable("codigo") Long codigo, @RequestBody Produto produto) {  // Alterado para Long
        if (!produtoService.findById(codigo).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        produto.setCodigo(codigo);
        return ResponseEntity.ok(produtoService.save(produto));
    }

    // Endpoint para deletar um produto
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deleteProduto(@PathVariable("codigo") Long codigo) {  // Alterado para Long
        if (!produtoService.findById(codigo).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        produtoService.delete(codigo);
        return ResponseEntity.noContent().build();
    }
}
