package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String respostaJSON;
	// private final int FORM_INSERT = 1;
	// private final int FORM_DETAIL = 2;
	// private final int FORM_UPDATE = 3;

	// private final int FORM_ORDERBY_CHAVE = 1;
	// private final int FORM_ORDERBY_RESUMO = 2;

	public UsuarioService() {
	}

	/*
	 * public Object insert(Request request, Response response) {
	 * String login = request.queryParams("login");
	 * String senha = request.queryParams("senha");
	 * String sexo = request.queryParams("sexo");
	 * 
	 * String resp = "";
	 * 
	 * Usuario usuario = new Usuario(-1, login, senha, sexo);
	 * 
	 * if(usuarioDAO.insert(usuario) == true) {
	 * resp = "Usuario (" + login + ") inserido!";
	 * response.status(201); // 201 Created
	 * } else {
	 * resp = "Usuario (" + login + ") não inserido!";
	 * response.status(404); // 404 Not found
	 * }
	 * 
	 * makeForm();
	 * return form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 */

	public Object get(Request request, Response response) {
		String token_usuario = request.params(":token_usuario");
		Usuario usuario = (Usuario) usuarioDAO.get(token_usuario);

		if (usuario != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Usuario\": [{";
			respostaJSON += usuario.toJson();
			respostaJSON += "} ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Usuario " + token_usuario + " não encontrado.";
		}

		return respostaJSON;
	}

	public Object insert(Request request, Response response) {
		String sqlString = request.body();
		boolean result = usuarioDAO.post(sqlString);

		if (result) {
			response.status(200); // success
			respostaJSON = "bem sucedido! :D";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "mal sucedido! >:(";
		}

		return respostaJSON;
	}

	public Object login(Request request, Response response) {
		String email = request.params(":email");
		String senha = request.params(":senha");
		String token = request.params(":token");
		int resposta = usuarioDAO.login(email, senha, token);

		response.status(200); // success
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [{";
		respostaJSON += "\"id\":" + resposta;
		respostaJSON += "} ] }";

		return respostaJSON;
	}

	/*
	 * public Object getToUpdate(Request request, Response response) {
	 * int codigo = Integer.parseInt(request.params(":codigo"));
	 * Usuario usuario = (Usuario) usuarioDAO.get(codigo);
	 * 
	 * if (usuario != null) {
	 * response.status(200); // success
	 * makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_LOGIN);
	 * } else {
	 * response.status(404); // 404 Not found
	 * String resp = "Usuario " + codigo + " não encontrado.";
	 * makeForm();
	 * form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 * 
	 * return form;
	 * }
	 */

	/*
	 * public Object getAll(Request request, Response response) {
	 * int orderBy = Integer.parseInt(request.params(":orderby"));
	 * makeForm(orderBy);
	 * response.header("Content-Type", "text/html");
	 * response.header("Content-Encoding", "UTF-8");
	 * return form;
	 * }
	 */

	/*
	 * public Object update(Request request, Response response) {
	 * int codigo = Integer.parseInt(request.params(":codigo"));
	 * Usuario usuario = usuarioDAO.get(codigo);
	 * String resp = "";
	 * 
	 * if (usuario != null) {
	 * usuario.setLogin(request.queryParams("login"));
	 * usuario.setSenha(request.queryParams("senha"));
	 * usuario.setSexo(request.queryParams("sexo"));
	 * usuarioDAO.update(usuario);
	 * response.status(200); // success
	 * resp = "Usuario (Codigo " + usuario.getCodigo() + ") atualizado!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp = "Usuario (Codigo \" + usuario.getCodigo() + \") não encontrado!";
	 * }
	 * makeForm();
	 * return form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 */

	/*
	 * public Object delete(Request request, Response response) {
	 * int codigo = Integer.parseInt(request.params(":codigo"));
	 * Usuario usuario = usuarioDAO.get(codigo);
	 * String resp = "";
	 * if (usuario != null) {
	 * usuarioDAO.delete(codigo);
	 * response.status(200); // success
	 * resp = "Usuario (" + codigo + ") excluído!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp = "Usuario (" + codigo + ") não encontrado!";
	 * }
	 * makeForm();
	 * return form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 */
}