package model;

import java.time.LocalDate;

public class Artigo {
    private int chave;
    private String imagem;
    private String imagem_alt;
    private String titulo;
    private String conteudo;
    private String resumo;
    private String autor;
    private LocalDate dataFabricacao;
    private Artigo next;

    public Artigo() {
        this.chave = -1;
        this.imagem = "";
        this.imagem_alt = "";
        this.titulo = "";
        this.conteudo = "";
        this.resumo = "";
        this.autor = "";
        this.dataFabricacao = LocalDate.now();
        this.next = null;
    }

    public Artigo(int chave, String imagem, String imagem_alt, String titulo, String conteudo, String resumo,
            String autor, LocalDate dataFabricacao) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagem_alt = imagem_alt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
        this.next = null;
    }

    public int get_chave() {
        return this.chave;
    }

    public String get_imagem() {
        return this.imagem;
    }

    public String get_imagem_alt() {
        return this.imagem_alt;
    }

    public String get_titulo() {
        return this.titulo;
    }

    public String get_conteudo() {
        return this.conteudo;
    }

    public String get_resumo() {
        return this.resumo;
    }

    public String get_autor() {
        return this.autor;
    }

    public LocalDate get_dataFabricacao() {
        return this.dataFabricacao;
    }

    public Artigo get_next() {
        return this.next;
    }

    public boolean hasNext() {
        boolean has = true;
        if (this.next == null) {
            has = false;
        }
        return has;
    }

    public void add(Artigo novo) {
        novo.next = this.next;
        this.next = novo;
    }

    public String toJsonRec(Artigo tmp) {
        String Json = "";
        if (tmp != null) {
            Json += toJsonRec(tmp.next);
            Json += "{ ";
            Json += "\"chave\":" + tmp.chave + ", \"imagem\":\"" + tmp.imagem + "\", \"imagem_alt\":\"" + tmp.imagem_alt
                    + "\", \"titulo\":\"" + tmp.titulo + "\", \"conteudo\":\"" + tmp.conteudo + "\", \"resumo\":\"" + tmp.resumo
                    + "\", \"autor\":\"" + tmp.autor + "\", \"dataFabricacao\":\"" + tmp.dataFabricacao + "\"";
            Json += "}";
            Json += ",";
        }
        return Json;
    }

    public String toJson() {
        String Json = "";
        Artigo last = this;

        Json += toJsonRec(last);
        Json += Json.substring(0, Json.length() - 1);
        last = null;
        return Json;
    }
}
