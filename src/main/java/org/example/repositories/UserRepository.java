package org.example.repositories;

import org.example.model.User;
import java.sql.*;
import java.util.Optional;

public class UserRepository {
    public static final String URL_CONNECTION = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    public static final String USER = "rm553351";
    public static final String PASSWORD = "120303";

    public Optional<User> findByUsername(String username) {
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario WHERE USERNAME = ?")
        ) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean save(User user) {
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario (username, password) VALUES (?, ?)")
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOptional = findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

