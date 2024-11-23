package DAOs;

import Connections.Conexao;
import Salas.Sala;

import java.sql.*;
import java.util.ArrayList;

public abstract class SalaDAO implements MasterDAO<Sala>{
    public Sala salvar(Sala sala) {
        String sql = "INSERT INTO salas (nome, capacidade) VALUES (?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapacidade());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sala.setNome(rs.getString(1)); // Define o nome da sala como chave gerada
            }

            return sala;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar sala: " + e.getMessage());
            return null;
        }
    }

    public Sala buscarPorId(int id) {
        String sql = "SELECT * FROM salas WHERE id = ?";
        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Sala(rs.getString("nome"), rs.getInt("capacidade"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar sala: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Sala> buscarTodos() {
        String sql = "SELECT * FROM salas";
        ArrayList<Sala> salas = new ArrayList<>();
        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                salas.add(new Sala(rs.getString("nome"), rs.getInt("capacidade")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as salas: " + e.getMessage());
        }
        return salas;
    }

    public void atualizar(Sala sala) {
        String sql = "UPDATE salas SET capacidade = ? WHERE nome = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getCapacidade());
            stmt.setString(2, sala.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    public void deletar(Sala sala) {
        String sql = "DELETE FROM salas WHERE nome = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar sala: " + e.getMessage());
        }
    }
}
