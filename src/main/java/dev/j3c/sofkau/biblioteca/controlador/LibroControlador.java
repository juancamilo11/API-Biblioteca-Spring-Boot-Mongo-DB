package dev.j3c.sofkau.biblioteca.controlador;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
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
        return new ResponseEntity(libroServicio.guardarLibro(libroDTO), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<LibroDTO> actualizarLibro(@RequestBody LibroDTO libroDTO) {
        if (libroDTO.getId() != null) {
            return new ResponseEntity(libroServicio.actualizarLibro(libroDTO), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarLibro(@PathVariable("id") String id) {
        try {
            libroServicio.eliminarLibro(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity consultarDisponibilidadLibro(@PathVariable("id") String id) {
        return new ResponseEntity(libroServicio.consultarDisponibilidad(id), HttpStatus.OK);
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity prestarLibro(@PathVariable("id") String id){
        return  new ResponseEntity(libroServicio.prestarLibro(id), HttpStatus.OK);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity devolverLibro(@PathVariable("id") String id){
        return  new ResponseEntity(libroServicio.devolverLibro(id), HttpStatus.OK);
    }

    @GetMapping("/recomendar/categoria/{categoria}")
    public ResponseEntity recomendarPorCategoria(@PathVariable("categoria") String categoria) {
        return new ResponseEntity(libroServicio.getLibrosPorCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping("/recomendar/tipo/{tipo}")
    public ResponseEntity recomendarPorTipo(@PathVariable("tipo") String tipo) {
        return new ResponseEntity(libroServicio.getLibrosPorTipo(tipo), HttpStatus.OK);
    }
}
