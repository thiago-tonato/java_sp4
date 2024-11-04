package br.com.fiap.infra;

import br.com.fiap.dominio.User;
import br.com.fiap.infra.dao.UserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTest {

    private UserDAO userDAO = new UserDAO();

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        boolean result = userDAO.saveUser(user);
        assertEquals(true, result);

        // Tentar salvar o mesmo usuário novamente
        result = userDAO.saveUser(user);
        assertEquals(false, result);
    }

    @Test
    public void testFindUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        userDAO.saveUser(user);

        User foundUser = userDAO.findUser("testuser");
        assertEquals(user.getUsername(), foundUser.getUsername());
    }
}
