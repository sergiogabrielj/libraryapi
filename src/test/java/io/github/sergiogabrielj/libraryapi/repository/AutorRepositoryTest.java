package io.github.sergiogabrielj.libraryapi.repository;

import io.github.sergiogabrielj.libraryapi.enums.GeneroLivroEnum;
import io.github.sergiogabrielj.libraryapi.model.AutorEntity;
import io.github.sergiogabrielj.libraryapi.model.LivroEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AutorRepositoryTest {
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    @Order(1)
    void saveAutor(){
        AutorEntity autorEntity = new AutorEntity();
        autorEntity.setNome("Sérgio");
        autorEntity.setNacionalidade("Brasil");
        autorEntity.setDataNascimento(LocalDate.of(1995,04,16));
        autorRepository.save(autorEntity);
    }

    @Test
    @Order(2)
    void updateAutor(){
        AutorEntity exRecord = autorRepository.findAll().getLast();
        if (exRecord != null){
            exRecord.setNacionalidade("Brasileiro");
            autorRepository.save(exRecord);
        }else{
            throw new RuntimeException("Autor não encontrado");
        }
    }

    @Test
    @Order(3)
    void listAutor(){
        List<AutorEntity> listaAutor = autorRepository.findAll();
        listaAutor.forEach(System.out::println);
    }

    @Test
    @Order(4)
    void deleteAutor(){
        List<AutorEntity> autorRecords = autorRepository.findAll();
        autorRepository.deleteAll(autorRecords);
    }

    @Test
    //@Transactional
    //Não utilizar @Transactional se AutorEntity @OneToMany(mappedBy = "idAutor") cascade não for LAZY
    void salvarAutorComLivrosTest(){
        AutorEntity autor = new AutorEntity();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        LivroEntity livro = new LivroEntity();
        livro.setIsbn("20847-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivroEnum.FANTASIA);
        livro.setTitulo("O roubo da casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 2));
        livro.setIdAutor(autor);

        LivroEntity livro2 = new LivroEntity();
        livro2.setIsbn("99999-84874");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivroEnum.FANTASIA);
        livro2.setTitulo("O roubo da casa assombrada 2");
        livro2.setDataPublicacao(LocalDate.of(2005, 1, 2));
        livro2.setIdAutor(autor);

        autor.setLivro(new ArrayList<>());
        autor.getLivro().add(livro);
        autor.getLivro().add(livro2);

        autorRepository.save(autor);

        //livroRepository.saveAll(autor.getLivro());
        //Se AutorEntity @OneToMany cascade = CascadeType.ALL, não é necessário livroRepository.saveAll
    }

    /*@Test
    void listarLivrosAutor(){
        var id = UUID.fromString("27c51581-8dfb-4b78-8c52-3965f6496f01");
        var autor = autorRepository.findById(id).get();

        // buscar os livros do autor

        List<LivroEntity> livrosLista = livroRepository.findByIdAutor(autor);
        autor.setLivro(livrosLista);

        autor.getLivro().forEach(System.out::println);
    }*/
}
