package service;

import dao.ProfissionalDAO;
import model.Profissional;
import spark.Request;
import spark.Response;

public class ProfissionalService {

	private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
	private String respostaJSON;
	// private final int FORM_INSERT = 1;
	// private final int FORM_DETAIL = 2;
	// private final int FORM_UPDATE = 3;

	// private final int FORM_ORDERBY_CHAVE = 1;
	// private final int FORM_ORDERBY_RESUMO = 2;

	public ProfissionalService() {
	}

	/*
	 * public Object insert(Request request, Response response) {
	 * String login = request.queryParams("login");
	 * String senha = request.queryParams("senha");
	 * String sexo = request.queryParams("sexo");
	 * 
	 * String resp = "";
	 * 
	 * Profissional profissional = new Profissional(-1, login, senha, sexo);
	 * 
	 * if(profissionalDAO.insert(profissional) == true) {
	 * resp = "Profissional (" + login + ") inserido!";
	 * response.status(201); // 201 Created
	 * } else {
	 * resp = "Profissional (" + login + ") não inserido!";
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
	/*
	 * public Object get(Request request, Response response) {
	 * int chave = Integer.parseInt(request.params(":chave"));
	 * Profissional profissional = (Profissional) profissionalDAO.getAll(chave);
	 * 
	 * if (profissional != null) {
	 * response.status(200); // success
	 * respostaJSON = "";
	 * respostaJSON += "{ \"Profissionais\": [";
	 * respostaJSON += profissional.toJson();
	 * respostaJSON += " ] }";
	 * } else {
	 * response.status(404); // 404 Not found
	 * respostaJSON = "Profissional " + chave + " não encontrado.";
	 * }
	 * 
	 * return respostaJSON;
	 * }
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

	/*
	 * public Object getToUpdate(Request request, Response response) {
	 * int codigo = Integer.parseInt(request.params(":codigo"));
	 * Profissional profissional = (Profissional) profissionalDAO.get(codigo);
	 * 
	 * if (profissional != null) {
	 * response.status(200); // success
	 * makeForm(FORM_UPDATE, profissional, FORM_ORDERBY_LOGIN);
	 * } else {
	 * response.status(404); // 404 Not found
	 * String resp = "Profissional " + codigo + " não encontrado.";
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
	 * Profissional profissional = profissionalDAO.get(codigo);
	 * String resp = "";
	 * 
	 * if (profissional != null) {
	 * profissional.setLogin(request.queryParams("login"));
	 * profissional.setSenha(request.queryParams("senha"));
	 * profissional.setSexo(request.queryParams("sexo"));
	 * profissionalDAO.update(profissional);
	 * response.status(200); // success
	 * resp = "Profissional (Codigo " + profissional.getCodigo() + ") atualizado!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp =
	 * "Profissional (Codigo \" + profissional.getCodigo() + \") não encontrado!";
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
	 * Profissional profissional = profissionalDAO.get(codigo);
	 * String resp = "";
	 * if (profissional != null) {
	 * profissionalDAO.delete(codigo);
	 * response.status(200); // success
	 * resp = "Profissional (" + codigo + ") excluído!";
	 * } else {
	 * response.status(404); // 404 Not found
	 * resp = "Profissional (" + codigo + ") não encontrado!";
	 * }
	 * makeForm();
	 * return form.
	 * replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">"
	 * , "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp
	 * +"\">");
	 * }
	 */
}