package br.com.fiap.service;

import br.com.fiap.dominio.Endereco;
import br.com.fiap.dominio.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    private UserService userService = new UserService();

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEndereco(new Endereco());
        user.getEndereco().setCep("01001-000");

        String result = userService.registerUser(user);
        assertEquals("Usuário cadastrado com sucesso!", result);
    }

    @Test
    public void testLoginUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        userService.registerUser(user);

        boolean result = userService.loginUser("testuser", "password123");
        assertEquals(true, result);
    }
}