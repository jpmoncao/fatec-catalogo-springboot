package com.catalogo.catalogo.services;

import com.catalogo.catalogo.models.CategoriaModel;
import com.catalogo.catalogo.repositories.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaModel> listarTodos() {
        return repository.findAll();
    }

    public List<CategoriaModel> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public CategoriaModel buscarPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
    }

    @Transactional
    public void salvar(CategoriaModel categoria) {
        var existente = repository.findByNomeIgnoreCase(categoria.getNome());

        if (existente.isPresent()
                && !existente.get().getIdCategoria().equals(categoria.getIdCategoria())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome.");
        }

        repository.save(categoria);
    }

    @Transactional
    public void excluir(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Não é possível excluir: existem produtos vinculados a esta categoria.");
        }
    }
}
