/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class ClasseConexao {

    private static ClasseConexao con = null;
    private static Connection connection = null;

    private ClasseConexao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClasseConexao.class.getName()).log(Level.SEVERE, "O driver do banco de dados não foi encontrado.", ex);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/pdi", "postgres", "manager");
        } catch (SQLException ex) {
            Logger.getLogger(ClasseConexao.class.getName()).log(Level.SEVERE, "Erro ao obter a conexão com o banco de dados.", ex);
        }
    }

    public static synchronized Connection getConnection() {
        if (con == null) {
            con = new ClasseConexao();
        }
        return connection;
    }

}
