package io.github.sergiogabrielj.libraryapi.repository;

import io.github.sergiogabrielj.libraryapi.model.AutorEntity;
import io.github.sergiogabrielj.libraryapi.model.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<LivroEntity, UUID> {
    // Query Method
    // select * from livro where id_autor = id
    List<LivroEntity> findByIdAutor(AutorEntity autor);

    // select * from livro where titulo = titulo
    List<LivroEntity> findByTitulo(String titulo);

    // select * from livro where isbn = ?
    List<LivroEntity> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<LivroEntity> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<LivroEntity> findByTituloOrIsbn(String titulo, String isbn);
}
