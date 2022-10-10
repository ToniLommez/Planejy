package service;

import dao.ArtigoDAO;
import model.Artigo;
import spark.Request;
import spark.Response;

/**
 * Servico de tratamento de response/request para get/post do objeto Artigo
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao feitas as chamadas das DAO'S, redirecionamentos dos pedidos e
 * retorno com status de resposta
 * 
 * @method get
 * @method getAll
 */
public class ArtigoService {

	private ArtigoDAO artigoDAO = new ArtigoDAO();
	private String respostaJSON;

	/**
	 * Construtor padrao
	 */
	public ArtigoService() {
	}

	/**
	 * Metodo GET para responder com um JSON contendo o artigos requisitado
	 * 
	 * Utiliza o metodo artigoDAO.get(chave)
	 * 
	 * @see Artigo.java
	 * @see ArtigoDAO.java
	 * @request chave
	 * @response200 JSON (com objeto)
	 * @response404 nao encontrado
	 * @return Json com artigo ou erro
	 */
	public Object get(Request request, Response response) {
		int chave = Integer.parseInt(request.params(":chave"));
		Artigo artigo = (Artigo) artigoDAO.get(chave);

		if (artigo != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Articles\": [";
			respostaJSON += artigo.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Artigo " + chave + " não encontrado.";
		}

		return respostaJSON;
	}

	/**
	 * Metodo GET para responder com um JSON contendo todos os artigos encontrados
	 * no Banco de Dados
	 * 
	 * Utiliza o metodo artigoDAO.getAll()
	 * 
	 * @see Artigo.java
	 * @see ArtigoDAO.java
	 * @request vazio
	 * @response200 JSON (com objeto)
	 * @response404 nada encontrado
	 * @return Json com artigos ou erro
	 */
	public Object getAll(Request request, Response response) {
		Artigo artigo = (Artigo) artigoDAO.getAll();

		if (artigo != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Articles\": [";
			respostaJSON += artigo.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Artigos não encontrados.";
		}

		return respostaJSON;
	}
}