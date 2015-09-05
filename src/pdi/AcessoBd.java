/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Rodrigo
 */
public class AcessoBd {

    private ResultSet resultSet;
    private Statement statement;
    private final Connection connection;
    private PreparedStatement preparedStatement;    

    public AcessoBd() {
        connection = ClasseConexao.getConnection();
    }

    public List<Object[]> buscarDados(String sql, String[] cols) {
        try {

            Object[] colunas;
            List<Object[]> linhas = new ArrayList<>();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                colunas = new Object[cols.length];

                for (int i = 0; i < cols.length; i++) {
                    colunas[i] = resultSet.getObject(cols[i]);
                }

                linhas.add(colunas);
            }
            return linhas;

        } catch (Exception x) {

            Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
            return null;

        } finally {

            try {

                resultSet.close();
                statement.close();

            } catch (SQLException x) {

                Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);

            }

        }

    }

    public void executarInsert(String sql) {

        try {

            statement = connection.createStatement();
            statement.execute(sql);

        } catch (Exception x) {

            Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);

        } finally {

            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void salvarImagem(String path) {
        try {

            FileInputStream fis = null;
            File file = new File(path);

            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException x) {
                Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
            }

            preparedStatement = connection
                    .prepareStatement("INSERT INTO tab1 (imagem) VALUES (?)");
            preparedStatement.setBinaryStream(1, fis, (int) file.length());
            preparedStatement.executeUpdate();

        } catch (SQLException x) {

            Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);

        } finally {

            try {
                preparedStatement.close();
            } catch (SQLException x) {
                Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
            }

        }
    }

    public Image recuperarImagem(String sql) {
        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                InputStream is = resultSet.getBinaryStream(1);
                return ImageIO.read(is);
            } else {
                return null;
            }

        } catch (IOException | SQLException x) {

            Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
            return null;

        } finally {

            try {

                resultSet.close();
                statement.close();

            } catch (SQLException x) {
                Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
            }

        }
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException x) {
            Logger.getLogger(AcessoBd.class.getName()).log(Level.SEVERE, null, x);
        }
    }

}