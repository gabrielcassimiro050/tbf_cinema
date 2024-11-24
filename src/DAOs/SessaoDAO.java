package DAOs;

import Connections.Conexao;
import Filmes.Filme;

import Salas.Sala;
import Sessoes.Sessao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static Main.Main.salaDAO;

public class SessaoDAO implements MasterDAO<Sessao> {
    public SessaoDAO(){
        criarTabela();
    }

    public void criarTabela() {
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS sessoes (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "id_sala INT NOT NULL," +
                    "id_filme INT NOT NULL," +
                    "data TIMESTAMP NOT NULL," +
                    "horario_fim TIMESTAMP NOT NULL" +
                    ");";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de sessões: " + e.getMessage());
        }
    }

    @Override
    public Sessao salvar(Sessao sessao) {
        String sql = "INSERT INTO sessoes (sala_nome, filme_id, data, horario_fim) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getData().getTime()));
            stmt.setTimestamp(4, new Timestamp(sessao.getHorarioFim().getTime()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sessao.setId(rs.getInt(1));
            }
            return sessao;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar sessão: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Sessao buscarPorId(int id) {
        String sql = "SELECT * FROM sessoes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sala sala = salaDAO.buscarPorId(rs.getInt("id_sala"));
                Filme filme = new Filme(rs.getInt("id_filme"), "Placeholder", 0);
                Date data = new Date(rs.getTimestamp("data").getTime());
                Date horarioFim = new Date(rs.getTimestamp("horario_fim").getTime());
                return new Sessao(rs.getInt("id"), sala, filme, data, horarioFim);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar sessão por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Sessao> buscarTodos() {
        String sql = "SELECT * FROM sessoes";
        ArrayList<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala sala = salaDAO.buscarPorId(rs.getInt("id_sala"));
                Filme filme = new Filme(rs.getInt("id_filme"), "Placeholder", 0);
                Date data = new Date(rs.getTimestamp("data").getTime());
                Date horarioFim = new Date(rs.getTimestamp("horario_fim").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), sala, filme, data, horarioFim));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    @Override
    public void atualizar(Sessao sessao) {
        String sql = "UPDATE sessoes SET id_sala = ?, id_filme = ?, data = ?, horario_fim = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getData().getTime()));
            stmt.setTimestamp(4, new Timestamp(sessao.getHorarioFim().getTime()));
            stmt.setInt(5, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sessão: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Sessao sessao) {
        String sql = "DELETE FROM sessoes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar sessão: " + e.getMessage());
        }
    }
}
