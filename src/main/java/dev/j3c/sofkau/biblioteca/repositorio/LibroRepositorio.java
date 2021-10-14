package dev.j3c.sofkau.biblioteca.repositorio;

import dev.j3c.sofkau.biblioteca.modelo.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepositorio extends MongoRepository<Libro, String> {
    List<Libro> findByTipo(final String tipo);
    List<Libro> findByCategoria(final String categoria);
}
