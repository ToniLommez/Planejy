package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

/**
 * Servico de tratamento de response/request para get/post do objeto Usuario
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao feitas as chamadas das DAO'S, redirecionamentos dos pedidos e
 * retorno com status de resposta
 * 
 * @method get
 * @method insert
 * @method delete
 * @method update
 */
public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String respostaJSON;

	/**
	 * Construtor padrao
	 */
	public UsuarioService() {
	}

	/**
	 * Metodo GET para responder com um JSON contendo a Usuario requisitada
	 * 
	 * Utiliza o metodo usuarioDAO.get(tokenUsuario)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @request tokenUsuario
	 * @response200 JSON (com objeto)
	 * @response404 nao encontrado
	 * @return JSON com usuarios ou Erro
	 */
	public Object get(Request request, Response response) {
		String tokenUsuario = request.params(":tokenUsuario");
		Usuario usuario = (Usuario) usuarioDAO.get(tokenUsuario);

		if (usuario != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Usuario\": [";
			respostaJSON += usuario.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Usuario " + tokenUsuario + " não encontrado.";
		}

		return respostaJSON;
	}

	/**
	 * Metodo POST para inserir um usuario no Banco de Dados
	 * 
	 * Utiliza o metodo usuarioDAO.insert(nome, nick, senha, email)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @body senha
	 * @request nome
	 * @request nick
	 * @request email
	 * @response200 bem sucedido
	 * @return JSON com status de constraint
	 */
	public Object insert(Request request, Response response) {
		String nome = request.params(":nome");
		String nick = request.params(":nick");
		String email = request.params(":email");
		String senha = request.body();

		String result = usuarioDAO.insert(nome, nick, senha, email);

		response.status(200); // success ??
		respostaJSON = result;

		return respostaJSON;
	}

	/**
	 * Metodo POST para atualizar um usuario no Banco de Dados
	 * 
	 * Utiliza o metodo usuarioDAO.update(token, id, body)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @body email;nome;nascimento;nick;genero
	 * @request token
	 * @request id
	 * @response200 bem sucedido
	 * @return JSON com status de constraint
	 */
	public Object update(Request request, Response response) {
		String token = request.params(":token");
		int id = Integer.parseInt(request.params(":id"));
		String body = request.body();
		String result = usuarioDAO.update(token, id, body);

		response.status(200); // success ??
		respostaJSON = result;

		return respostaJSON;
	}

	/**
	 * Metodo POST para realizar Login de um usuario e cadastrar seu token de acesso no
	 * Banco de Dados
	 * 
	 * Utiliza o metodo usuarioDAO.login(email, senha, token)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @body senha
	 * @request email
	 * @request token
	 * @response200 bem sucedido
	 * @return JSON com Id do usuario logado
	 */
	public Object login(Request request, Response response) {
		String email = request.params(":email");
		String token = request.params(":token");
		String senha = request.body();
		int resposta = usuarioDAO.login(email, senha, token);

		response.status(200); // success
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [{";
		respostaJSON += "\"id\":" + resposta;
		respostaJSON += "} ] }";

		return respostaJSON;
	}

	/**
	 * Metodo GET para confirmar a existencia do email de um usuario para realizar a
	 * redefinicao de senha
	 * 
	 * Utiliza o metodo usuarioDAO.confirmarEmail(tokenUsuario, email)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @request tokenUsuario
	 * @request email
	 * @response200 bem sucedido
	 * @return JSON com Id do usuario
	 */
	public Object confirmarEmail(Request request, Response response) {
		String tokenUsuario = request.params(":tokenUsuario");
		String email = request.params(":email");
		int id = usuarioDAO.confirmarEmail(tokenUsuario, email);

		response.status(200); // success ??
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [{";
		respostaJSON += "\"id\": " + id;
		respostaJSON += "} ] }";

		return respostaJSON;
	}

	/**
	 * Metodo POST para executar a mudanca de senha de um usuario.
	 * Precisa da execucao do metodo confirmarEmail() para geracao do token
	 * 
	 * Utiliza o metodo usuarioDAO.mudarSenhaToken(tokenUsuario, senha)
	 * 
	 * @see Usuario.java
	 * @see usuarioDAO.java
	 * @body senha
	 * @request tokenUsuario
	 * @response200 bem sucedido
	 * @return JSON com Id do usuario
	 */
	public Object mudarSenhaToken(Request request, Response response) {
		String tokenUsuario = request.params(":tokenUsuario");
		String senha = request.body();
		int id = usuarioDAO.mudarSenhaToken(tokenUsuario, senha);

		response.status(200); // success ??
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [";
		respostaJSON += "\"id\": " + id;
		respostaJSON += " ] }";

		return respostaJSON;
	}
}