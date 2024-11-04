package br.com.fiap.controller;

import br.com.fiap.dominio.Endereco;
import br.com.fiap.dominio.User;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private final String BASE_URL = "http://localhost:8080/my-restful-api/api/users";


    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEndereco(new Endereco());
        user.getEndereco().setCep("01001-000");

        // Enviar requisição para registrar o usuário
        URL url = new URL(BASE_URL + "/register");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{"
                + "\"username\":\"" + user.getUsername() + "\","
                + "\"password\":\"" + user.getPassword() + "\","
                + "\"endereco\":{\"cep\":\"" + user.getEndereco().getCep() + "\"}"
                + "}";

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.writeBytes(jsonInputString);
            wr.flush();
        }

        // Ler a resposta
        int responseCode = conn.getResponseCode();
        assertEquals(201, responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();

        assertEquals("Usuário cadastrado com sucesso!", response);
    }

    @Test
    public void testLoginUser() throws Exception {
        // Enviar requisição para fazer login
        URL url = new URL(BASE_URL + "/login?username=testuser&password=password123");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        // Ler a resposta
        int responseCode = conn.getResponseCode();
        assertEquals(200, responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();

        assertEquals("Login bem-sucedido!", response);
    }
}