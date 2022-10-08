package model;

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
    private Profissional next;

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
        this.next = null;
    }

    public Profissional(int registro, String nome, String servico, Float preco, String foto, String facebook,
            String twitter, String instagram, String linkedin) {
        this.registro = registro;
        this.nome = nome;
        this.servico = servico;
        this.preco = preco;
        this.foto = foto;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.next = null;
    }

    public int get_registro() {
        return this.registro;
    }

    public String get_nome() {
        return this.nome;
    }

    public String get_servico() {
        return this.servico;
    }

    public Float get_preco() {
        return this.preco;
    }

    public String get_foto() {
        return this.foto;
    }

    public String get_facebook() {
        return this.facebook;
    }

    public String get_twitter() {
        return this.twitter;
    }

    public String get_instagram() {
        return this.instagram;
    }

    public String get_linkedin() {
        return this.linkedin;
    }

    public Profissional get_next() {
        return this.next;
    }

    public boolean hasNext() {
        boolean has = true;
        if (this.next == null) {
            has = false;
        }
        return has;
    }

    public void add(Profissional novo) {
        novo.next = this.next;
        this.next = novo;
    }

    public String toJsonRec(Profissional tmp) {
        String Json = "";
        if (tmp != null) {
            Json += toJsonRec(tmp.next);
            Json += "{ ";
            Json += "\"registro\":" + tmp.registro + ",\"nome\":\"" + tmp.nome + "\",\"servico\":\"" + tmp.servico + "\",\"preco\":" + tmp.preco
                    + ",\"foto\":\"" + tmp.foto + "\",\"facebook\":\"" + tmp.facebook + "\",\"twitter\":\"" + tmp.twitter + "\",\"instagram\":\""
                    + tmp.instagram + "\",\"linkedin\":\"" + tmp.linkedin + "\"";
            Json += "}";
            Json += ",";
        }
        return Json;
    }

    public String toJson() {
        String Json = "";
        if (this.hasNext()) {
            Profissional last = this.next;
            Json += toJsonRec(last);
            Json = Json.substring(0, Json.length() - 1);
            last = null;
        } else {
            Json += "{ ";
            Json += "\"registro\":" + registro + ",\"nome\":\"" + nome + "\",\"servico\":\"" + servico + "\",\"preco\":" + preco
                    + ",\"foto\":\"" + foto + "\",\"facebook\":\"" + facebook + "\",\"twitter\":\"" + twitter + "\",\"instagram\":"
                    + instagram + "\",\"linkedin\":\"" + linkedin + "\"";
            Json += "}";
        }
        return Json;
    }
}
