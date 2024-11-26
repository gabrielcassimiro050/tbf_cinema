package DAOs;

import Connections.Conexao;
import Exceptions.IdExistenteException;
import Filmes.Filme;
import Salas.Sala;

import java.sql.*;
import java.util.ArrayList;


public class FilmeDAO implements MasterDAO<Filme>{

    public FilmeDAO(){
        criarTabela();
    }

    public void criarTabela() {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS filmes (id INT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "duracao_s BIGINT NOT NULL);");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public Filme salvar(Filme filme) {
        String sql = "INSERT INTO filmes (id, nome, duracao_s) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.setString(2, filme.getNome());
            stmt.setLong(3, filme.getDuracao_s());
            stmt.executeUpdate();

            return filme;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar filme: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void existe(int id) throws IdExistenteException{
        String sql = "SELECT * FROM filmes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                throw new IdExistenteException("ID já existe");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar filme: " + e.getMessage());
        }
    }

    public Filme buscarPorId(int id) {
        String sql = "SELECT * FROM filmes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filme por ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Filme> buscarTodos() {
        String sql = "SELECT * FROM filmes";
        ArrayList<Filme> filmes = new ArrayList<>();
        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                filmes.add(new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os filmes: " + e.getMessage());
        }
        return filmes;
    }

    public void atualizar(Filme filme) {
        String sql = "UPDATE filmes SET nome = ?, duracao_s = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNome());
            stmt.setLong(2, filme.getDuracao_s());
            stmt.setInt(3, filme.getId());
            stmt.executeUpdate();

            System.out.println("Atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    public void deletar(Filme filme) {
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getId());
            stmt.executeUpdate();

            System.out.println("Deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar filme: " + e.getMessage());
        }
    }
}
