package dev.j3c.sofkau.biblioteca.modelo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Libro {

    @Id
    private String id;
    private String nombre;
    private String tipo;
    private String categoria;
    private Integer unidadesDisponibles;
    private Integer unidadesPrestadas;
    private LocalDateTime fechaUltimoPrestamo;

    public Libro() {
    }

    public Libro(String id) {
        this.id = id;
    }

    public Libro(String id, String nombre, String tipo, String categoria, Integer unidadesDisponibles, Integer unidadesPrestadas) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesPrestadas = unidadesPrestadas;
        this.fechaUltimoPrestamo = null;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public Integer getUnidadesPrestadas() {
        return unidadesPrestadas;
    }
}
