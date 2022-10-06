package service;

<<<<<<< HEAD
=======
import java.util.Scanner;
import java.io.File;
import java.util.List;
>>>>>>> ae92672a4c231a91a53a39ab29445850af60a8cb
import dao.ArtigoDAO;
import model.Artigo;
import spark.Request;
import spark.Response;


public class ArtigoService {

	private ArtigoDAO artigoDAO = new ArtigoDAO();
	private String respostaJSON;
	// private final int FORM_INSERT = 1;
	// private final int FORM_DETAIL = 2;
	// private final int FORM_UPDATE = 3;
<<<<<<< HEAD
	
	// private final int FORM_ORDERBY_CHAVE = 1;
	// private final int FORM_ORDERBY_RESUMO = 2;
=======
	private final int FORM_ORDERBY_CHAVE = 1;
	private final int FORM_ORDERBY_RESUMO = 2;
>>>>>>> ae92672a4c231a91a53a39ab29445850af60a8cb


	public ArtigoService() {
	}
	
	/* 
	public Object insert(Request request, Response response) {
		String login = request.queryParams("login");
		String senha = request.queryParams("senha");
		String sexo = request.queryParams("sexo");

		String resp = "";
		
		Artigo artigo = new Artigo(-1, login, senha, sexo);
		
		if(artigoDAO.insert(artigo) == true) {
            resp = "Artigo (" + login + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Artigo (" + login + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	 */
	
	public Object get(Request request, Response response) {
		int chave = Integer.parseInt(request.params(":chave"));		
		Artigo artigo = (Artigo) artigoDAO.get(chave);
		
<<<<<<< HEAD
        if (artigo != null) {
            response.status(200); // success
            respostaJSON = "";
            respostaJSON += "{ \"Articles\": [";
            respostaJSON += artigo.toJson();
            respostaJSON += " ] }";
=======
		if (artigo != null) {
			response.status(200); // success
			respostaJSON = artigo.toJson();
>>>>>>> ae92672a4c231a91a53a39ab29445850af60a8cb
        } else {
            response.status(404); // 404 Not found
            respostaJSON = "Artigo " + chave + " não encontrado.";   
        }

		return respostaJSON;
	}

	/* 
	public Object getToUpdate(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Artigo artigo = (Artigo) artigoDAO.get(codigo);
		
		if (artigo != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, artigo, FORM_ORDERBY_LOGIN);
        } else {
            response.status(404); // 404 Not found
            String resp = "Artigo " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	*/

	/* 
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}		
	*/	
	
	/* 
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Artigo artigo = artigoDAO.get(codigo);
        String resp = "";       

        if (artigo != null) {
        	artigo.setLogin(request.queryParams("login"));
        	artigo.setSenha(request.queryParams("senha"));
        	artigo.setSexo(request.queryParams("sexo"));
        	artigoDAO.update(artigo);
        	response.status(200); // success
            resp = "Artigo (Codigo " + artigo.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Artigo (Codigo \" + artigo.getCodigo() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
 	*/

	/* 
	public Object delete(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
        Artigo artigo = artigoDAO.get(codigo);
        String resp = "";       
        if (artigo != null) {
            artigoDAO.delete(codigo);
            response.status(200); // success
            resp = "Artigo (" + codigo + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Artigo (" + codigo + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	*/
}