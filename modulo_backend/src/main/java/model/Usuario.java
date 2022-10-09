package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Usuario {
    private int id;
    private String nome;
    private Date nascimento;
    private String nick;
    private String senha;
    private String email;
    private String genero;
    private String token;
    private SimpleDateFormat nascimentoFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Usuario() {
        this.id = -1;
        this.nome = "";
        this.nascimento = new Date();
        this.nick = "";
        this.senha = "";
        this.email = "";
        this.genero = "";
        this.token = "";
    }

    // email, nome, nascimento, nick, genero
    public Usuario(String body) {
        String tmp[] = body.split(";");

        this.id = -1;
        this.senha = "";
        this.token = "";

        this.email = tmp[0];
        this.nome = tmp[1];
        try {
            this.nascimento = nascimentoFormat.parse(tmp[2]);
        } catch (ParseException e) {}
        this.nick = tmp[3];
        this.genero = tmp[4];
    }

    public Usuario(int id, String nome, Date nascimento, String nick, String senha, String email, String genero,
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

    public Date get_nascimento() {
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
