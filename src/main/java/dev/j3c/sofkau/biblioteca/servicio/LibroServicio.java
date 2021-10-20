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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    private LibroMapper libroMapper = new LibroMapper();

    public LibroDTO getLibroPorId(String id) {
        Objects.requireNonNull(id);
        Libro libro = libroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso con id " + id + " no encontrado."));
        return libroMapper.fromCollection(libro);
    }

    public List<LibroDTO> getLibros() {
        List<Libro> libroList = libroRepositorio.findAll();
        return libroMapper.fromCollectionList(libroList);
    }

    public LibroDTO guardarLibro(LibroDTO libroDTO) {
        if(datosValidos(libroDTO)) {
            Libro libro = libroMapper.fromDTO(libroDTO);
            return libroMapper.fromCollection(libroRepositorio.save(libro));
        }
        throw new IllegalArgumentException("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente");
    }

    private boolean datosValidos(LibroDTO libroDTO) {
        if(libroDTO.getNombre().trim().isBlank()) {
            return false;
        }
        if(libroDTO.getCategoria().trim().isBlank()) {
            return false;
        }
        if(libroDTO.getTipo().trim().isBlank()) {
            return false;
        }
        if(libroDTO.getUnidadesDisponibles() <= 0) {
            return false;
        }
        return true;
    }

    public LibroDTO actualizarLibro(LibroDTO libroDTO) {
        Libro libro = libroMapper.fromDTO(libroDTO);
        libroRepositorio.findById(libro.getId()).orElseThrow(() -> new RuntimeException("Recurso con id: " + libroDTO.getId() + " no encontrado."));
        if(libroDTO.getUnidadesDisponibles() < 0 || libroDTO.getUnidadesPrestadas() < 0) {
            throw  new RuntimeException("Cantidades de numero de unidades disponibles o unidades prestadas invalidas.");
        }
        return libroMapper.fromCollection(libroRepositorio.save(libro));
    }

    public void eliminarLibro(String id) {
        Objects.requireNonNull(id);
        libroRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso con id: " + id + " no encontrado."));
        libroRepositorio.deleteById(id);
    }

    private boolean isDisponible(String id) {
        Objects.requireNonNull(id);
        if(getLibroPorId(id).getUnidadesDisponibles() > 0) {
            return true;
        }
        return false;
    }

    public String consultarDisponibilidad(String id) {
        Objects.requireNonNull(id);
        LibroDTO libroDTO = getLibroPorId(id);
        if(isDisponible(id)) {
            return ("El libro con id " + id + " si se encuentra disponible, hay " + libroDTO.getUnidadesDisponibles() + " unidades disponibles");
        }
        return ("El libro con id " + id + " no se encuentra disponible, la fecha de último préstamo fue el " + libroDTO.getFechaUltimoPrestamo());
    }

    public String prestarLibro(String id) {
        Objects.requireNonNull(id);
        LibroDTO libroDTO = getLibroPorId(id);
        if(libroDTO.getUnidadesDisponibles() == 1) {
            libroDTO.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles() - 1);
            libroDTO.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas() + 1);
            libroDTO.setFechaUltimoPrestamo(LocalDate.now());
            actualizarLibro(libroDTO);
            return ("Se ha prestado la última unidad del libro con " + id + " en la fecha " + getLibroPorId(id).getFechaUltimoPrestamo());
        }
        if(!isDisponible(id)) {
            return ("No quedan unidades disponibles del libro con id " + id + " puede esperar a que alguien devuelva una unidad.");
        }
        libroDTO.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles() - 1);
        libroDTO.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas() + 1);
        actualizarLibro(libroDTO);
        return ("Se ha prestado el libro con id " + id +  ", en total se han prestado " + libroDTO.getUnidadesPrestadas() +
                        " unidades y quedan aún disponibles " + libroDTO.getUnidadesDisponibles() + " unidades.");
    }

    public String devolverLibro(String id) {
        Objects.requireNonNull(id);
        LibroDTO libroDTO = getLibroPorId(id);
        if(libroDTO.getUnidadesPrestadas() == 0) {
            return ("Todos los libros con " + id + " han sido devueltos.");
        }
        if(libroDTO.getUnidadesDisponibles() == 1) {
            libroDTO.setFechaUltimoPrestamo(null);
        }
        libroDTO.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles() + 1);
        libroDTO.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas() - 1);
        actualizarLibro(libroDTO);
        return ("Se ha devuelto un libro con id " + id + ", en total se han prestado " + libroDTO.getUnidadesPrestadas() +
                " unidades y quedan aún disponibles " + libroDTO.getUnidadesDisponibles() + " unidades.");
    }

    public List<LibroDTO> getLibrosPorCategoria(String categoria) {
        Objects.requireNonNull(categoria);
        List<Libro> libroList = libroRepositorio.findByCategoria(categoria);
        return libroMapper.fromCollectionList(libroList);
    }

    public List<LibroDTO> getLibrosPorTipo(String tipo) {
        Objects.requireNonNull(tipo);
        List<Libro> libroList = libroRepositorio.findByCategoria(tipo);
        return libroMapper.fromCollectionList(libroList);
    }
}