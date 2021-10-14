package dev.j3c.sofkau.biblioteca.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.j3c.sofkau.biblioteca.dto.LibroDTO;
import dev.j3c.sofkau.biblioteca.servicio.LibroServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LibroControladorTest {

    @MockBean
    private LibroServicio libroServicio;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / libros - success")
    public void encontrarTodosLosLibros() throws Exception{

        //Arrange
        LibroDTO libroDTO1 = new LibroDTO();
        libroDTO1.setId("1");
        libroDTO1.setNombre("Nombre libro #1");
        libroDTO1.setTipo("Tipo #1");
        libroDTO1.setCategoria("Categoria #1");
        libroDTO1.setUnidadesDisponibles(10);
        libroDTO1.setUnidadesPrestadas(5);
        libroDTO1.setFechaUltimoPrestamo(null);

        LibroDTO libroDTO2 = new LibroDTO();
        libroDTO2.setId("2");
        libroDTO2.setNombre("Nombre libro #2");
        libroDTO2.setTipo("Tipo #2");
        libroDTO2.setCategoria("Categoria #2");
        libroDTO2.setUnidadesDisponibles(30);
        libroDTO2.setUnidadesPrestadas(8);
        libroDTO2.setFechaUltimoPrestamo(null);

        LibroDTO libroDTO3 = new LibroDTO();
        libroDTO3.setId("3");
        libroDTO3.setNombre("Nombre libro #3");
        libroDTO3.setTipo("Tipo #3");
        libroDTO3.setCategoria("Categoria #3");
        libroDTO3.setUnidadesDisponibles(0);
        libroDTO3.setUnidadesPrestadas(25);
        libroDTO3.setFechaUltimoPrestamo(LocalDate.of(2021,9,27));

        LibroDTO libroDTO4 = new LibroDTO();
        libroDTO4.setId("4");
        libroDTO4.setNombre("Nombre libro #4");
        libroDTO4.setTipo("Tipo #4");
        libroDTO4.setCategoria("Categoria #4");
        libroDTO4.setUnidadesDisponibles(0);
        libroDTO4.setUnidadesPrestadas(10);
        libroDTO4.setFechaUltimoPrestamo(LocalDate.of(2021,11,20));

        List<LibroDTO> lista = new ArrayList<>();
        lista.add(libroDTO1);
        lista.add(libroDTO2);
        lista.add(libroDTO3);
        lista.add(libroDTO4);
        Mockito.when(libroServicio.getLibros()).thenReturn(lista);

        //Act
        mockMvc.perform(get("/biblioteca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                //Assert
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("Nombre libro #1")))
                .andExpect(jsonPath("$[0].categoria", is("Categoria #1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].nombre", is("Nombre libro #2")))
                .andExpect(jsonPath("$[1].categoria", is("Categoria #2")))
                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].nombre", is("Nombre libro #3")))
                .andExpect(jsonPath("$[2].categoria", is("Categoria #3")))
                .andExpect(jsonPath("$[3].id", is("4")))
                .andExpect(jsonPath("$[3].nombre", is("Nombre libro #4")))
                .andExpect(jsonPath("$[3].categoria", is("Categoria #4")));
    }

    @Test
    @DisplayName("POST /biblioteca/crear success")
    public void crearNuevoLibro() throws Exception {

        LibroDTO datoPost = new LibroDTO();
        datoPost.setNombre("Nombre Libro #1");
        datoPost.setCategoria("Categoria #1");
        datoPost.setTipo("Tipo Libro #1");
        datoPost.setUnidadesDisponibles(5);
        datoPost.setUnidadesPrestadas(2);

        LibroDTO datoReturn = new LibroDTO();
        datoReturn.setId("2");
        datoReturn.setNombre("Nombre Libro #1");
        datoReturn.setCategoria("Categoria #1");
        datoReturn.setTipo("Tipo Libro #1");
        datoReturn.setUnidadesDisponibles(5);
        datoReturn.setUnidadesPrestadas(2);

        Mockito.when(libroServicio.guardarLibro(datoPost)).thenReturn(datoReturn);

        //Act
        mockMvc.perform(post("/biblioteca/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(datoPost)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is("2")))
                .andExpect(jsonPath("$.name", is("Jorge Ramirez")))
                .andExpect(jsonPath("$.position", is("Gerente")));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}