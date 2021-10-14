package dev.j3c.sofkau.biblioteca.servicio;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.mapper.LibroMapper;
import dev.j3c.sofkau.biblioteca.modelo.Libro;
import dev.j3c.sofkau.biblioteca.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    private LibroMapper libroMapper = new LibroMapper();

    public LibroDTO getLibroPorId(String id) {
        Libro libro = libroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso con id " + id + " no encontrado."));
        return libroMapper.fromCollection(libro);
    }

    public List<LibroDTO> getLibros() {
        List<Libro> libroList = libroRepositorio.findAll();
        return libroMapper.fromCollectionList(libroList);
    }

    public LibroDTO guardarLibro(LibroDTO libroDTO) {
        Libro libro = libroMapper.fromDTO(libroDTO);
        return libroMapper.fromCollection(libroRepositorio.save(libro));
    }

    public LibroDTO actualizarLibro(LibroDTO libroDTO) {
        Libro libro = libroMapper.fromDTO(libroDTO);
        libroRepositorio.findById(libro.getId()).orElseThrow(() -> new RuntimeException("Recurso con id: " + libroDTO.getId() + " no encontrado."));
        return libroMapper.fromCollection(libroRepositorio.save(libro));
    }

    public void eliminarLibro(String id) {
        libroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso con id: " + id + " no encontrado."));
        libroRepositorio.deleteById(id);
    }

    private boolean isDisponible(String idLibro) {
        if(getLibroPorId(idLibro).getUnidadesDisponibles() > 0) {
            return true;
        }
        return false;
    }

    public String consultarDisponibilidad(String id) {
        LibroDTO libroDTO = getLibroPorId(id);
        if(isDisponible(id)) {
            return ("El libro con id " + id + " si se encuentra disponible, hay " + libroDTO.getUnidadesDisponibles() + " unidades disponibles");
        }
        return ("El libro con id " + id + " no se encuentra disponible, la fecha de último préstamo fue el " + libroDTO.getUnidadesDisponibles());
    }

    public String prestarLibro(String id) {
        LibroDTO libroDTO = getLibroPorId(id);
        if(libroDTO.getUnidadesDisponibles() == 1) {
            libroDTO.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles() - 1);
            libroDTO.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas() + 1);
            libroDTO.setFechaUltimoPrestamo(LocalDateTime.now());
            actualizarLibro(libroDTO);
            return ("Se ha prestado la última unidad del libro con " + id + " en la fecha " + getLibroPorId(id).getFechaUltimoPrestamo());
        }
        libroDTO.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles() - 1);
        libroDTO.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas() + 1);
        return ("Se ha prestado el libro con id " + id);
    }



}