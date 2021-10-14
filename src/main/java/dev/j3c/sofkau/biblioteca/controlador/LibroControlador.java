package dev.j3c.sofkau.biblioteca.controlador;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biblioteca")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> findbyId(@PathVariable("id") String id) {
        return new ResponseEntity(libroServicio.getLibroPorId(id), HttpStatus.OK);
    }

}
