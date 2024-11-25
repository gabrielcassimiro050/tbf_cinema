package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final int DB_PORT = 3306;
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "coltec";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "gm";

    private static Connection conexao = null;

    static {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("Falha ao carregar o Driver do JDBC MySQL: " + ex.getMessage());
        }
    }

    public static Connection obtemConexao() throws FalhaConexaoException {
        String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            }
            return conexao;
        } catch (SQLException e) {
            throw new FalhaConexaoException("Falha ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void fechaConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                conexao = null;
            }
        } catch (SQLException e) {
            System.err.println("Falha ao fechar a conexão: " + e.getMessage());
        }
    }
}
