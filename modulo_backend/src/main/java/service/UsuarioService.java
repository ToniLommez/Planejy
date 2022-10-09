package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String respostaJSON;

	public UsuarioService() {
	}

	public Object get(Request request, Response response) {
		String token_usuario = request.params(":token_usuario");
		Usuario usuario = (Usuario) usuarioDAO.get(token_usuario);

		if (usuario != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Usuario\": [";
			respostaJSON += usuario.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Usuario " + token_usuario + " não encontrado.";
		}

		return respostaJSON;
	}

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

	public Object update(Request request, Response response) {
		String token = request.params(":token");
		int id = Integer.parseInt(request.params(":id"));
		String body = request.body();
		String result = usuarioDAO.update(token, id, body);

		response.status(200); // success ??
		respostaJSON = result;

		return respostaJSON;
	}

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

	public Object confirmarEmail(Request request, Response response) {
		String token_usuario = request.params(":token_usuario");
		String email = request.params(":email");
		int id =  usuarioDAO.confirmarEmail(token_usuario, email);

		response.status(200); // success ??
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [{";
		respostaJSON += "\"id\": " + id;
		respostaJSON += "} ] }";

		return respostaJSON;
	}

	public Object mudarSenhaToken(Request request, Response response) {
		String token_usuario = request.params(":token_usuario");
		String senha = request.body();
		int id =  usuarioDAO.mudarSenhaToken(token_usuario, senha);

		response.status(200); // success ??
		respostaJSON = "";
		respostaJSON += "{ \"Usuario\": [";
		respostaJSON += "\"id\": " + id;
		respostaJSON += " ] }";

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