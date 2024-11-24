package DAOs;

import Cinemas.Cinema;
import Connections.Conexao;

import java.sql.*;
import java.util.ArrayList;

public class CinemaDAO implements MasterDAO<Cinema> {
    public CinemaDAO(){
        criarTabela();
    }

    @Override
    public void criarTabela() {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS cinemas (id INT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "local VARCHAR(255) NOT NULL;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    @Override
    public Cinema salvar(Cinema objeto) {
        String sql = "INSERT INTO cinemas (id, nome, local) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getLocal());
            stmt.executeUpdate();
            return objeto;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar cinema: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Cinema buscarPorId(int id) {
        String sql = "SELECT * FROM cinemas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cinema(rs.getInt("id"), rs.getString("nome"), rs.getString("local"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cinema: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Cinema> buscarTodos() {
        String sql = "SELECT * FROM cinemas";
        ArrayList<Cinema> cinemas = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cinemas.add(new Cinema(rs.getInt("id"), rs.getString("nome"), rs.getString("local")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os cinemas: " + e.getMessage());
        }
        return cinemas;
    }

    @Override
    public void atualizar(Cinema objeto) {
        String sql = "UPDATE cinemas SET nome = ?, local = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getLocal());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cinema: " + e.getMessage());
        }
    }
    
    @Override
    public void deletar(Cinema objeto) {
        String sql = "DELETE FROM cinemas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cinema: " + e.getMessage());
        }
    }
}