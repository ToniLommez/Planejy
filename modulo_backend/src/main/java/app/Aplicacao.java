package app;

import static spark.Spark.*;
import service.ArtigoService;
import service.NotaService;
import service.ProfissionalService;
import service.UsuarioService;
import spark.Filter;

public class Aplicacao {
    
    private static ArtigoService artigoService = new ArtigoService();
    private static NotaService notaService = new NotaService();
    private static ProfissionalService profissionalService = new ProfissionalService();
    private static UsuarioService usuarioService = new UsuarioService();
    
    public static void main(String[] args) {
        port(5678);

        staticFiles.location("/public");
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        });
        
        get("/articles/:chave", (request, response) -> artigoService.get(request, response));
        
        get("/articles/all/", (request, response) -> artigoService.getAll(request, response));
        
        get("/nota/get/:id_usuario", (request, response) -> notaService.get(request, response));
        
        post("/nota/post/:id_usuario", (request, response) -> notaService.insert(request, response));

        get("/profissional/all/", (request, response) -> profissionalService.getAll(request, response));
        
        post("/usuario/registrar/:nome/:nick/:email", (request, response) -> usuarioService.insert(request, response));

        get("/usuario/get/:token_usuario", (request, response) -> usuarioService.get(request, response));
        
        get("/usuario/login/:email/:token", (request, response) -> usuarioService.login(request, response));
           
        // get("/usuario/delete/:codigo", (request, response) -> usuarioService.delete(request, response));

             
    }
}