package br.com.fiap.infra.dao;

import br.com.fiap.dominio.Endereco;
import br.com.fiap.dominio.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private Map<String, User> userDatabase = new HashMap<>();
    private ConnectionFactory connectionFactory;

    public UserDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public boolean saveUser(User user) {
        if (userDatabase.containsKey(user.getUsername())) {
            return false;
        }
        userDatabase.put(user.getUsername(), user);
        return true;
    }

    public void saveUserToDatabase(User user) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String sql = "INSERT INTO usuarios (username, password, cep, logradouro, uf) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEndereco().getCep());
                stmt.setString(4, user.getEndereco().getLogradouro());
                stmt.setString(5, user.getEndereco().getUf());
                stmt.executeUpdate();
            }
        }
    }

    public User findUser(String username) {
        Connection connection = connectionFactory.getConnection();
        String query = "SELECT username, password, cep, logradouro, uf FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                Endereco endereco = new Endereco();
                endereco.setCep(rs.getString("cep"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setUf(rs.getString("uf"));
                user.setEndereco(endereco);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
