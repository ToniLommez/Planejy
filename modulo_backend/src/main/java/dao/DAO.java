package dao;

import java.sql.*;

/**
 * Data Access Object
 * Metodo Pai para geradao das classes ObjetoDAO.java
 */
public class DAO {
    protected Connection conexao;

    /**
     * Construtor padrao
     */
    public DAO() {
        conexao = null;
    }

    /**
     * Conexao com o banco de dados
     * Insira aqui os dados utilizados para conectar no banco de dados
     * 
     * @print Conexao efetuada ou o erro gerado
     * @return Status de conexao
     */
    public boolean conectar() {
        // Dados para conexao
        String driverName = "org.postgresql.Driver";
        String serverName = "planejy.postgres.database.azure.com";
        String mydatabase = "Planejy";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "planejy@planejy";
        String password = "Temporario123";
        boolean status = false;

        // Conexao
        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    /**
     * Fechar a conexao com o banco de dados
     * 
     * @print erro (se gerado)
     * @return se a conexao foi fechada com sucesso ou nao
     */
    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
}