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
        get("/hello", (request, response) -> "{\n"
        		+ "    \"Articles\": [{\n"
        		+ "            \"id\": \"1\",\n"
        		+ "            \"title\": \"Será que as notícias estão afetando a minha saúde mental?\",\n"
        		+ "            \"brief\": \"Você sabia que a quantidade de informação que consumimos pode afetar a nossa psique? Leia o artigo e entenda melhor como isso funciona.\",\n"
        		+ "            \"image\": \"images/article-1.png\",\n"
        		+ "            \"rating\": \"4\"\n"
        		+ "        },\n"
        		+ "        {\n"
        		+ "            \"id\": \"2\",\n"
        		+ "            \"title\": \"7 Dicas para melhorar a produtividade nos estudos\",\n"
        		+ "            \"brief\": \"This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.\",\n"
        		+ "            \"image\": \"images/article-2.png\",\n"
        		+ "            \"rating\": \"2.5\"\n"
        		+ "        }\n"
        		+ "    ]\n"
        		+ "}");
        
        // post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));

        // get("/usuario/:codigo", (request, response) -> usuarioService.get(request, response));
        
        // get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));

        // get("/usuario/update/:codigo", (request, response) -> usuarioService.getToUpdate(request, response));
        
        // post("/usuario/update/:codigo", (request, response) -> usuarioService.update(request, response));
           
        // get("/usuario/delete/:codigo", (request, response) -> usuarioService.delete(request, response));

             
    }
}