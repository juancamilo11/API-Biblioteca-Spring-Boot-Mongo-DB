package dev.j3c.sofkau.biblioteca.dto;


import java.time.LocalDateTime;

public class LibroDTO {

    private String id;
    private String nombre;
    private String tipo;
    private String categoria;
    private Integer unidadesDisponibles;
    private Integer unidadesPrestadas;
    private LocalDateTime fechaUltimoPrestamo;

    public LibroDTO() {
    }

    public LibroDTO(String id) {
        this.id = id;
    }

    public LibroDTO(String id, String nombre, String tipo, String categoria, Integer unidadesDisponibles, Integer unidadesPrestadas, LocalDateTime fechaUltimoPrestamo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesPrestadas = unidadesPrestadas;
        this.fechaUltimoPrestamo = fechaUltimoPrestamo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(Integer unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public Integer getUnidadesPrestadas() {
        return unidadesPrestadas;
    }

    public void setUnidadesPrestadas(Integer unidadesPrestadas) {
        this.unidadesPrestadas = unidadesPrestadas;
    }

    public LocalDateTime getFechaUltimoPrestamo() {
        return fechaUltimoPrestamo;
    }

    public void setFechaUltimoPrestamo(LocalDateTime fechaUltimoPrestamo) {
        this.fechaUltimoPrestamo = fechaUltimoPrestamo;
    }
}
