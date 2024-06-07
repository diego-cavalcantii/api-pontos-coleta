package org.example.repositories;

import org.example.model.User;
import java.sql.*;
import java.util.Optional;

public class UserRepository {
    public static final String URL_CONNECTION = System.getenv("DATABASE_URL");
    public static final String USER = System.getenv("DATABASE_USER");
    public static final String PASSWORD = System.getenv("DATABASE_PASSWORD");


    public Optional<User> findByUserName(String email) {
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario WHERE USERNAME = ?")
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setCpf(rs.getString("CPF"));
                user.setName(rs.getString("NAME"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmail(rs.getString("EMAIL"));
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
                PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario (CPF,NAME,USERNAME,EMAIL,PASSWORD) VALUES (?, ?, ?, ?, ?)")
        ) {
            ps.setString(1, user.getCpf());
            ps.setString(2, user.getName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOptional = findByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

