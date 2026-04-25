package com.catalogo.catalogo.services;

import com.catalogo.catalogo.models.ProdutoModel;
import com.catalogo.catalogo.repositories.CategoriaRepository;
import com.catalogo.catalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAll();
    }

    public List<ProdutoModel> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
    
    public List<ProdutoModel> listarComFiltros(String busca, Long idCategoria) {
        boolean temBusca = busca != null && !busca.isBlank();
        boolean temCategoria = idCategoria != null;

        if (temBusca && temCategoria) {
            return repository.findByNomeContainingIgnoreCaseAndCategoria_IdCategoria(busca.trim(), idCategoria);
        }
        if (temBusca) {
            return repository.findByNomeContainingIgnoreCase(busca.trim());
        }
        if (temCategoria) {
            return repository.findByCategoria_IdCategoria(idCategoria);
        }
        return repository.findAll();
    }

    public ProdutoModel buscarPorId(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    @Transactional
    public void salvar(ProdutoModel produto) {
        var existente = repository.findByNomeIgnoreCase(produto.getNome());

        if (existente.isPresent() &&
                !existente.get().getIdProduto().equals(produto.getIdProduto())) {

            throw new IllegalArgumentException("Já existe um produto com esse nome.");
        }

        if (produto.getCategoria() == null || produto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Selecione uma categoria.");
        }
        var idCategoria = produto.getCategoria().getIdCategoria();
        var categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + idCategoria));
        produto.setCategoria(categoria);

        repository.save(produto);
    }

    @Transactional
    public void excluir(long id) {
        repository.deleteById(id);
    }
}
