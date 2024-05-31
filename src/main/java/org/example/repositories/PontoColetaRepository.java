package org.example.repositories;

import org.example.model.PontoColeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PontoColetaRepository {
    public static final String URL_CONNECTION = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    public static final String USER = "rm553351";
    public static final String PASSWORD = "120303";

    public List<PontoColeta> findAll(){
        List<PontoColeta> listPontoColeta = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("SELECT * FROM pontos_coleta WHERE STATUS = 'ACCEPTED'");
                ResultSet rs = psmt.executeQuery()
        ){
            while (rs.next()){
                PontoColeta pontoColeta = new PontoColeta();
                pontoColeta.setName(rs.getString("NAME"));
                pontoColeta.setType(rs.getString("TYPE"));
                listPontoColeta.add(pontoColeta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPontoColeta;
    }

    public Optional<PontoColeta> findPontoColetaByName(String name){
        PontoColeta coleta = null;
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("SELECT * FROM pontos_coleta WHERE NAME = ? ORDER BY NAME")
        ){
            psmt.setString(1, name);
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                coleta = new PontoColeta();
                coleta.setName(rs.getString("NAME"));
                coleta.setType(rs.getString("TYPE"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(coleta);
    }

    public List<PontoColeta> findPendingPontoColeta(){
        List<PontoColeta> pendingPontoColetaList = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("SELECT * FROM pontos_coleta WHERE STATUS = 'PENDING'");
                ResultSet rs = psmt.executeQuery()
        ){
            while (rs.next()){
                PontoColeta pontoColeta = new PontoColeta();
                pontoColeta.setName(rs.getString("NAME"));
                pontoColeta.setType(rs.getString("TYPE"));
                pendingPontoColetaList.add(pontoColeta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingPontoColetaList;
    }

    public int acceptPontoColeta(String name){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("UPDATE pontos_coleta SET STATUS = 'ACCEPTED' WHERE NAME = ? AND STATUS = 'PENDING'")
        ){
            psmt.setString(1, name);
            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    public int rejectPontoColeta(String name){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("DELETE FROM pontos_coleta WHERE NAME = ?")
        ){
            psmt.setString(1, name);
            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int createPontoColeta(PontoColeta coleta){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("INSERT INTO pontos_coleta (NAME, TYPE, STATUS) VALUES (?, ?, 'PENDING')")
        ){
            psmt.setString(1, coleta.getName());
            psmt.setString(2, coleta.getType());

            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePontoColeta(PontoColeta coleta){
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("UPDATE pontos_coleta SET NAME=?,TYPE=? WHERE NAME = ?")
        ){
            psmt.setString(1, coleta.getName());
            psmt.setString(2, coleta.getType());
            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int deletePontoColeta(String name){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("DELETE FROM pontos_coleta WHERE NAME = ?")
        ){
            psmt.setString(1, name);
            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
