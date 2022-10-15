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

	/**
	 * Metodo POST para avaliar a classificacao de um profissional
	 * 
	 * Utiliza o metodo profissionalDAO.avaliar()
	 * 
	 * @see ProfissionalDAO.java
	 * @request tokenUsuario
	 * @request nota
	 * @response200 avaliado
	 * @response404 erro encontrado
	 * @return Mensagem a ser exibida
	 */
	/* public Object avaliar(Request request, Response response) {
		// :tokenUsuario/:nota
		String tokenUsuario = request.params(":tokenUsuario");
		int nota = Integer.parseInt(request.params(":nota"));

		boolean result = profissionalDAO.avaliar(tokenUsuario, nota);

		if (result) {
			response.status(200); // success
			respostaJSON = "avaliado com sucesso";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "erro ao avaliar";
		}

		return respostaJSON;
	} */
}