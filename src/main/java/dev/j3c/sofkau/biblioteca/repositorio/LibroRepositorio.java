package dev.j3c.sofkau.biblioteca.repositorio;

import dev.j3c.sofkau.biblioteca.modelo.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends MongoRepository<Libro, String> {

}
