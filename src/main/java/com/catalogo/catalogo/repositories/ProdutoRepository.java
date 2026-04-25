package com.catalogo.catalogo.repositories;

import com.catalogo.catalogo.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    List<ProdutoModel> findByNomeContainingIgnoreCase(String nome);

    List<ProdutoModel> findByCategoria_IdCategoria(Long idCategoria);

    List<ProdutoModel> findByNomeContainingIgnoreCaseAndCategoria_IdCategoria(String nome, Long idCategoria);

    boolean existsByNomeIgnoreCase(String nome);

    Optional<ProdutoModel> findByNomeIgnoreCase(String nome);
}
