package service;

import dao.ProfissionalDAO;
import model.Profissional;
import spark.Request;
import spark.Response;

/**
 * Servico de tratamento de response/request para get/post do objeto
 * Profissional
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao feitas as chamadas das DAO'S, redirecionamentos dos pedidos e
 * retorno com status de resposta
 * 
 * @method getAll
 */
public class ProfissionalService {

	private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
	private String respostaJSON;

	/**
	 * Construtor padrao
	 */
	public ProfissionalService() {
	}

	/**
	 * Metodo GET para responder com um JSON contendo todos os profissionais
	 * encontrados no Banco de Dados
	 * 
	 * Utiliza o metodo profissionalDAO.getAll()
	 * 
	 * @see Profissional.java
	 * @see ProfissionalDAO.java
	 * @request vazio
	 * @response200 JSON (com objeto)
	 * @response404 nada encontrado
	 * @return JSON com profissionais ou Erro
	 */
	public Object getAll(Request request, Response response) {
		Profissional profissional = (Profissional) profissionalDAO.getAll();

		if (profissional != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Profissional\": [";
			respostaJSON += profissional.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Profissionais não encontrados.";
		}

		return respostaJSON;
	}

}