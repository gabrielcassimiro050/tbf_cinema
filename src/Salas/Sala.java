package Salas;

import Connections.Conexao;
import DAOs.SalaDAO;
import Exceptions.IdExistenteException;
import Exceptions.SalaOcupadaException;
import Filmes.Filme;
import Sessoes.Sessao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static Main.Main.*;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private int id_cinema;
    private ArrayList<Sessao> sessoes;

    public Sala(int id, String nome, int capacidade, int id_cinema) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.id_cinema = id_cinema;
        this.sessoes = new ArrayList<>();
    }

    public ArrayList<Sessao> getSessoes() {
        return sessoes;
    }

    public int getId_cinema() {
        return id_cinema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }



    public void criarSessao(int id, Filme filme, Date data) throws SalaOcupadaException {
        Date horarioFim = new Date(data.getTime() + filme.getDuracao_s() * 1000);
        for (Sessao s : sessoes) {
            if ((data.before(s.getHorarioFim()) || data.equals(s.getHorarioFim())) && (horarioFim.after(s.getData()) || horarioFim.equals(s.getData()))) {
                throw new SalaOcupadaException("Horário da sessão conflitante com outra sessão na sala.");
            }
        }

        Sessao sessao = new Sessao(id, this, filme, data);
        sessoes.add(sessao);
        sessaoDAO.salvar(sessao);

    }

    public void listarSessoes() {
        ArrayList<Sessao> sessoes = sessaoDAO.buscarTodos(id);

        if (sessoes.isEmpty()) {
            System.out.println("Não há sessões cadastradas.");
            return;
        }

        for (Sessao s : sessoes) {
            System.out.println(s.toString());
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + '\n' + "Nome: " + nome + '\n' + "Capacidade: " + capacidade + '\n';
    }
}
