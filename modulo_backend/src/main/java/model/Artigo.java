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
    private int numNotas;
    private int notaUsuario;
    private String classificacaoCrua;
    private String classificacao[];
    private double classificacaoFinal;
    private boolean jaEntrou;
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
        this.numNotas = 0;
        this.classificacao = null;
        this.classificacaoCrua = null;
        this.classificacaoFinal = 0;
        this.jaEntrou = false;
        this.next = null;
    }

    public Artigo(int chave, String imagem, String imagemAlt, String titulo, String conteudo, String resumo,
            String autor, LocalDate dataFabricacao, double nota, int numNotas, int notaUsuario) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagemAlt = imagemAlt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
        this.nota = nota;
        this.numNotas = numNotas;
        this.notaUsuario = notaUsuario;
        this.classificacao = null;
        this.classificacaoCrua = null;
        this.classificacaoFinal = 0;
        this.jaEntrou = false;
        this.next = null;
    }

    public Artigo(int chave, String imagem, String imagemAlt, String titulo, String conteudo, String resumo,
            String autor, LocalDate dataFabricacao, double nota, int numNotas, String classificacao, boolean jaEntrou) {
        this.chave = chave;
        this.imagem = imagem;
        this.imagemAlt = imagemAlt;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.resumo = resumo;
        this.autor = autor;
        this.dataFabricacao = dataFabricacao;
        this.nota = nota;
        this.numNotas = numNotas;
        this.notaUsuario = 0;
        this.classificacaoCrua = classificacao;
        this.classificacao = classificacao.split(",");
        this.classificacaoFinal = 0;
        this.jaEntrou = jaEntrou;
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

    public int getNumNotas() {
        return this.numNotas;
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

    public String[] getClassificacao() {
        return this.classificacao;
    }

    public String getClassificacaoCrua() {
        return this.classificacaoCrua;
    }

    public double getClassificacaoFinal() {
        return this.classificacaoFinal;
    }

    public boolean jaEntrou() {
        return this.jaEntrou;
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

    public void notaFinal(String nome[], int nota[]) {
        int n = this.classificacao.length;
        // Parse para correcao de padrao ortografico
        for (int i = 0; i < n; i++) {
            String tmp = "";

            for (int k = 0; k < nome.length; k++) {
                if (this.classificacao[i].equalsIgnoreCase(nome[k])) {
                    tmp = String.valueOf(nota[k]);
                    k = nome.length;
                }
            }
            this.classificacao[i] = tmp;
        }

        // insertion sort
        for (int i = 1; i < n; i++) {
            String tmp = this.classificacao[i];
            int j = i - 1;

            while ((j >= 0) && (this.classificacao[j].compareTo(tmp) < 0)) {
                this.classificacao[j + 1] = this.classificacao[j];
                j--;
            }
            this.classificacao[j + 1] = tmp;
        }

        String tmp = this.classificacao[0] + ".";
        for (int i = 1; i < n; i++) {
            tmp += this.classificacao[i];
        }
        tmp += "0";
        this.classificacaoFinal = Double.parseDouble(tmp);
    }

    public static void normalizarNotas(int nota[], String nome[]) {
        int n = nota.length;

        // Diminuir a extensao para otimizar a ordenacao
        float maior = nota[0];
        for (int i = 1; i < n; i++) {
            if (nota[i] > maior) {
                maior = nota[i];
            }
        }

        if (maior > 0) {
            // Normalizando
            for (int i = 0; i < n; i++) {
                nota[i] = (int) ((nota[i] / maior) * 100);
            }

            // CountingSort!!!
            // Array para contar o numero de ocorrencias de cada elemento
            int[] count = new int[101];
            int[] ordenado = new int[n];
            String[] nomeOrd = new String[n];

            // Inicializar cada posicao do array de contagem
            for (int i = 0; i < count.length; i++) {
                count[i] = 0;
            }

            // Agora, o count[i] contem o numero de elemento iguais a i
            for (int i = 0; i < n; i++) {
                count[nota[i]]++;
            }

            // Agora, o count[i] contem o numero de elemento menores ou iguais a i
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            // Ordenando
            for (int i = n - 1; i >= 0; i--) {
                nomeOrd[count[nota[i]] - 1] = nome[i];
                ordenado[count[nota[i]] - 1] = nota[i];
                count[nota[i]]--;
            }

            // Copiando para o array original
            for (int i = 0; i < n; i++) {
                nome[i] = nomeOrd[i];
                nota[i] = ordenado[i];
            }

            // Normalizando denovo!
            int inicio = 0;
            for (inicio = 0; nota[inicio] < 1;) {
                inicio++;
            }

            for (int i = 1; inicio < n;) {
                if (inicio < n - 1) {
                    if (nota[inicio] == nota[inicio + 1]) {
                        nota[inicio] = i;
                        inicio++;
                    } else {
                        nota[inicio] = i;
                        inicio++;
                        i++;
                    }
                } else {
                    nota[inicio] = i;
                    inicio++;
                    i++;
                }
            }
        }
    }

    public void ordenar() {
        // Contar o tamanho da pilha
        int n = 0;
        Artigo last = this;
        while (last.hasNext()) {
            n++;
            last = last.getNext();
        }

        // Desempilhar
        Artigo array[] = new Artigo[n];
        last = this.next;
        for (int i = 0; i < n; i++) {
            array[i] = last;
            last = last.getNext();
            array[i].next = null;
        }
        this.next = null;

        // Ordenar
        // Array para contar o numero de ocorrencias de cada elemento
        int count[] = new int[101];
        Artigo ordenado[] = new Artigo[n];

        // Inicializar cada posicao do array de contagem
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }

        // Agora, o count[i] contem o numero de elemento iguais a i
        for (int i = 0; i < n; i++) {
            count[(int) (array[i].getClassificacaoFinal() * 10)]++;
        }

        // Agora, o count[i] contem o numero de elemento menores ou iguais a i
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Ordenando
        for (int i = n - 1; i >= 0; i--) {
            ordenado[count[(int) (array[i].getClassificacaoFinal() * 10)] - 1] = array[i];
            count[(int) (array[i].getClassificacaoFinal() * 10)]--;
        }

        // Copiando para o array original
        for (int i = 0, j = n - 1; i < n; i++, j--) {
            array[j] = ordenado[i];
        }

        for (int i = 1; i < n; i++) {
            Artigo tmp = array[i];
            int j = i - 1;

            while ((j >= 0) && (array[j].classificacaoFinal < tmp.classificacaoFinal)) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = tmp;
        }

        // Empilhar
        for (int i = 0; i < n; i++) {
            this.add(array[i]);
        }
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
                    + artigo.imagemAlt + "\", \"titulo\":\"" + artigo.titulo + "\", \"conteudo\":\"" + artigo.conteudo
                    + "\", \"resumo\":\"" + artigo.resumo + "\", \"autor\":\"" + artigo.autor
                    + "\", \"dataFabricacao\":\"" + artigo.dataFabricacao + "\", \"nota\":\"" + artigo.nota
                    + "\", \"notaUsuario\":\"" + artigo.notaUsuario + "\", \"numNotas\":\"" + artigo.numNotas
                    + "\", \"classificacaoCrua\":\"" + artigo.classificacaoCrua + "\", \"jaEntrou\":\"" + artigo.jaEntrou + "\"";
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
                    + nota + "\", \"notaUsuario\":\"" + notaUsuario + "\", \"numNotas\":\"" + numNotas
                    + "\", \"classificacaoCrua\":\"" + classificacaoCrua + "\", \"jaEntrou\":\"" + jaEntrou + "\"";
            Json += "}";
        }
        return Json;
    }
}
