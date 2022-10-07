package app;

import static spark.Spark.*;
import service.ArtigoService;
import service.NotaService;
import spark.Filter;

public class Aplicacao {
    
    private static ArtigoService artigoService = new ArtigoService();
    private static NotaService notaService = new NotaService();
    
    public static void main(String[] args) {
        port(5678);

        staticFiles.location("/public");
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
        
        get("/articles/:chave", (request, response) -> artigoService.get(request, response));
        
        get("/articles/all/", (request, response) -> artigoService.getAll(request, response));
        
        get("/nota/get/:id_usuario", (request, response) -> notaService.get(request, response));
        
        post("/nota/post/:id_usuario", (request, response) -> notaService.insert(request, response));

        // get("/usuario/:codigo", (request, response) -> usuarioService.get(request, response));
        
        // get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));

        // get("/usuario/update/:codigo", (request, response) -> usuarioService.getToUpdate(request, response));
        
        // post("/usuario/update/:codigo", (request, response) -> usuarioService.update(request, response));
           
        // get("/usuario/delete/:codigo", (request, response) -> usuarioService.delete(request, response));

             
    }
}