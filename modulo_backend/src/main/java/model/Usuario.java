package model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome;
    private LocalDate nascimento;
    private String nick;
    private String senha;
    private String email;
    private String genero;
    private String token;

    public Usuario() {
        this.id = -1;
        this.nome = "";
        this.nascimento = LocalDate.now();
        this.nick = "";
        this.senha = "";
        this.email = "";
        this.genero = "";
        this.token = "";
    }

    public Usuario(int id, String nome, LocalDate nascimento, String nick, String senha, String email, String genero,
            String token) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.nick = nick;
        this.senha = senha;
        this.email = email;
        this.genero = genero;
        this.token = token;
    }

    public int get_id() {
        return this.id;
    }

    public String get_nome() {
        return this.nome;
    }

    public LocalDate get_nascimento() {
        return this.nascimento;
    }

    public String get_nick() {
        return this.nick;
    }

    public String get_senha() {
        return this.senha;
    }

    public String get_email() {
        return this.email;
    }

    public String get_genero() {
        return this.genero;
    }

    public String get_token() {
        return this.token;
    }

    public String toJson() {
        String Json = "";
        Json += "{ ";
        Json +=  "\"id\":" + this.id + ", \"nome\":\"" + this.nome + "\", \"nascimento\":\"" + this.nascimento + "\", \"nick\":\"" + this.nick + "\", \"email\":\"" + this.email + "\", \"genero\":\"" + this.genero + "\"";
        Json += "}";
        return Json;
    }

}
