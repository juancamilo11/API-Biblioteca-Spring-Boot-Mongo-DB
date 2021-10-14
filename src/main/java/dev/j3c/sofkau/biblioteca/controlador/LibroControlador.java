package dev.j3c.sofkau.biblioteca.controlador;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.modelo.Libro;
import dev.j3c.sofkau.biblioteca.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> encontrarLibroPorId(@PathVariable("id") String id) {
        return new ResponseEntity(libroServicio.getLibroPorId(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<LibroDTO>> encontrarTodosLosLibros() {
        return new ResponseEntity(libroServicio.getLibros(), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<LibroDTO> crearNuevoLibro(@RequestBody LibroDTO libroDTO) {
        return new ResponseEntity(libroServicio.guardar(libroDTO), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<LibroDTO> actualizarLibro(@RequestBody LibroDTO libroDTO) {
        if (libroDTO.getId() != null) {
            return new ResponseEntity(libroServicio.actualizar(libroDTO), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }



}
