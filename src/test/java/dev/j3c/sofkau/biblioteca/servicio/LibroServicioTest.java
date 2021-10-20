package dev.j3c.sofkau.biblioteca.servicio;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.mapper.LibroMapper;
import dev.j3c.sofkau.biblioteca.modelo.Libro;
import dev.j3c.sofkau.biblioteca.repositorio.LibroRepositorio;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibroServicioTest {

    @MockBean
    private LibroRepositorio libroRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private LibroMapper libroMapper;

    @Test
    @DisplayName("Testing creación de un nuevo libro - Exito") //guardarLibro
    void testAgregarUnNuevoLibro() {

        // Arrange
        Libro libro = new Libro("1",
                "Nombre libro #1",
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        var resultado = libroServicio.guardarLibro(libroMapper.fromCollection(libro));

        // Assert
        Assertions.assertEquals("1", resultado.getId());
        Assertions.assertEquals("Nombre libro #1", resultado.getNombre());
        Assertions.assertEquals("Tipo libro #1", resultado.getTipo());
        Assertions.assertEquals("Categoria libro #1", resultado.getCategoria());
        Assertions.assertEquals(20, resultado.getUnidadesDisponibles());
        Assertions.assertEquals(0, resultado.getUnidadesPrestadas());
        Assertions.assertNull( resultado.getFechaUltimoPrestamo());
    }

    @Test
    @DisplayName("Testing creación de un nuevo libro - Fail") //guardarLibro
    void testAgregarUnNuevoLibroFail1() {

        // Arrange
        Libro libro = new Libro("1",
                "",     //Los datos tipo String no pueden estar vacíos
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        Throwable excepcion = assertThrows(IllegalArgumentException.class, () -> {
            var resultado = libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        });
        // Assert
        assertEquals("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente", excepcion.getMessage());
    }

    @Test
    @DisplayName("Testing creación de un nuevo libro - Fail") //guardarLibro
    void testAgregarUnNuevoLibroFail2() {

        // Arrange
        Libro libro = new Libro("1",
                "",     //Los datos tipo String no pueden estar vacíos
                "Tipo libro #1",
                "Categoria libro #1",
                -20, //Las cantidades de unidades disponibles o prestadas no pueden ser negativas
                0);
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        Throwable excepcion = assertThrows(IllegalArgumentException.class, () -> {
           libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        });

        // Assert
        assertEquals("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente", excepcion.getMessage());
    }

    @Test
    @DisplayName("Testing creación de un nuevo libro - Fail") //guardarLibro
    void testAgregarUnNuevoLibroFail3() {

        // Arrange
        Libro libro = new Libro("1",
                "Nombre libro #1",
                "Tipo libro #1",
                "Categoria libro #1",
                0,  //Debe ingresar al menos una unidad de este tipo de libro en el sistema
                0);
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        Throwable excepcion = assertThrows(IllegalArgumentException.class, () -> {
            libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        });

        // Assert
        assertEquals("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente", excepcion.getMessage());
    }

    @Test
    @DisplayName("Testing de traer todos los libros - Exito") //getLibros
    void getTodosLosLibros() {

        // Arrange
        List<Libro> libroList = Lists.newArrayList(
                new Libro("1",
                        "Nombre libro #1",
                        "Tipo libro #1",
                        "Categoria libro #1",
                        20,
                        10),
                new Libro("2",
                        "Nombre libro #2",
                        "Tipo libro 2",
                        "Categoria libro #2",
                        15,
                        5),
                new Libro("3",
                        "Nombre libro #3",
                        "Tipo libro #3",
                        "Categoria libro #3",
                        100,
                        45),
                new Libro("4",
                        "Nombre libro #4",
                        "Tipo libro #4",
                        "Categoria libro #4",
                        5,
                        3));
        Mockito.when(libroRepositorio.findAll()).thenReturn(libroList);

        // Act
        var result = libroServicio.getLibros();

        // Assert
        Assertions.assertEquals("1", result.get(0).getId());
        Assertions.assertEquals("Nombre libro #1", result.get(0).getNombre());
        Assertions.assertEquals("Categoria libro #1", result.get(0).getCategoria());
        Assertions.assertEquals(10,result.get(0).getUnidadesPrestadas());

        Assertions.assertEquals("2", result.get(1).getId());
        Assertions.assertEquals("Nombre libro #2", result.get(1).getNombre());
        Assertions.assertEquals("Categoria libro #2", result.get(1).getCategoria());
        Assertions.assertEquals(5,result.get(1).getUnidadesPrestadas());

        Assertions.assertEquals("3", result.get(2).getId());
        Assertions.assertEquals("Nombre libro #3", result.get(2).getNombre());
        Assertions.assertEquals("Categoria libro #3", result.get(2).getCategoria());
        Assertions.assertEquals(45,result.get(2).getUnidadesPrestadas());

        Assertions.assertEquals("4", result.get(3).getId());
        Assertions.assertEquals("Nombre libro #4", result.get(3).getNombre());
        Assertions.assertEquals("Categoria libro #4", result.get(3).getCategoria());
        Assertions.assertEquals(3,result.get(3).getUnidadesPrestadas());

    }

    @Test
    @DisplayName("Testing de obtención de un libro - Exito") //getLibroPorId
    void obtenerLibroPorId(){

        // Arrange
        Libro libro = new Libro("1",
                "Nombre libro #1",
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);

        Mockito.when(libroRepositorio.findById(Mockito.any())).thenReturn(Optional.of(libro));

        // Act
        var resultado = libroServicio.getLibroPorId("1");

        // Assert
        Assertions.assertEquals("1", resultado.getId());
        Assertions.assertEquals("Nombre libro #1", resultado.getNombre());
        Assertions.assertEquals("Tipo libro #1", resultado.getTipo());
        Assertions.assertEquals("Categoria libro #1", resultado.getCategoria());
        Assertions.assertEquals(20, resultado.getUnidadesDisponibles());
        Assertions.assertEquals(0, resultado.getUnidadesPrestadas());
    }

    @Test
    @DisplayName("Testing de edición de un libro - Exito") //actualizarLibro
    void actualizar(){

        // Arrange
        var libroActualizado = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                12,
                5);

        var libroDTO = new LibroDTO("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                12,
                5);

        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libroMapper.fromDTO(libroDTO));
        Mockito.when(libroRepositorio.findById(libroDTO.getId())).thenReturn(Optional.of(libroActualizado));

        // Act
        var resultado =libroServicio.actualizarLibro(libroDTO);

        // Assert
        Assertions.assertEquals("10", resultado.getId());
        Assertions.assertEquals("Nombre libro #10", resultado.getNombre());
        Assertions.assertEquals("Tipo libro #10", resultado.getTipo());
        Assertions.assertEquals("Categoria libro #10", resultado.getCategoria());
        Assertions.assertEquals(12, resultado.getUnidadesDisponibles());
        Assertions.assertEquals(5, resultado.getUnidadesPrestadas());
        Assertions.assertNull(resultado.getFechaUltimoPrestamo());
    }

    @Test
    @DisplayName("Testing consultar disponibilidad de un libro - Disponible") //consultarDisponibilidad
    void consultarDisponibilidadRecursoDisponible() {

        // Arrange
        var libro1 = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                12,
                5);
        Mockito.when(libroRepositorio.findById("10")).thenReturn(Optional.of(libro1));

        // Act
        var resultadoDisponible = libroServicio.consultarDisponibilidad("10");

        // Assert
        Assertions.assertEquals("El libro con id " + libro1.getId() + " si se encuentra disponible, hay " + libro1.getUnidadesDisponibles() + " unidades disponibles", resultadoDisponible);

    }

    @Test
    @DisplayName("Testing consultar disponibilidad de un libro - No disponible") //consultarDisponibilidad
    void consultarDisponibilidadRecursoNoDisponible() {

        // Arrange
        var libro2 = new Libro("11",
                "Nombre libro #11",
                "Tipo libro #11",
                "Categoria libro #11",
                0,
                30);

        libro2.setFechaUltimoPrestamo(LocalDate.now());

        Mockito.when(libroRepositorio.findById("11")).thenReturn(Optional.of(libro2));

        // Act
        var resultadoNoDisponible = libroServicio.consultarDisponibilidad("11");

        // Assert
        Assertions.assertEquals("El libro con id " + libro2.getId() + " no se encuentra disponible, la fecha de último préstamo fue el " + String.valueOf(LocalDate.now()), resultadoNoDisponible);

    }

    @Test
    @DisplayName("Testing de prestar un libro disponible - Exito") //prestarLibro
    void prestarLibroDisponible(){

        // Arrange
        var libro = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                13,
                4);
        Mockito.when(libroRepositorio.findById(libro.getId())).thenReturn(Optional.of(libro));
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        var resultado = libroServicio.prestarLibro(libro.getId());

        // Assert
        Assertions.assertEquals("Se ha prestado el libro con id " + libro.getId() +  ", en total se han prestado " + (libro.getUnidadesPrestadas() + 1) +
                " unidades y quedan aún disponibles " + (libro.getUnidadesDisponibles() - 1) + " unidades.", resultado);
    }

    @Test
    @DisplayName("Testing de prestar un libro no disponible - Fail") //prestarLibro
    void prestarLibroNoDisponible(){

        // Arrange
        var libro = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                0,
                25);
        Mockito.when(libroRepositorio.findById(libro.getId())).thenReturn(Optional.of(libro));
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        var resultado = libroServicio.prestarLibro(libro.getId());

        // Assert
        Assertions.assertEquals("No quedan unidades disponibles del libro con id " + libro.getId() + " puede esperar a que alguien devuelva una unidad.", resultado);
    }

    @Test
    @DisplayName("Testing de devolver un libro prestado - Exito") //devolverLibro
    void devolverLibro(){

        // Arrange
        var libro = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                15,
                4);
        Mockito.when(libroRepositorio.findById(libro.getId())).thenReturn(Optional.of(libro));
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        var resultado = libroServicio.devolverLibro(libro.getId());

        // Assert
        Assertions.assertEquals("Se ha devuelto un libro con id " + libro.getId() + ", en total se han prestado " + (libro.getUnidadesPrestadas() - 1) +
                " unidades y quedan aún disponibles " + (libro.getUnidadesDisponibles() + 1) + " unidades.", resultado);
    }

    @Test
    @DisplayName("Testing de devolver un libro cuando no hay libros prestados - Fail") //devolverLibro
    void devolverLibroFail(){

        //Arrange
        var libro = new Libro("10",
                "Nombre libro #10",
                "Tipo libro #10",
                "Categoria libro #10",
                15,
                0);
        Mockito.when(libroRepositorio.findById(libro.getId())).thenReturn(Optional.of(libro));
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        // Act
        var resultado = libroServicio.devolverLibro(libro.getId());

        // Assert
        Assertions.assertEquals("Todos los libros con 10 han sido devueltos.", resultado);
    }
}