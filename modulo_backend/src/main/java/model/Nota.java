package model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Nota {
    private long chave;
    private int id_usuario;
    private String titulo;
    private LocalDate dia;
    private String descricao;
    private LocalTime horario;
    private String categoria;
    private String cor;
    private Nota next;

    public Nota() {
        this.chave = -1;
        this.id_usuario = -1;
        this.titulo = "";
        this.dia = LocalDate.now();
        this.descricao = "";
        this.horario = LocalTime.now();
        this.categoria = "";
        this.cor = "";
        this.next = null;
    }

    public Nota(long chave, int id_usuario, String titulo, LocalDate dia, String descricao, Time time,
            String categoria, String cor) {
        this.chave = chave;
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.dia = dia;
        this.descricao = descricao;
        this.horario = time.toLocalTime();
        this.categoria = categoria;
        this.cor = cor;
        this.next = null;
    }

    public long get_chave() {
        return this.chave;
    }

    public int get_id_usuario() {
        return this.id_usuario;
    }

    public String get_titulo() {
        return this.titulo;
    }

    public LocalDate get_dia() {
        return this.dia;
    }

    public String get_descricao() {
        return this.descricao;
    }

    public LocalTime get_horario() {
        return this.horario;
    }

    public String get_categoria() {
        return this.categoria;
    }

    public String get_cor() {
        return this.cor;
    }

    public Nota get_next() {
        return this.next;
    }

    public boolean hasNext() {
        boolean has = true;
        if (this.next == null){
            has = false;
        }
        return has;
    }

    public void add(Nota nova) {
        nova.next = this.next;
        this.next = nova;
    }

    public String toJson() {
        String Json = "";
        Nota last = this;
        
        while(last.hasNext()) {
            last = last.next;
            Json += "{ ";
            Json += "\"id\":" + last.chave + ", \"id_usuario\":" + last.id_usuario + ", \"title\":\"" + last.titulo + "\", \"start\":\"" + last.dia + "\", \"description\":\"" + last.descricao + "\", \"horario\":\"" + last.horario + "\", \"categoria\":\"" + last.categoria + "\", \"color\":\"" + last.cor + "\"";
            Json += "}";
            if(last.hasNext()) {
                Json += ",";                
            }
        }
        
        last = null;
        return Json;
    }
}