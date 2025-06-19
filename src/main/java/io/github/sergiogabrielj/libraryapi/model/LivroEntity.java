package io.github.sergiogabrielj.libraryapi.model;

import io.github.sergiogabrielj.libraryapi.enums.GeneroLivroEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "livro")
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String isbn;

    @Column(length = 150,nullable = false)
    private String titulo;

    private LocalDate dataPublicacao;

    private Integer edicao;

    @Enumerated(EnumType.STRING)
    @Column(length = 30,nullable = false)
    private GeneroLivroEnum genero;

    @Column(precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private AutorEntity idAutor;
}
