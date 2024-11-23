package DAOs;

import Connections.Conexao;
import Filmes.Filme;
import Salas.Sala;
import Sessoes.Sessao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public abstract class SessaoDAO implements MasterDAO<Sessao> {

    @Override
    public Sessao salvar(Sessao sessao) {

        String sql = "INSERT INTO sessoes (sala_nome, filme_id, data) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sessao.getSala().getNome());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getData().getTime()));
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
                Sala sala = new Sala(rs.getString("sala_nome"), 0); // Capacidade não disponível diretamente
                Filme filme = new Filme(rs.getInt("filme_id"), "Placeholder", 0); // Detalhes adicionais devem ser buscados
                Date data = new Date(rs.getTimestamp("data").getTime());
                return new Sessao(rs.getInt("id"), sala, filme, data);
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
                Sala sala = new Sala(rs.getString("sala_nome"), 0); // Capacidade não disponível diretamente
                Filme filme = new Filme(rs.getInt("filme_id"), "Placeholder", 0); // Detalhes adicionais devem ser buscados
                Date data = new Date(rs.getTimestamp("data").getTime());
                sessoes.add(new Sessao(rs.getInt("id"), sala, filme, data));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    @Override
    public void atualizar(Sessao sessao) {
        String sql = "UPDATE sessoes SET sala_nome = ?, filme_id = ?, data = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sessao.getSala().getNome());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, new Timestamp(sessao.getData().getTime()));
            stmt.setInt(4, sessao.getId());
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
