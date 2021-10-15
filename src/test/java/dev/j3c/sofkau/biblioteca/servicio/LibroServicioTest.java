package dev.j3c.sofkau.biblioteca.servicio;

import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.mapper.LibroMapper;
import dev.j3c.sofkau.biblioteca.modelo.Libro;
import dev.j3c.sofkau.biblioteca.repositorio.LibroRepositorio;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibroServicioTest {

    @Mock
    private LibroRepositorio libroRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private LibroMapper libroMapper;

    @Test
    @DisplayName("Testing creación de un nuevo libro - Exito")
    void testAgregarUnNuevoLibro() {

        // Arrange
        Libro libro = new Libro("1",
                "Nombre libro #1",
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);

        // Act
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        //Assert
        var resultado = libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        Assertions.assertEquals("1", resultado.getId());
        Assertions.assertEquals("Nombre libro #1", resultado.getNombre());
        Assertions.assertEquals("Tipo libro #1", resultado.getTipo());
        Assertions.assertEquals("Categoria libro #1", resultado.getCategoria());
        Assertions.assertEquals(20, resultado.getUnidadesDisponibles());
        Assertions.assertEquals(0, resultado.getUnidadesPrestadas());
        Assertions.assertNull( resultado.getFechaUltimoPrestamo());
    }

    @Test
    @DisplayName("Testing creación de un nuevo libro - Fail")
    void testAgregarUnNuevoLibroFail1() {

        // Arrange
        Libro libro = new Libro("1",
                "",     //Los datos tipo String no pueden estar vacíos
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);

        // Act
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        //Assert

        Throwable excepcion = assertThrows(IllegalArgumentException.class, () -> {
            var resultado = libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        });
        assertEquals("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente", excepcion.getMessage());
    }

    @Test
    @DisplayName("Testing creación de un nuevo libro - Fail")
    void testAgregarUnNuevoLibroFail2() {

        // Arrange
        Libro libro = new Libro("1",
                "",     //Los datos tipo String no pueden estar vacíos
                "Tipo libro #1",
                "Categoria libro #1",
                -20,
                0);

        // Act
        Mockito.when(libroRepositorio.save(Mockito.any())).thenReturn(libro);

        //Assert

        Throwable excepcion = assertThrows(IllegalArgumentException.class, () -> {
           libroServicio.guardarLibro(libroMapper.fromCollection(libro));
        });
        assertEquals("Parámetros de ingreso del nuevo tipo de libro incorrectos, intente nuevamente", excepcion.getMessage());
    }

    @Test
    @DisplayName("Testing de obtención de un libro - Exito")
    void obtenerPorId(){
        Libro libro = new Libro("1",
                "Nombre libro #1",     //Los datos tipo String no pueden estar vacíos
                "Tipo libro #1",
                "Categoria libro #1",
                20,
                0);

        Mockito.when(libroRepositorio.findById(Mockito.any())).thenReturn(Optional.of(libro));

        var resultado = libroServicio.getLibroPorId("1");
        Assertions.assertEquals("1", resultado.getId());
        Assertions.assertEquals("Nombre libro #1", resultado.getNombre());
        Assertions.assertEquals("Tipo libro #1", resultado.getTipo());
        Assertions.assertEquals("Categoria libro #1", resultado.getCategoria());
        Assertions.assertEquals(20, resultado.getUnidadesDisponibles());
        Assertions.assertEquals(0, resultado.getUnidadesPrestadas());
    }

}