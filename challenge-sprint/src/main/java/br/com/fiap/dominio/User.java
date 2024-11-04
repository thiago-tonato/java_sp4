package br.com.fiap.dominio;

public class User {
    private String username;
    private String password;
    private Endereco endereco;  // Mudança aqui

    public User(){
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Endereco getEndereco() {  // Mudança aqui
        return endereco;
    }

    public void setEndereco(Endereco endereco) {  // Mudança aqui
        this.endereco = endereco;
    }
}

