package DAOs;

import Connections.Conexao;
import Exceptions.IdExistenteException;
import Filmes.Filme;

import Salas.Sala;
import Sessoes.Sessao;

import javax.sound.midi.InvalidMidiDataException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Main.Main.filmeDAO;
import static Main.Main.salaDAO;

public class SessaoDAO implements MasterDAO<Sessao> {

    public SessaoDAO(){
        criarTabela();
    }

    public void criarTabela() {
        try (Connection conexao = Conexao.obtemConexao();
             Statement stmt = conexao.createStatement()) {


            stmt.execute("CREATE TABLE IF NOT EXISTS sessoes (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "id_sala INT NOT NULL," +
                    "id_filme INT NOT NULL," +
                    "data varchar(100) NOT NULL," +
                    "horario_fim varchar(100) NOT NULL" +
                    ")");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de sessões: " + e.getMessage());
        }
    }

    @Override
    public Sessao salvar(Sessao sessao) {
        String sql = "INSERT INTO sessoes (id_sala, id_filme, data, horario_fim) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());

            SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
            stmt.setString(3, formatoData.format(sessao.getData()));

            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            stmt.setString(4, formatoHora.format(sessao.getHorarioFim()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sessao.setId(rs.getInt(1));
            }

            return sessao;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar sessão: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar sessão", e); // Relança a exceção
        }
    }


    @Override
    public void existe(int id) throws IdExistenteException{
        String sql = "SELECT * FROM sessoes WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                throw new IdExistenteException("ID já existe");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar sessão: " + e.getMessage());
        }
    }

    @Override
    public Sessao buscarPorId(int id) {
        String sql = "SELECT s.id AS sessao_id, s.data, " +
                "sal.id AS sala_id, sal.nome AS sala_nome, sal.capacidade, sal.id_cinema, " +
                "f.id AS filme_id, f.nome AS filme_nome, f.duracao_s " +
                "FROM sessoes s " +
                "JOIN salas sal ON s.id_sala = sal.id " +
                "JOIN filmes f ON s.id_filme = f.id " +
                "WHERE s.id_sala = ?";
        SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Sala sala = new Sala(
                        rs.getInt("sala_id"),
                        rs.getString("sala_nome"),
                        rs.getInt("capacidade"),
                        rs.getInt("id_cinema")
                );

                Filme filme = new Filme(
                        rs.getInt("filme_id"),
                        rs.getString("filme_nome"),
                        rs.getLong("duracao_s")
                );

                Date data = null;
                try {
                    data = formatoData.parse(rs.getString("data"));
                } catch (ParseException e) {
                    System.err.println("Erro ao converter a data da sessão com ID " + rs.getInt("sessao_id") + ": " + e.getMessage());
                }

                return(new Sessao(
                        rs.getInt("sessao_id"),
                        sala,
                        filme,
                        data
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
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
                int id = rs.getInt("id");
                Sala sala = salaDAO.buscarPorId(rs.getInt("id_sala"));
                Filme filme = new Filme(rs.getInt("id_filme"), "Placeholder", 0);
                Date data = new Date(rs.getString("data"));
                sessoes.add(new Sessao(id, sala, filme, data));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }

    public ArrayList<Sessao> buscarTodos(int idSala) {
        String sql = "SELECT s.id AS sessao_id, s.data, " +
                "sal.id AS sala_id, sal.nome AS sala_nome, sal.capacidade, sal.id_cinema, " +
                "f.id AS filme_id, f.nome AS filme_nome, f.duracao_s " +
                "FROM sessoes s " +
                "JOIN salas sal ON s.id_sala = sal.id " +
                "JOIN filmes f ON s.id_filme = f.id " +
                "WHERE s.id_sala = ?";
        ArrayList<Sessao> sessoes = new ArrayList<>();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSala);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sala sala = new Sala(
                        rs.getInt("sala_id"),
                        rs.getString("sala_nome"),
                        rs.getInt("capacidade"),
                        rs.getInt("id_cinema")
                );

                Filme filme = new Filme(
                        rs.getInt("filme_id"),
                        rs.getString("filme_nome"),
                        rs.getLong("duracao_s")
                );

                Date data = null;
                try {
                    data = formatoData.parse(rs.getString("data"));
                } catch (ParseException e) {
                    System.err.println("Erro ao converter a data da sessão com ID " + rs.getInt("sessao_id") + ": " + e.getMessage());
                }

                sessoes.add(new Sessao(
                        rs.getInt("sessao_id"),
                        sala,
                        filme,
                        data
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as sessões: " + e.getMessage());
        }
        return sessoes;
    }



    @Override
    public void atualizar(Sessao sessao) {
        String sql = "UPDATE sessoes SET id = ?, id_sala = ?, id_filme = ?, data = ?, horario_fim = ? WHERE id = ?";

        try (Connection conn = Conexao.obtemConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setString(3, sessao.getData().toString());
            stmt.setString(4, sessao.getHorarioFim().toString());
            stmt.setInt(5, sessao.getId());
            stmt.executeUpdate();

            System.out.println("Atualizado com sucesso!");
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

            System.out.println("Deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar sessão: " + e.getMessage());
        }
    }
}
