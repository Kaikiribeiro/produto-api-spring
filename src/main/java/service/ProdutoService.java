package com.exemplo.produto.service;

import com.exemplo.produto.model.Produto;
import com.exemplo.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Método para buscar todos os produtos
    public List<Produto> findAll() {
        return repository.findAll();
    }

    // Método para buscar um produto pelo ID
    public Optional<Produto> findById(Long id) {
        return repository.findById(id);
    }

    // Método para salvar um novo produto
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    // Método para deletar um produto pelo ID
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
