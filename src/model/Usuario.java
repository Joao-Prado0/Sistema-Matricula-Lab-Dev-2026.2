package model;

public abstract class Usuario {

    private String nome;
    private String login;
    private String senha;


    //setters getters
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

    //metodos complexos
    public boolean autenticar(String login, String senha){
        return true;
    }

}
