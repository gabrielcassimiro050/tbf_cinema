package Main;

import Cinemas.Cinema;
import DAOs.CinemaDAO;
import DAOs.FilmeDAO;
import DAOs.SalaDAO;
import DAOs.SessaoDAO;
import Filmes.Filme;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static CinemaDAO cinemaDAO = new CinemaDAO();
    public static FilmeDAO filmeDAO = new FilmeDAO();
    public static SalaDAO salaDAO = new SalaDAO();
    public static SessaoDAO sessaoDAO;

    private static final Scanner scanner = new Scanner(System.in);
    //private static final ArrayList<Cinema> cinemas = cinemaDAO.buscarTodos();
    //private static final ArrayList<Filme> filmes = filmeDAO.buscarTodos();

    private static final ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
    private static final ArrayList<Filme> filmes = new ArrayList<Filme>();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n----- Sistema de Gerenciamento de Cinema -----");
            System.out.println("1. Gerenciar Cinemas");
            System.out.println("2. Gerenciar Filmes");
            System.out.println("3. Gerenciar Salas");
            System.out.println("4. Gerenciar Sessões");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    menuCinemas();
                    break;
                case 2:
                    menuFilmes();
                    break;
                case 3:
                    menuSalas();
                    break;
                case 4:
                    menuSessoes();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private static void menuCinemas() {
        System.out.println("\n--- Gerenciar Cinemas ---");
        System.out.println("1. Adicionar Cinema");
        System.out.println("2. Listar Cinemas");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                adicionarCinema();
                break;
            case 2:
                listarCinemas();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private static void adicionarCinema() {
        try {
            System.out.print("Digite o ID do cinema: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o nome do cinema: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o local do cinema: ");
            String local = scanner.nextLine();

            // Validação de ID único
            for (Cinema cinema : cinemas) {
                if (cinema.getId() == id) {
                    throw new Exception("IdExistenteException: O ID informado já existe!");
                }
            }

            cinemas.add(new Cinema(id, nome, local));
            System.out.println("Cinema adicionado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void listarCinemas() {
        if (cinemas.isEmpty()) {
            System.out.println("Não há cinemas cadastrados.");
            return;
        }

        System.out.println("\n--- Lista de Cinemas ---");
        for (Cinema cinema : cinemas) {
            System.out.println(cinema);
        }
    }

    private static void menuFilmes() {
        System.out.println("\n--- Gerenciar Filmes ---");
        System.out.println("1. Adicionar Filme");
        System.out.println("2. Listar Filmes");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                adicionarFilme();
                break;
            case 2:
                listarFilmes();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private static void adicionarFilme() {
        System.out.print("Digite o ID do filme: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a duração do filme (em segundos): ");
        long duracao = scanner.nextLong();
        scanner.nextLine();

        filmes.add(new Filme(id, nome, duracao));
        System.out.println("Filme adicionado com sucesso!");
    }

    private static void listarFilmes() {
        if (filmes.isEmpty()) {
            System.out.println("Não há filmes cadastrados.");
            return;
        }

        System.out.println("\n--- Lista de Filmes ---");
        for (Filme filme : filmes) {
            System.out.println(filme);
        }
    }

    private static void menuSalas() {
        System.out.println("\n--- Gerenciar Salas ---");
        System.out.println("1. Adicionar Sala");
        System.out.println("2. Listar Salas");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                adicionarSala();
                break;
            case 2:
                listarSalas();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private static void adicionarSala() {
        System.out.println("Funcionalidade a ser implementada.");
    }

    private static void listarSalas() {
        System.out.println("Funcionalidade a ser implementada.");
    }

    private static void menuSessoes() {
        System.out.println("Funcionalidade a ser implementada.");
    }
}
