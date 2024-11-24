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

import static Main.Main.cinemaDAO;
import static Main.Main.salaDAO;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private int id_cinema;
    private ArrayList<Sessao> sessoes;

    public Sala(int id, String nome, int capacidade, int id_cinema) {
        if (salaDAO.buscarPorId(id) != null) {
            throw new IdExistenteException("Já existe uma sala com o ID " + id + ".");
        }
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.id_cinema = id_cinema;
        this.sessoes = new ArrayList<>();
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



    public void criarSessao(int id, Sala sala, Filme filme, Date data) throws SalaOcupadaException {
        Date horarioFim = new Date(data.getTime() + filme.getDuracao_s() * 1000);
        for (Sessao s : sessoes) {
            if (data.before(s.getHorarioFim()) && horarioFim.after(s.getData())) {
                throw new SalaOcupadaException("Horário da sessão conflitante com outra sessão na sala.");
            }
        }

        Sessao sessao = new Sessao(id, sala, filme, data, horarioFim);
        sessoes.add(sessao);

        String sql = "INSERT INTO sessoes (id, sala_nome, filme_id, data, horario_fim) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, filme.getId());
            stmt.setTimestamp(4, new Timestamp(data.getTime()));
            stmt.setTimestamp(5, new Timestamp(horarioFim.getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao criar sessão: " + e.getMessage());
        }
    }

    public void listarSessoes() {
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
        return "Nome: " + nome + '\n' + "Capacidade: " + capacidade + '\n';
    }
}
