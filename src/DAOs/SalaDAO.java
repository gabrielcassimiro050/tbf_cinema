package DAOs;

import Connections.Conexao;
import Exceptions.IdExistenteException;
import Filmes.Filme;
import Salas.Sala;
import Sessoes.Sessao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SalaDAO implements MasterDAO<Sala> {

    public SalaDAO(){
        criarTabela();
    }

    public void criarTabela() {
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS salas (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nome VARCHAR(255) NOT NULL," +
                    "capacidade INT NOT NULL," +
                    "id_cinema INT NOT NULL)");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de salas: " + e.getMessage());
        }
    }

    @Override
    public Sala salvar(Sala sala) {
        String sql = "INSERT INTO salas (id, nome, capacidade, id_cinema) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sala.getId());
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, sala.getCapacidade());
            stmt.setInt(4, sala.getId_cinema());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sala.setId(rs.getInt(1));
            }
            return sala;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar sala: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void existe(int id) throws IdExistenteException {
        String sql = "SELECT * FROM salas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                throw new IdExistenteException("ID já existe");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar sala: " + e.getMessage());
        }
    }

    @Override
    public Sala buscarPorId(int id) {
        String sql = "SELECT * FROM salas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("id_cinema"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar sala por ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Sala> buscarTodos(int id) {
        String sql = "SELECT * FROM salas WHERE id_cinema = ?";
        ArrayList<Sala> salas = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                salas.add(new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("id_cinema")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as salas: " + e.getMessage());
        }
        return salas;
    }

    @Override
    public ArrayList<Sala> buscarTodos() {
        String sql = "SELECT * FROM salas";
        ArrayList<Sala> salas = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {
                salas.add(new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("id_cinema")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as salas: " + e.getMessage());
        }
        return salas;
    }

    @Override
    public void atualizar(Sala sala) {
        String sql = "UPDATE salas SET id = ?, nome = ?, capacidade = ?, id_cinema = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getId());
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, sala.getCapacidade());
            stmt.setInt(4, sala.getId_cinema());
            stmt.executeUpdate();

            System.out.println("Atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Sala sala) {
        String sql = "DELETE FROM salas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getId());
            stmt.executeUpdate();

            System.out.println("Deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar sala: " + e.getMessage());
        }
    }
}
