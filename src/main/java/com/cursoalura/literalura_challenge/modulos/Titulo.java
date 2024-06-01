package com.cursoalura.literalura_challenge.modulos;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(500)", unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDeDescarga;
    @ManyToOne
    private Autor autors;

    public Titulo() {
    }

    public Titulo(String titulo, List<String> idioma, Integer numeroDeDescarga, List<DatosAutor> autors) {
        this.titulo = titulo;
        this.idioma = idioma.get(0);
        this.numeroDeDescarga = numeroDeDescarga;
        this.autors = new Autor(autors.get(0));
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutors() {
        return autors;
    }

    public void setAutors(Autor autors) {
        this.autors = autors;
    }

    public Integer getNumeroDeDescarga() {
        return numeroDeDescarga;
    }

    public void setNumeroDeDescarga(Integer numeroDeDescarga) {
        this.numeroDeDescarga = numeroDeDescarga;
    }

    @Override
    public String toString() {
        return  """
                -----------------Libro---------------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                -------------------------------------
                """.formatted(this.titulo,this.autors.getNombre(),this.idioma,this.numeroDeDescarga);
    }
}
