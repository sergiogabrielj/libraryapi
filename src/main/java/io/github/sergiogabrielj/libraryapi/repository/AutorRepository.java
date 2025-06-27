package io.github.sergiogabrielj.libraryapi.repository;

import io.github.sergiogabrielj.libraryapi.model.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorEntity, UUID> {

}
