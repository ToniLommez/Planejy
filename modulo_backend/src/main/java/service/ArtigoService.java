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

	public Object get(Request request, Response response) {
		int chave = Integer.parseInt(request.params(":chave"));
		String tokenUsuario = request.params(":tokenUsuario");
		Artigo artigo = (Artigo) artigoDAO.get(chave, tokenUsuario);

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