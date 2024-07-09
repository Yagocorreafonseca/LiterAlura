package com.alura.LiterAlura.Principal;

import com.alura.LiterAlura.DTO.AutorDTO;
import com.alura.LiterAlura.DTO.LivroDTO;
import com.alura.LiterAlura.Models.Autor;
import com.alura.LiterAlura.Models.Livro;
import com.alura.LiterAlura.Repository.AutorRepository;
import com.alura.LiterAlura.Repository.LivroRepository;
import com.alura.LiterAlura.Service.ConsumirAPI;
import com.alura.LiterAlura.Service.ConverterDados;

import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);

    private ConsumirAPI consumirAPI = new ConsumirAPI();

    private ConverterDados conversor = new ConverterDados();

    private final String endereco = "https://gutendex.com/books?search=";

    private AutorRepository repositorioAutor;
    private LivroRepository repositorioLivro;

    public Principal(AutorRepository repositorioAutor, LivroRepository repositorioLivro) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLivro = repositorioLivro;
    }

    int menuNumber = -1;


    public void principal() {

        while (menuNumber != 0) {
            System.out.println("___________________________");
            System.out.println("    LiterAlura   ");
            System.out.println("___________________________");
            System.out.println("         MENU              ");
            System.out.println("1 = Buscar livros pelo titulo");
            System.out.println("2 = Listar livros registrados");
            System.out.println("3 = Listar autores registrados ");
            System.out.println("4 = Listar autores vivos pelo ano");
            System.out.println("5 = Listar livros por idioma");
            System.out.println("6 = Top 10 livros");
            System.out.println("7 = Buscar autores por nome");
            System.out.println("8 = Média de downloads por autor");
            System.out.println("0 = Sair");
            System.out.println("Selecione uma opção");
            menuNumber = scanner.nextInt();
            scanner.nextLine();


                switch (menuNumber){
                    case 1:
                        buscarNovoLivro();
                        break;
                    case 2:
                        buscarLivrosRegistrados();
                        break;
                    case 3:
                        buscarAutoresRegistrados();
                        break;
                    case 4:
                        buscarAutoresVivosPorAno();
                        break;
                    case 5:
                        buscarLivrosPorIdioma();
                        break;
                    case 6:
                        buscarTop10();
                        break;
                    case 7:
                        buscarAutorPorNome();
                        break;
                    case 8:
                        mediaDeDownlaodsPorAutor();
                        break;
                    case 0:
                        System.out.println("Processo finalizado");
                        break;
                    default:
                        System.out.println("Opção invalida (Digite um número de 0 a 8)");
                }
            }


        }

        private void buscarNovoLivro() {
            System.out.println("\nQual livro deseja buscar?");
            var buscaDoUsuario = scanner.nextLine();
            var dados = consumirAPI.consumo(endereco + buscaDoUsuario.replace(" ","%20"));
            salvarNoDb(dados);
        }

        private void salvarNoDb(String dados){
            try{
                Livro livro = new Livro(conversor.obterDados(dados, LivroDTO.class));
                Autor autor = new Autor(conversor.obterDados(dados, AutorDTO.class));
                Autor autorDb = null;
                Livro livroDb = null;
                if (!repositorioAutor.existsByNome(autor.getNome())){
                    repositorioAutor.save(autor);
                    autorDb = autor;
                }else{
                    autorDb = repositorioAutor.findByNome(autor.getNome());
                }
                if (!repositorioLivro.existsByNome(livro.getNome())){
                    livro.setAutor(autorDb);
                    repositorioLivro.save(livro);
                    livroDb = livro;
                }else{
                    livroDb = repositorioLivro.findByNome(livro.getNome());
                }
                System.out.println(livroDb);
            }catch (NullPointerException e){
                System.out.println("Livro não encontrado");
            }

        }


        private void buscarLivrosRegistrados() {
            var bucasDB = repositorioLivro.findAll();
            if(!bucasDB.isEmpty()){
                System.out.println("\nLivros cadastrados no Banco de Dados: ");
                bucasDB.forEach(System.out::println);
            }else{
                System.out.println("\nNenhum livro encontrado no Banco de Dados!");
            }
        }

        private void buscarAutoresRegistrados() {
            var buscaDb = repositorioAutor.findAll();
            if(!buscaDb.isEmpty()){
                System.out.println("\nAutores cadastrados no Banco de Dados:");
                buscaDb.forEach(System.out::println);
            }else{
                System.out.println("\nNenhum autor encontrado no Banco de Dados!");
            }
        }

        private void buscarAutoresVivosPorAno() {
            System.out.println("\nQual ano deseja pesquisar?");
            var anoSelecionado = scanner.nextInt();
            scanner.nextLine();
            var buscaAutoresNoDb = repositorioAutor.verificarSeVivo(anoSelecionado);
            if(!buscaAutoresNoDb.isEmpty()){
                System.out.println("\n\nAtores vivos no ano de: " + anoSelecionado);
                buscaAutoresNoDb.forEach(System.out::println);
            }else {
                System.out.println("\nNenhum autor encontrado para esta data!");
            }
        }

        private void buscarLivrosPorIdioma() {
            var idiomasCadastrados = repositorioLivro.bucasIdiomas();
            System.out.println("\nIdiomas cadastrados no Banco de Dados:");
            idiomasCadastrados.forEach(System.out::println);
            System.out.println("\nSelecione um dos idiomas cadastrados no Banco de Dados:\n");
            var idiomaSelecionado = scanner.nextLine();
            repositorioLivro.buscarPorIdioma(idiomaSelecionado).forEach(System.out::println);
        }

        private void buscarTop10() {
            var top10 = repositorioLivro.findTop10ByOrderByQuantidadeDeDownloadsDesc();
            System.out.println("\n\n_______________  Top 10 livros      ________________\n\n");
            top10.forEach(System.out::println);
        }

        private void buscarAutorPorNome() {
            System.out.println("Qual o nome do autor?");
            var pesquisa = scanner.nextLine();
            var autor = repositorioAutor.encontrarPorNome(pesquisa);
            if (!autor.isEmpty()){
                autor.forEach(System.out::println);
            }else{
                System.out.println("Autor não encontrado");
            }
        }

        private void mediaDeDownlaodsPorAutor() {
            System.out.println("Qual autor deseja buscar?");
            var pesquisa = scanner.nextLine();
            var test = repositorioLivro.encontrarLivrosPorAutor(pesquisa);
            DoubleSummaryStatistics media = test.stream()
                    .mapToDouble(Livro::getQuantidadeDeDownloads)
                    .summaryStatistics();
            System.out.println("Média de Downloads: "+ media.getAverage());
        }


    }
