package dev.j3c.sofkau.biblioteca.mapper;


import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.modelo.Libro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LibroMapper {

    public Libro fromDTO(LibroDTO libroDTO) {
        Libro libro = new Libro();
        libro.setId(libroDTO.getId());
        libro.setNombre(libroDTO.getNombre());
        libro.setTipo(libroDTO.getTipo());
        libro.setCategoria(libroDTO.getCategoria());
        libro.setUnidadesDisponibles(libroDTO.getUnidadesDisponibles());
        libro.setUnidadesPrestadas(libroDTO.getUnidadesPrestadas());
        libro.setFechaUltimoPrestamo(libroDTO.getFechaUltimoPrestamo());
        return libro;
    }

    public LibroDTO fromCollection(Libro libro) {
        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(libro.getId());
        libroDTO.setNombre(libro.getNombre());
        libroDTO.setTipo(libro.getTipo());
        libroDTO.setCategoria(libro.getCategoria());
        libroDTO.setUnidadesDisponibles(libro.getUnidadesDisponibles());
        libroDTO.setUnidadesPrestadas(libro.getUnidadesPrestadas());
        libroDTO.setFechaUltimoPrestamo(libro.getFechaUltimoPrestamo());
        return libroDTO;
    }

    public List<LibroDTO> fromCollectionList(List<Libro> libroList) {
        if (libroList == null) {
            return null;
        }
        List<LibroDTO> libroDTOList = new ArrayList<>();
        Iterator iterator = libroList.iterator();
        while(iterator.hasNext()) {
            Libro libro = (Libro) iterator.next();
            libroDTOList.add(fromCollection(libro));
        }
        return libroDTOList;
    }

}
