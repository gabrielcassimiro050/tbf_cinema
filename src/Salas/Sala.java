package Salas;

import Connections.Conexao;
import DAOs.MasterDAO;
import DAOs.SalaDAO;
import Filmes.Filme;
import Sessoes.Sessao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private ArrayList<Sessao> sessoes;
    private static SalaDAO salaDAO;

    public Sala(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.sessoes = new ArrayList<>();
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

    public static ArrayList<Sala> listarSalas(){
        return ;
    }

    public void criarSessao(int id, Sala sala, Filme filme, Date data) {
        Sessao sessao = new Sessao(id, sala, filme, data);
        sessoes.add(sessao);

        String sql = "INSERT INTO sessoes (id, sala_nome, filme_id, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, filme.getId());
            stmt.setTimestamp(4, new Timestamp(data.getTime()));
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
