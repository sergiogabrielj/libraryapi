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
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    @Order(1)
    void saveLivro(){
        AutorEntity autorRecord = autorRepository.findAll().getLast();

        if(autorRecord != null) {
            LivroEntity livroRecord = new LivroEntity();
            livroRecord.setIsbn("12373-91248");
            livroRecord.setEdicao(2);
            livroRecord.setPreco(BigDecimal.valueOf(100));
            livroRecord.setDataPublicacao(LocalDate.of(2004,12,15));
            livroRecord.setTitulo("Poesias Romanas");
            livroRecord.setGenero(GeneroLivroEnum.POESIA);
            livroRecord.setIdAutor(autorRecord);
            livroRepository.save(livroRecord);
        }else{
            throw new RuntimeException("Autor não encontrado");
        }
    }

    @Test
    @Order(2)
    void saveCascadeLivro(){
        //Cria livro
        LivroEntity livroRecord = new LivroEntity();
        livroRecord.setIsbn("12373-91248");
        livroRecord.setEdicao(2);
        livroRecord.setPreco(BigDecimal.valueOf(100));
        livroRecord.setDataPublicacao(LocalDate.of(2004,12,15));
        livroRecord.setTitulo("Poesias Romanas");
        livroRecord.setGenero(GeneroLivroEnum.POESIA);

        //Cria autor
        AutorEntity autorRecord = new AutorEntity();
        autorRecord.setNome("Júnior");
        autorRecord.setNacionalidade("Itália");
        autorRecord.setDataNascimento(LocalDate.of(1996,05,15));
        //Autor será salvo em cascada;
        //configurado no ManyToOne da Livro Entity

        livroRecord.setIdAutor(autorRecord);
        livroRepository.save(livroRecord);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("b30d5d01-3b1a-49e5-a47c-75743edcca3a");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("6151d8a8-187e-4dd4-803f-d7c52d193ee4");
        AutorEntity newAutor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setIdAutor(newAutor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletarCascade(){
        //configurado no ManyToOne da Livro Entity
        //Verificar se esta habilitado
        UUID id = UUID.fromString("b30d5d01-3b1a-49e5-a47c-75743edcca3a");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional(readOnly = true)
    void BuscarLivroTest(){
        LivroEntity livro = livroRepository.findAll().getLast();
        System.out.println(livro.getTitulo());
        System.out.println(livro.getIdAutor().getNome());
    }

}
