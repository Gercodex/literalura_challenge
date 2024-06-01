package com.cursoalura.literalura_challenge.principal;

import com.cursoalura.literalura_challenge.modulos.Autor;
import com.cursoalura.literalura_challenge.modulos.DatosConsulta;
import com.cursoalura.literalura_challenge.modulos.Titulo;
import com.cursoalura.literalura_challenge.repositorio.RepositorioAutor;
import com.cursoalura.literalura_challenge.servicios.ConexionUrl;
import com.cursoalura.literalura_challenge.servicios.Conversor;

import java.io.IOException;
import java.util.*;

public class Principal {
    private final String url = "https://gutendex.com/books/";
    private ConexionUrl conexionUrl = new ConexionUrl();
    private Conversor conversor = new Conversor();
    private Scanner teclado = new Scanner(System.in);
    private RepositorioAutor repositorioAutor;


    public Principal(RepositorioAutor repositorioAutor) {
        this.repositorioAutor = repositorioAutor;
    }

    public void mostrarMenu() {
        int opcion = 0;
        boolean bucle = true;
        String menu = """
                                        
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Listar Libros por autor
                    7 - Listar 10 más descargados registrados
                    0 - Salir
                    """;
        while (bucle) {
            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        mostrarLibrosRegistrados();
                        break;
                    case 3:
                        mostrarAutores();
                        break;
                    case 4:
                        mostrarLiveYearCheck();
                        break;
                    case 5:
                        mostrarMenuDeIdiomas();
                        break;
                    case 6:
                        buscarTitulosPorNombre();
                        break;
                    case 7:
                        mostrarMasDescargados();
                        break;
                    case 0:
                        bucle = false;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (Exception e) {
                System.out.println("Error, Intente de nuevo." + e.getMessage());
                teclado.nextLine();
            }
        }
    }

    private void mostrarMasDescargados() {
        List<Titulo> titulosTemp = repositorioAutor.masDescargados();
        titulosTemp.forEach(System.out::println);
    }

    private void buscarTitulosPorNombre() {
        System.out.println("Escribe el nombre del autor");
        String nombreAutor = teclado.nextLine();
        List<Titulo> titulosTemp = repositorioAutor.tituloPorAutor(nombreAutor);
        if (titulosTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarMenuDeIdiomas() {
        /*repositorioAutor.listaIdiomas().forEach(System.out::println);*/ //Listado de idiomas existentes.
        System.out.println("""
                es: Español
                pt: Portugués
                fr: Francés
                en: Inglés
                """);
        System.out.println("Ingrese el idioma para buscar los libros:");
        String entrada = teclado.nextLine();
        List<Titulo>  titulosTemp = repositorioAutor.listaTitulosPorIdioma(entrada);
        if (titulosTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarAutores() {

        repositorioAutor.findAll().forEach(System.out::println);

    }

    private void mostrarLiveYearCheck() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
        Integer year = teclado.nextInt();
        teclado.nextLine();

        List<Autor>  autorsTemp = repositorioAutor.liveYearCheck(year);
        if (autorsTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        autorsTemp.forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        repositorioAutor.todosLosTitulos().forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException{
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String nombreTitulo = teclado.nextLine();
        DatosConsulta consulta = buscarLibro(nombreTitulo);
        Optional<Titulo> titulo = consulta.resultados().stream()
                .map(r -> new Titulo(
                        r.titulo(),
                        r.idiomas(),
                        r.numeroDeDescargas(),
                        r.autores()
                )).
                findFirst();
        if (titulo.isPresent()) {
            Titulo tituloTemp = titulo.get();
            Autor autorTemp = tituloTemp.getAutors();
            if (repositorioAutor.buscarTituloPorNombre(tituloTemp.getTitulo()).isPresent()) {
                System.out.println("No se puede registrar este libro más de una vez");
            } else if (repositorioAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).isPresent()) {
                tituloTemp.setAutors(repositorioAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).get());
                tituloTemp.getAutors().addTitulo(tituloTemp);
                repositorioAutor.save(tituloTemp.getAutors());
                System.out.println(tituloTemp);
            } else {
                titulo.get().getAutors().addTitulo(titulo.get());
                repositorioAutor.save(titulo.get().getAutors());
                System.out.println(tituloTemp);
            }

        } else {
            System.out.println("Titulo no encontrado");
        }

    }

    private DatosConsulta buscarLibro(String titulo) throws IOException, InterruptedException {
        return conversor.convertir(new String(conexionUrl.consultar(url+"?search="+titulo.replace(" ","+"))), DatosConsulta.class);
    }


}
