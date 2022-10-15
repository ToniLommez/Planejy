package model;

import java.time.LocalDate;

/**
 * Objeto Artigo para ser usado de referencia para populacao do Banco de Dados
 * ou para construcao de JSON para o frontend
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao feitos os gets e construcao de JSON's
 * 
 * @method construtor de objeto vazio
 * @method construtor de objeto preenchido
 * @method hasnext/add (para pilha simplismente encadeada)
 * @method toJson
 */
public class Artigo {
    private int chave;
    private String imagem;
    private String imagemAlt;
    private String titulo;
    private String conteudo;
    private String resumo;
    private String autor;
    private LocalDate dataFabricacao;
    private double nota;
    private int notaUsuario;
    private Artigo next;

    /**
     * Construtor padrao
     */
    public Artigo() {
        this.chave = -1;
        this.imagem = "";
        this.imagemAlt = "";
        this.titulo = "";
        this.conteudo = "";
        this.resumo = "";
        this.autor = "";
        this.dataFabricacao = LocalDate.now();
        this.nota = 0;
        this.notaUsuario = 0;
        this.next = null;
    }

    public Artigo(int chave, String imagem, String imagemAlt, String titulo, String conteudo, String resumo,
            String autor, LocalDate dataFabricacao, double nota, int notaUsuario) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagemAlt = imagemAlt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
        this.nota = nota;
        this.notaUsuario = notaUsuario;
        this.next = null;
    }

    public Artigo(int chave, String imagem, String imagemAlt, String titulo, String conteudo, String resumo,
            String autor, LocalDate dataFabricacao, double nota) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagemAlt = imagemAlt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
        this.nota = nota;
        this.notaUsuario = 0;
        this.next = null;
    }

    public int getChave() {
        return this.chave;
    }

    public String getImagem() {
        return this.imagem;
    }

    public String getImagemAlt() {
        return this.imagemAlt;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public String getResumo() {
        return this.resumo;
    }

    public String getAutor() {
        return this.autor;
    }

    public LocalDate getDataFabricacao() {
        return this.dataFabricacao;
    }

    public Double getNota() {
        return this.nota;
    }

    public Artigo getNext() {
        return this.next;
    }

    /**
     * Metodo para lista encadeada
     * 
     * @return true se next nao for null
     */
    private boolean hasNext() {
        boolean has = true;
        if (this.next == null) {
            has = false;
        }
        return has;
    }

    /**
     * Metodo para construcao da Pilha encadeada
     * 
     * @param novo Artigo a ser inserido
     */
    public void add(Artigo novo) {
        novo.next = this.next;
        this.next = novo;
    }

    /**
     * Metodo recursivo para gerar o arquivo JSON da pilha encadeada em ordem
     * correta, afinal os valores sao adicionados de forma inversa, apenas
     * desempilhar ira gerar uma ordem invertida
     * 
     * @param artigo primeiro artigo da lista
     * @return String JSON com os dados da pilha
     */
    private String toJsonRec(Artigo artigo) {
        String Json = "";
        if (artigo != null) {
            // empilhamento recursivo
            Json += toJsonRec(artigo.next);
            // construcao da string apos empilhamento
            Json += "{ ";
            Json += "\"chave\":" + artigo.chave + ", \"imagem\":\"" + artigo.imagem + "\", \"imagemAlt\":\""
                    + artigo.imagemAlt
                    + "\", \"titulo\":\"" + artigo.titulo + "\", \"conteudo\":\"" + artigo.conteudo
                    + "\", \"resumo\":\""
                    + artigo.resumo
                    + "\", \"autor\":\"" + artigo.autor + "\", \"dataFabricacao\":\"" + artigo.dataFabricacao
                    + "\", \"nota\":\"" + artigo.nota + "\", \"notaUsuario\":\"" + artigo.notaUsuario + "\"";
            Json += "}";
            Json += ",";
        }
        return Json;
    }

    /**
     * Gerador padrao de JSON
     * 
     * Primeiro se analisa se e uma pilha ou um registro unico, caso seja uma pilha
     * sera chamado o metodo toJsonRec
     * 
     * @return JSON sem objeto pai
     */
    public String toJson() {
        String Json = "";
        // Se for pilha, chamar toJsonRec
        if (this.hasNext()) {
            Artigo last = this.next;
            Json += toJsonRec(last);
            Json = Json.substring(0, Json.length() - 1);
            // limpeza da variavel
            last = null;
        } else {
            Json += "{ ";
            Json += "\"chave\":" + chave + ", \"imagem\":\"" + imagem + "\", \"imagemAlt\":\"" + imagemAlt
                    + "\", \"titulo\":\"" + titulo + "\", \"conteudo\":\"" + conteudo + "\", \"resumo\":\"" + resumo
                    + "\", \"autor\":\"" + autor + "\", \"dataFabricacao\":\"" + dataFabricacao + "\", \"nota\":\""
                    + nota + "\", \"notaUsuario\":\"" + notaUsuario + "\"";
            Json += "}";
        }
        return Json;
    }
}
