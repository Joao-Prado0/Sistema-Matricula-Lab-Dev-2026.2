package model;

public abstract class Usuario {

    private String nome;
    private String login;
    private String senha;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public boolean autenticar(String login, String senha) {
        return this.login != null && this.senha != null
                && this.login.equals(login) && this.senha.equals(senha);
    }
}
