package DAOs;

import Cinemas.Cinema;
import Connections.Conexao;
import Exceptions.IdExistenteException;
import Main.Main;
import Sessoes.Sessao;
import Vendas.Venda;

import java.sql.*;
import java.util.ArrayList;

public class VendaDAO implements MasterDAO<Venda> {

    public VendaDAO() {
        criarTabela();
    }

    public void criarTabela() {
        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS vendas (" +
                    "id INT PRIMARY KEY," +
                    "cinema_id INT NOT NULL," +
                    "sessao_id INT NOT NULL," +
                    "preco FLOAT NOT NULL");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public Venda salvar(Venda venda) {
        String sql = "INSERT INTO vendas (id, cinema_id, sessao_id, preco) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getId());
            stmt.setInt(2, venda.getCinema().getId());
            stmt.setInt(3, venda.getSessao().getId());
            stmt.setFloat(4, venda.getPreco());
            stmt.executeUpdate();

            return venda;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar venda: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void existe(int id) throws IdExistenteException {
        String sql = "SELECT * FROM vendas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                throw new IdExistenteException("ID já existe");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de venda: " + e.getMessage());
        }
    }

    public Venda buscarPorId(int id) {
        String sql = "SELECT * FROM vendas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int cinemaId = rs.getInt("cinema_id");
                int sessaoId = rs.getInt("sessao_id");

                Cinema cinema = Main.cinemaDAO.buscarPorId(cinemaId);
                Sessao sessao = Main.sessaoDAO.buscarPorId(sessaoId);

                if (cinema != null && sessao != null) {
                    return new Venda(id, cinema, sessao, rs.getFloat("preco"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Venda> buscarTodos() {
        String sql = "SELECT * FROM vendas";
        ArrayList<Venda> vendas = new ArrayList<>();

        try (Connection conn = Conexao.obtemConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int cinemaId = rs.getInt("cinema_id");
                int sessaoId = rs.getInt("sessao_id");

                Cinema cinema = Main.cinemaDAO.buscarPorId(cinemaId);
                Sessao sessao = Main.sessaoDAO.buscarPorId(sessaoId);

                if (cinema != null && sessao != null) {
                    vendas.add(new Venda(rs.getInt("id"), cinema, sessao, rs.getFloat("preco")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as vendas: " + e.getMessage());
        }
        return vendas;
    }

    public void atualizar(Venda venda) {
        String sql = "UPDATE vendas SET cinema_id = ?, sessao_id = ?, preco = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getCinema().getId());
            stmt.setInt(2, venda.getSessao().getId());
            stmt.setFloat(3, venda.getPreco());
            stmt.setInt(4, venda.getId());
            stmt.executeUpdate();

            System.out.println("Venda atualizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void deletar(Venda venda) {
        String sql = "DELETE FROM vendas WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getId());
            stmt.executeUpdate();

            System.out.println("Venda deletada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar venda: " + e.getMessage());
        }
    }
}
