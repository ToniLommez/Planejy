package app;

import static spark.Spark.*;
import spark.Filter;

public class Aplicacao {
	
	// private static UsuarioService usuarioService = new UsuarioService();
	
    public static void main(String[] args) {
        port(5678);
        
        staticFiles.location("/public");
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
        get("/articles/:usuario", (request, response) -> "articlesService.get(request, response)");
        
        // post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));

        // get("/usuario/:codigo", (request, response) -> usuarioService.get(request, response));
        
        // get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));

        // get("/usuario/update/:codigo", (request, response) -> usuarioService.getToUpdate(request, response));
        
        // post("/usuario/update/:codigo", (request, response) -> usuarioService.update(request, response));
           
        // get("/usuario/delete/:codigo", (request, response) -> usuarioService.delete(request, response));

             
    }
}