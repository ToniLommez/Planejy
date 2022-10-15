package model;

/**
 * Objeto Profissional para ser usado de referencia para populacao do Banco de
 * Dados ou para construcao de JSON para o frontend
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao feitos os gets e construcao de JSON's
 * 
 * @method construtor de objeto vazio
 * @method construtor de objeto preenchido
 * @method hasnext/add (para pilha simplismente encadeada)
 * @method toJson
 */
public class Profissional {
    private int registro;
    private String nome;
    private String servico;
    private Float preco;
    private String foto;
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
    private double nota;
    private int notaUsuario;
    private Profissional next;

    /**
     * Construtor padrao
     */
    public Profissional() {
        this.registro = -1;
        this.nome = "";
        this.servico = "";
        this.preco = (float) -1.0;
        this.foto = "";
        this.facebook = "";
        this.twitter = "";
        this.instagram = "";
        this.linkedin = "";
        this.nota = 0;
        this.notaUsuario = 0;
        this.next = null;
    }

    /**
     * Construtor populado
     * 
     * @param registro
     * @param nome
     * @param servico
     * @param preco
     * @param foto
     * @param facebook
     * @param twitter
     * @param instagram
     * @param linkedin
     * @param nota
     * @param notaUsuario
     */
    public Profissional(int registro, String nome, String servico, Float preco, String foto, String facebook,
            String twitter, String instagram, String linkedin, double nota, int notaUsuario) {
        this.registro = registro;
        this.nome = nome;
        this.servico = servico;
        this.preco = preco;
        this.foto = foto;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.nota = nota;
        this.notaUsuario = notaUsuario;
        this.next = null;
    }

    public int getRegistro() {
        return this.registro;
    }

    public String getNome() {
        return this.nome;
    }

    public String getServico() {
        return this.servico;
    }

    public Float getPreco() {
        return this.preco;
    }

    public String getFoto() {
        return this.foto;
    }

    public String getFacebook() {
        return this.facebook;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public String getInstagram() {
        return this.instagram;
    }

    public String getLinkedin() {
        return this.linkedin;
    }

    public Double getNota() {
        return this.nota;
    }

    public int getNotaUsuario() {
        return this.notaUsuario;
    }

    public Profissional getNext() {
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
    public void add(Profissional novo) {
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
    public String toJsonRec(Profissional tmp) {
        String Json = "";
        if (tmp != null) {
            // empilhamento recursivo
            Json += toJsonRec(tmp.next);
            // construcao da string apos empilhamento
            Json += "{ ";
            Json += "\"registro\":" + tmp.registro + ",\"nome\":\"" + tmp.nome + "\",\"servico\":\"" + tmp.servico
                    + "\",\"preco\":" + tmp.preco + ",\"foto\":\"" + tmp.foto + "\",\"facebook\":\"" + tmp.facebook
                    + "\",\"twitter\":\"" + tmp.twitter + "\",\"instagram\":\"" + tmp.instagram + "\",\"linkedin\":\""
                    + tmp.linkedin + "\",\"nota\":\"" + tmp.nota + "\",\"notaUsuario\":\"" + tmp.notaUsuario + "\"";
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
            Profissional last = this.next;
            Json += toJsonRec(last);
            Json = Json.substring(0, Json.length() - 1);
            // limpeza da variavel
            last = null;
        } else {
            Json += "{ ";
            Json += "\"registro\":" + registro + ",\"nome\":\"" + nome + "\",\"servico\":\"" + servico + "\",\"preco\":"
                    + preco
                    + ",\"foto\":\"" + foto + "\",\"facebook\":\"" + facebook + "\",\"twitter\":\"" + twitter
                    + "\",\"instagram\":"
                    + instagram + "\",\"linkedin\":\"" + linkedin + "\",\"nota\":\"" + nota + "\",\"notaUsuario\":\"" + notaUsuario + "\"";
            Json += "}";
        }
        return Json;
    }
}
