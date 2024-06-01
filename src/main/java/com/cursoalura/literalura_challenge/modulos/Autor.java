package com.cursoalura.literalura_challenge.modulos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Titulo> titulos = new ArrayList<>();

    public Autor() {
    }

    public Autor(DatosAutor datosAutor, Titulo titulo) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFalleimiento();
        this.titulos.add(titulo);
    }

    public void addTitulo(Titulo titulo) {
        this.titulos.add(titulo);
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFalleimiento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    @Override
    public String toString() {

        return """
                Autor; %s
                Fecha de nacimiento: %d
                Fecha de fallecimiento: %d
                Libros: %s
                """.formatted(this.nombre, this.fechaNacimiento, this.fechaFallecimiento,
                this.titulos.stream().map(Titulo::getTitulo).toList().toString()
                );
    }
}
