package service;

import dao.NotaDAO;
import model.Nota;
import spark.Request;
import spark.Response;

public class NotaService {

	private NotaDAO notaDAO = new NotaDAO();
	private String respostaJSON;
	// private final int FORM_INSERT = 1;
	// private final int FORM_DETAIL = 2;
	// private final int FORM_UPDATE = 3;

	// private final int FORM_ORDERBY_CHAVE = 1;
	// private final int FORM_ORDERBY_RESUMO = 2;

	public NotaService() {
	}

	/*
	 * public Object insert(Request request, Response response) {
	 * String login = request.queryParams("login");
	 * String senha = request.queryParams("senha");
	 * String sexo = request.queryParams("sexo");
	 * 
	 * String resp = "";
	 * 
	 * Nota nota = new Nota(-1, login, senha, sexo);
	 * 
	 * if(notaDAO.insert(nota) == true) {
	 * resp = "Nota (" + login + ") inserido!";
	 * response.status(201); // 201 Created
	 * } else {
	 * resp = "Nota (" + login + ") não inserido!";
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
		int token_usuario = Integer.parseInt(request.params(":token_usuario"));
		Nota nota = (Nota) notaDAO.get(token_usuario);

		if (nota != null) {
			response.status(200); // success
			respostaJSON = "";
			respostaJSON += "{ \"Notas\": [";
			respostaJSON += nota.toJson();
			respostaJSON += " ] }";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "Usuario " + token_usuario + " não encontrado.";
		}

		return respostaJSON;
	}

	public Object insert(Request request, Response response) {
		int token_usuario = Integer.parseInt(request.params(":token_usuario"));
		String body = request.body();
		boolean result = notaDAO.post(token_usuario, body);

		if (result) {
			response.status(200); // success
			respostaJSON = "bem sucedido! :D";
		} else {
			response.status(404); // 404 Not found
			respostaJSON = "mal sucedido! >:(";
		}

		return respostaJSON;
	}

	/*
	 * public Object getToUpdate(Request request, Response response) {
	 * int codigo = Integer.parseInt(request.params(":codigo"));
	 * Nota nota = (Nota) notaDAO.get(codigo);
	 * 
	 * if (nota != null) {
	 * response.status(200); // success
	 * makeForm(FORM_UPDATE, nota, FORM_ORDERBY_LOGIN);
	 * } else {
	 * response.status(404); // 404 Not found
	 * String resp = "Nota " + codigo + " não encontrado.";
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
	 * Nota nota = notaDAO.get(codigo);
	 * String resp = "";
	 * 
	 * if (nota != null) {
	 * nota.setLogin(request.queryParams("login"));
	 * nota.setSenha(request.queryParams("senha"));
	 * nota.setSexo(request.queryParams("sexo"));
	 * notaDAO.update(nota);
	 * response.status(200); // success
	 * resp = "Nota (Codigo " + nota.getCodigo() + ") atualizado!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp = "Nota (Codigo \" + nota.getCodigo() + \") não encontrado!";
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
	 * Nota nota = notaDAO.get(codigo);
	 * String resp = "";
	 * if (nota != null) {
	 * notaDAO.delete(codigo);
	 * response.status(200); // success
	 * resp = "Nota (" + codigo + ") excluído!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp = "Nota (" + codigo + ") não encontrado!";
	 * }
	 * makeForm();
	 * return form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 */
}