package dev.j3c.sofkau.biblioteca.servicio;

import dev.j3c.sofkau.biblioteca.mapper.LibroMapper;
import dev.j3c.sofkau.biblioteca.modelo.Libro;
import dev.j3c.sofkau.biblioteca.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    private LibroMapper libroMapper = new LibroMapper();

    public Object getLibroPorId(String id) {
        Libro libro = libroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso con id " + id + " no encontrado."));
        return libroMapper.fromCollection(libro);
    }
}