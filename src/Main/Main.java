package Main;

import Cinemas.Cineart;
import Cinemas.Cinema;
import DAOs.*;
import Exceptions.IdExistenteException;
import Exceptions.NaoExisteException;
import Filmes.Filme;
import Salas.Sala;
import Sessoes.Sessao;
import Vendas.Venda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Main {
    public static CinemaDAO cinemaDAO = new CinemaDAO();
    public static FilmeDAO filmeDAO = new FilmeDAO();
    public static SalaDAO salaDAO = new SalaDAO();
    public static SessaoDAO sessaoDAO = new SessaoDAO();
    public static VendaDAO vendaDAO = new VendaDAO();

    public static Cineart cineart;

    public static final Scanner scanner = new Scanner(System.in);
    public static final ArrayList<Cinema> cinemas = cinemaDAO.buscarTodos();
    public static final ArrayList<Filme> filmes = filmeDAO.buscarTodos();

    public static void main(String[] args) {
        int opcao;
        cineart = Cineart.getInstance(1, "Cineart", "Contagem");

        do {
            System.out.println("\n----- Sistema de Gerenciamento de Cinema -----");
            System.out.println("1. Gerenciar Cinemas");
            System.out.println("2. Gerenciar Filmes");
            System.out.println("3. Gerenciar Salas");
            System.out.println("4. Gerenciar Sessões");
            System.out.println("4. Gerenciar Vendas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

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
                case 5:
                    menuVendas();
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


    //Menu Cinema--------------------------------------------------------------
    private static void menuCinemas() {
        System.out.println("\n--- Gerenciar Cinemas ---");
        System.out.println("1. Listar Cinemas");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
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
    //--------------------------------------------------------------------------



    //Menu Filme ---------------------------------------------------------------
    private static void menuFilmes() {
        System.out.println("\n--- Gerenciar Filmes ---");
        System.out.println("1. Adicionar Filme");
        System.out.println("2. Listar Filmes");
        System.out.println("3. Atualizar Filme");
        System.out.println("4. Remover Filme");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        int id;
        Filme filme;

        switch (opcao) {
            case 1:
                adicionarFilme();
                break;
            case 2:
                listarFilmes();
                break;
            case 3:
                System.out.println("Digite o ID do filme:");
                id = scanner.nextInt();
                scanner.nextLine();

                filme = filmeDAO.buscarPorId(id);
                try {
                    if(filme==null) throw new NaoExisteException("Filme não existe");
                    atualizarFilme(filmeDAO.buscarPorId(id));
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                }

                break;
            case 4:
                System.out.println("Digite o ID do filme:");
                id = scanner.nextInt();
                scanner.nextLine();

                filme = filmeDAO.buscarPorId(id);
                try {
                    if(filme==null) throw new NaoExisteException("Filme não existe");
                    deletarFilme(filmeDAO.buscarPorId(id));
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                }


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

        try{
            filmeDAO.existe(id);
        }catch (IdExistenteException e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a duração do filme (em segundos): ");
        long duracao = scanner.nextLong();
        scanner.nextLine();

        Filme filme = new Filme(id, nome, duracao);
        filmeDAO.salvar(filme);
        filmes.add(filme);
        System.out.println("Filme adicionado com sucesso!");
    } //OK

    private static void listarFilmes() {
        if (filmes.isEmpty()) {
            System.out.println("Não há filmes cadastrados.");
            return;
        }

        System.out.println("\n--- Lista de Filmes ---");
        for (Filme filme : filmes) {
            System.out.println(filme);
        }
    } //OK

    private static void atualizarFilme(Filme antigoFilme) {
        System.out.println("Atual: " + antigoFilme.getNome());
        System.out.print("Digite o nome do filme: ");
        String nome = scanner.nextLine();
        if(nome=="") nome = antigoFilme.getNome();

        System.out.println("Atual: " + antigoFilme.getDuracao_s());
        System.out.print("Digite a duração do filme (em segundos, 0 para manter o atual): ");
        long duracao = scanner.nextLong();
        if(duracao==0) duracao = antigoFilme.getDuracao_s();
        else scanner.nextLine();

        Filme novoFilme = new Filme(antigoFilme.getId(), nome, duracao);
        for(Filme filme : filmes){
            if(filme.getId()==novoFilme.getId()){
                filmes.set(filmes.indexOf(filme), novoFilme);
            }
        }
        //filmes.set(filmes.indexOf(antigoFilme), novoFilme);
        filmeDAO.atualizar(novoFilme);
    } //OK

    private static void deletarFilme(Filme filme) {
        filmeDAO.deletar(filme);
        for(Filme f : filmes){
            if(f.getId()==filme.getId()){
                filmes.remove(f);
            }
        }
    } //OK
    //------------------------------------------------------------------------------------------



    //Menu Salas--------------------------------------------------------------------------------
    private static void menuSalas() {
        System.out.println("\n--- Gerenciar Salas ---");
        System.out.println("1. Adicionar Sala");
        System.out.println("2. Listar Salas");
        System.out.println("3. Atualizar Sala");
        System.out.println("4. Deletar Sala");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        int id;
        Sala sala;
        Cinema cinema;

        switch (opcao) {
            case 1:
                adicionarSala();
                break;
            case 2:
                System.out.println("Digite o ID do Cinema:");
                id = scanner.nextInt();
                scanner.nextLine();
                cinema = cinemaDAO.buscarPorId(id);

                try {
                    if(cinema==null) throw new NaoExisteException("Cinema não existe");
                    listarSalas(cinema);
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                System.out.println("Digite o ID da Sala:");
                id = scanner.nextInt();
                scanner.nextLine();

                sala = salaDAO.buscarPorId(id);
                try {
                    if(sala==null) throw new NaoExisteException("Sala não existe");
                    atualizarSala(sala);
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                }


                break;
            case 4:
                System.out.println("Digite o ID da sala:");
                id = scanner.nextInt();
                scanner.nextLine();

                sala = salaDAO.buscarPorId(id);
                try {
                    if(sala==null) throw new NaoExisteException("Sala não existe");
                    deletarSala(sala);
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                }

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
        System.out.print("Digite o ID da sala: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try{
            salaDAO.existe(id);
        }catch (IdExistenteException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite o nome da sala: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a capacidade da sala: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o ID do cinema: ");
        int id_cinema = scanner.nextInt();
        scanner.nextLine();
        Cinema cinema = cinemaDAO.buscarPorId(id_cinema);

        try {
            if(cinema==null) throw new NaoExisteException("Cinema não existe");
        }catch(NaoExisteException e){
            System.out.println(e.getMessage());
            return;
        }

        Sala sala = new Sala(id, nome, capacidade, id_cinema);
        salaDAO.salvar(sala);
        cinema.getSalas().add(sala);
        System.out.println("Sala adicionada com sucesso!");
    } //OK

    private static void listarSalas(Cinema cinema) {
        ArrayList<Sala> salas = salaDAO.buscarTodos(cinema.getId());
        if (salas.isEmpty()) {
            System.out.println("Não há salas cadastrados.");
            return;
        }

        System.out.println("\n--- Lista de Salas do Cinema "+cinema.getNome()+" ---");
        for (Sala sala : salas) {
            System.out.println(sala);
        }
    } //OK

    private static void atualizarSala(Sala antigaSala) {
        System.out.println("Atual: " + antigaSala.getNome());
        System.out.print("Digite o nome da sala: ");
        String nome = scanner.nextLine();
        if(nome=="") nome = antigaSala.getNome();

        System.out.println("Atual: " + antigaSala.getCapacidade());
        System.out.print("Digite a capacidade da sala: ");
        int capacidade = scanner.nextInt();
        if(capacidade==0) capacidade = antigaSala.getCapacidade();
        else scanner.nextLine();



        ArrayList<Sala> salas = cinemaDAO.buscarPorId(antigaSala.getId_cinema()).getSalas();

        Sala novaSala = new Sala(antigaSala.getId(), nome, capacidade, antigaSala.getId_cinema());
        for(Sala sala : salas){
            if(sala.getId()==novaSala.getId()){
                salas.set(salas.indexOf(salas), novaSala);
            }
        }
        salaDAO.atualizar(novaSala);
    } //OK

    private static void deletarSala(Sala sala) {
        salaDAO.deletar(sala);
    } //OK
    //------------------------------------------------------------------------------------------



    //Menu Sessões--------------------------------------------------------------------------
    private static void menuSessoes() {
        System.out.println("\n--- Gerenciar Sessões ---");
        System.out.println("1. Adicionar Sessão");
        System.out.println("2. Listar Sessões");
        System.out.println("3. Atualizar Sessão");
        System.out.println("4. Deletar Sessão");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        int id;
        Sessao sessao;

        switch (opcao) {
            case 1:
                adicionarSessao();
                break;
            case 2:
                System.out.println("Digite o ID da Sala:");
                id = scanner.nextInt();
                scanner.nextLine();

                Sala sala = salaDAO.buscarPorId(id);
                try {
                    if(sala==null) throw new NaoExisteException("Cinema não existe");
                    listarSessoes(sala);
                }catch(NaoExisteException e){
                    System.out.println(e.getMessage());
                    return;
                }

                break;
            case 3:
                System.out.println("Digite o ID da Sessao:");
                id = scanner.nextInt();
                scanner.nextLine();

                sessao = sessaoDAO.buscarPorId(id);
                try {
                    if(sessao==null) throw new NaoExisteException("Sessão não existe");
                    atualizarSessao(sessao);
                }catch (NaoExisteException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                System.out.println("Digite o ID da sessão:");
                id = scanner.nextInt();
                scanner.nextLine();

                sessao = sessaoDAO.buscarPorId(id);
                try {
                    if(sessao==null) throw new NaoExisteException("Sessão não existe");
                    deletarSessao(sessao);
                }catch (NaoExisteException e) {
                    System.out.println(e.getMessage());
                }


                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }


    private static void adicionarSessao() {
        System.out.println();
        System.out.print("Digite o ID da sessão: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try{
            sessaoDAO.existe(id);
        }catch (IdExistenteException e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite o ID da sala: ");
        int id_sala = scanner.nextInt();
        scanner.nextLine();

        Sala sala = salaDAO.buscarPorId(id_sala);
        if (sala == null) {
            throw new NaoExisteException("Sala com ID " + id_sala + " não existe!");
        }

        System.out.print("Digite o ID do filme: ");
        int id_filme = scanner.nextInt();
        scanner.nextLine();

        Filme filme = filmeDAO.buscarPorId(id_filme);

        try{
            if (filme == null) throw new NaoExisteException("Filme com ID " + id_filme + " não existe!");
        }catch(NaoExisteException e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite uma data (dd/MM/yyyy): ");
        String dataString = scanner.nextLine();

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = formatoData.parse(dataString);
        } catch (Exception e) {
            System.out.println("Data inválida! Por favor, insira a data no formato correto (dd/MM/yyyy).");
            return;
        }

        sala.criarSessao(id, filme, data);
    } //OK

    private static void listarSessoes(Sala sala) {
        System.out.println("\n--- Lista de Salas da Sala "+sala.getNome()+" ---");
        sala.listarSessoes();
    } //OK

    private static void atualizarSessao(Sessao antigaSessao) {
        System.out.println("Atual "+antigaSessao.getSala().getId());
        System.out.print("Digite o ID da sala: ");
        int id_sala = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Atual: "+antigaSessao.getFilme().getId());
        System.out.print("Digite o ID do filme: ");
        int id_filme = scanner.nextInt();
        scanner.nextLine();

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Atual: " + antigaSessao.getData());
        System.out.print("Digite uma data (dd/MM/yyyy): ");
        String dataString = scanner.nextLine();

        Sala sala = salaDAO.buscarPorId(id_sala);
        Filme filme = filmeDAO.buscarPorId(id_filme);

        try {
            Date data = formatoData.parse(dataString);
            Sessao sessao = new Sessao(antigaSessao.getId(), sala, filme, data);
            sessaoDAO.salvar(sessao);

            System.out.println("Sessão adicionada com sucesso!");
        } catch (Exception e) {
            System.out.println("Data inválida! Por favor, insira a data no formato correto.");
        }
    }

    private static void deletarSessao(Sessao sessao) {
        sessaoDAO.deletar(sessao);
    }
    //--------------------------------------------------------------------------------------

    // Menu Vendas --------------------------------------------------------------------------------
    private static void menuVendas() {
        System.out.println("\n--- Gerenciar Vendas ---");
        System.out.println("1. Realizar Venda");
        System.out.println("2. Listar Vendas");
        System.out.println("3. Deletar Venda");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                realizarVenda();
                break;
            case 2:
                listarVendas();
                break;
            case 3:
                deletarVenda();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    // Função para realizar venda
    private static void realizarVenda() {
        System.out.print("Digite o ID da venda: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            vendaDAO.existe(id);
        } catch (IdExistenteException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite o ID da sessão: ");
        int idSessao = scanner.nextInt();
        scanner.nextLine();

        Sessao sessao = sessaoDAO.buscarPorId(idSessao);
        try {
            if (sessao == null) throw new NaoExisteException("Sessão não existe!");
        } catch (NaoExisteException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Digite a quantidade de ingressos: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        Venda venda = new Venda(id, cinemaDAO.buscarPorId(sessao.getSala().getId_cinema()), sessao, quantidade);
        vendaDAO.salvar(venda);
        System.out.println("Venda realizada com sucesso!");
    }

    private static void listarVendas() {
        ArrayList<Venda> vendas = vendaDAO.buscarTodos();
        if (vendas.isEmpty()) {
            System.out.println("Não há vendas registradas.");
            return;
        }

        System.out.println("\n--- Lista de Vendas ---");
        for (Venda venda : vendas) {
            System.out.println(venda);
        }
    }

    private static void deletarVenda() {
        System.out.print("Digite o ID da venda: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Venda venda = vendaDAO.buscarPorId(id);
        try {
            if (venda == null) throw new NaoExisteException("Venda não existe!");
            vendaDAO.deletar(venda);
            System.out.println("Venda deletada com sucesso!");
        } catch (NaoExisteException e) {
            System.out.println(e.getMessage());
        }
    }
// -
}
