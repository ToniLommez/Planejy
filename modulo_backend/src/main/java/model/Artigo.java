package model;

import java.time.LocalDate;
<<<<<<< HEAD

public class Artigo {
    private int chave;
    private String imagem;
    private String imagem_alt;
    private String titulo;
    private String conteudo;
    private String resumo;
    private String autor;
    private LocalDate dataFabricacao;
    
    public Artigo() {
        this.chave = -1;
        this.imagem = "";
        this.imagem_alt = "";
        this.titulo = "";
        this.conteudo = "";
        this.resumo = "";
        this.autor = "";
        this.dataFabricacao = LocalDate.now();
    }
    
    public Artigo(int chave, String imagem, String imagem_alt, String titulo, String conteudo, String resumo, String autor, LocalDate dataFabricacao) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagem_alt = imagem_alt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
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

    public String toJson() {
        return "{ \"chave\":" + chave + ", \"imagem\":\"" + imagem + "\", \"imagem_alt\":\"" + imagem_alt + "\", \"titulo\":\"" + titulo + "\", \"conteudo\":\"" + conteudo + "\", \"resumo\":\"" + resumo + "\", \"autor\":\"" + autor + "\", \"dataFabricacao\":\"" + dataFabricacao + "\" }";
    }
=======
import java.time.temporal.ChronoUnit;

public class Artigo {
	private int chave;
	private String imagem;
	private String imagem_alt;
	private String titulo;
	private String conteudo;
	private String resumo;
	private String autor;
	private LocalDate dataFabricacao;
	
	public Artigo() {
		this.chave = -1;
		this.imagem = "";
		this.imagem_alt = "";
		this.titulo = "";
		this.conteudo = "";
		this.resumo = "";
		this.autor = "";
		this.dataFabricacao = LocalDate.now();
	}
	
	public Artigo(int chave, String imagem, String imagem_alt, String titulo, String conteudo, String resumo, String autor, LocalDate dataFabricacao) {
		this.chave = chave;
		this.imagem = imagem;
		this.imagem_alt = imagem_alt;
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.resumo = resumo;
		this.autor = autor;
		this.dataFabricacao = dataFabricacao;
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

	public String toJson() {
		return "Artigo [chave" + chave + "imagem" + imagem + "imagem_alt" + imagem_alt + "titulo" + titulo + "conteudo" + conteudo + "resumo" + resumo + "autor" + autor + "dataFabricacao" + dataFabricacao + "]";
	}
>>>>>>> ae92672a4c231a91a53a39ab29445850af60a8cb
}
