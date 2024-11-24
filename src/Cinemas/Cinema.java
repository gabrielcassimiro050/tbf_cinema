package Cinemas;

import DAOs.CinemaDAO;
import Exceptions.IdExistenteException;
import Exceptions.NomeDuplicadoException;
import Salas.Sala;

import java.util.ArrayList;

public class Cinema {
    private int id;
    private String nome, local;
    private ArrayList<Sala> salas;
    public static CinemaDAO cinemaDAO;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }

    public Cinema(int id, String nome, String local) throws IdExistenteException {
        if (cinemaDAO.buscarPorId(id) != null) {
            throw new IdExistenteException("Já existe um cinema com o ID " + id + ".");
        }
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.salas = new ArrayList<>();
    }

    public void criarSala(int id, String nome, int capacidade) throws NomeDuplicadoException {
        for (Sala sala : salas) {
            if (sala.getNome().equalsIgnoreCase(nome)) {
                throw new NomeDuplicadoException("Já existe uma sala com o nome " + nome + " neste cinema.");
            }
        }
        Sala novaSala = new Sala(id, nome, capacidade, this.id);
        salas.add(novaSala);
    }

    public void listarSalas() {
        if (salas.isEmpty()) {
            System.out.println("Não há salas cadastradas.");
            return;
        }
        for (Sala sala : salas) {
            System.out.println(sala.toString());
        }
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' + "Nome: " + this.nome + '\n' + "Local: " + this.local + '\n';
    }
}
