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
                pontoColeta.setCep(rs.getString("CEP"));
                pontoColeta.setLogradouro(rs.getString("LOGRADOURO"));
                pontoColeta.setNumero(rs.getString("NUMERO"));
                pontoColeta.setBairro(rs.getString("BAIRRO"));
                pontoColeta.setCidade(rs.getString("CIDADE"));
                pontoColeta.setUf(rs.getString("UF"));
                pontoColeta.setComplemento(rs.getString("COMPLEMENTO"));
                pontoColeta.setTelefone(rs.getString("TELEFONE"));
                pontoColeta.setImagemUrl(rs.getString("IMAGEM_URL"));
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
                coleta.setCep(rs.getString("CEP"));
                coleta.setLogradouro(rs.getString("LOGRADOURO"));
                coleta.setNumero(rs.getString("NUMERO"));
                coleta.setBairro(rs.getString("BAIRRO"));
                coleta.setCidade(rs.getString("CIDADE"));
                coleta.setUf(rs.getString("UF"));
                coleta.setComplemento(rs.getString("COMPLEMENTO"));
                coleta.setTelefone(rs.getString("TELEFONE"));
                coleta.setImagemUrl(rs.getString("IMAGEM_URL"));


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
                pontoColeta.setCep(rs.getString("CEP"));
                pontoColeta.setLogradouro(rs.getString("LOGRADOURO"));
                pontoColeta.setNumero(rs.getString("NUMERO"));
                pontoColeta.setBairro(rs.getString("BAIRRO"));
                pontoColeta.setCidade(rs.getString("CIDADE"));
                pontoColeta.setUf(rs.getString("UF"));
                pontoColeta.setComplemento(rs.getString("COMPLEMENTO"));
                pontoColeta.setTelefone(rs.getString("TELEFONE"));
                pontoColeta.setImagemUrl(rs.getString("IMAGEM_URL"));
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
                PreparedStatement psmt = conn.prepareStatement("INSERT INTO pontos_coleta (NAME, TYPE,CEP,LOGRADOURO,NUMERO,BAIRRO,CIDADE,UF,COMPLEMENTO,TELEFONE,IMAGEM_URL, STATUS) VALUES (?,?,?,?,?,?,?,?,?,?,?, 'PENDING')")
        ){
            psmt.setString(1, coleta.getName());
            psmt.setString(2, coleta.getType());
            psmt.setString(3, coleta.getCep());
            psmt.setString(4, coleta.getLogradouro());
            psmt.setString(5, coleta.getNumero());
            psmt.setString(6, coleta.getBairro());
            psmt.setString(7, coleta.getCidade());
            psmt.setString(8, coleta.getUf());
            psmt.setString(9, coleta.getComplemento());
            psmt.setString(10, coleta.getTelefone());
            psmt.setString(11, coleta.getImagemUrl());





            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePontoColeta(PontoColeta coleta){
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("UPDATE pontos_coleta SET NAME=?,TYPE=?,CEP=?,LOGRADOURO=?,NUMERO=?, BAIRRO=?,CIDADE=?,UF=?,COMPLEMENTO=?,TELEFONE=?,IMAGEM_URL=? WHERE NAME = ?")
        ){
            psmt.setString(1, coleta.getName());
            psmt.setString(2, coleta.getType());
            psmt.setString(3, coleta.getCep());
            psmt.setString(4, coleta.getLogradouro());
            psmt.setString(5, coleta.getNumero());
            psmt.setString(6, coleta.getBairro());
            psmt.setString(7, coleta.getCidade());
            psmt.setString(8, coleta.getUf());
            psmt.setString(9, coleta.getComplemento());
            psmt.setString(10, coleta.getTelefone());
            psmt.setString(11, coleta.getImagemUrl());

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
