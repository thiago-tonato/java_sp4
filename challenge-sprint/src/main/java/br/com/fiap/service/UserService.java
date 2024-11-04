package br.com.fiap.service;

import br.com.fiap.dominio.User;
import br.com.fiap.infra.dao.UserDAO;
import br.com.fiap.infra.viacep.ViaCepService;
import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private ViaCepService viaCepService = new ViaCepService();

    public String registerUser(User user) {
        if (!viaCepService.isValidCep(user.getEndereco().getCep(), user)) {
            return "CEP inválido!";
        }
        try {
            userDAO.saveUserToDatabase(user);
            return "Usuário cadastrado com sucesso!";
        } catch (SQLException e) {
            return "Erro ao cadastrar usuário: " + e.getMessage();
        }
    }

    public boolean loginUser(String username, String password) {
        User user = userDAO.findUser(username);
        return user != null && user.getPassword().equals(password);
    }
}