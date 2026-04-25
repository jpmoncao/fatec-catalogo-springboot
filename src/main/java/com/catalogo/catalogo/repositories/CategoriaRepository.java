package com.catalogo.catalogo.repositories;

import com.catalogo.catalogo.models.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
    List<CategoriaModel> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    Optional<CategoriaModel> findByNomeIgnoreCase(String nome);
}
