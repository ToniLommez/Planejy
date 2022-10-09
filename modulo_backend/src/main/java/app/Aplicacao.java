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
        
        // artigos
        get("/articles/:chave", (request, response) -> artigoService.get(request, response));
        
        get("/articles/all/", (request, response) -> artigoService.getAll(request, response));
        
        // profissionais
        get("/profissional/all/", (request, response) -> profissionalService.getAll(request, response));

        // notas
        get("/nota/get/:token_usuario", (request, response) -> notaService.get(request, response));
        
        post("/nota/post/:token_usuario", (request, response) -> notaService.insert(request, response));

        get("/nota/delete/:token_usuario/:chave", (request, response) -> notaService.delete(request, response));

        post("/nota/update/:token_usuario/:chave", (request, response) -> notaService.update(request, response));
        
        // usuario
        post("/usuario/registrar/:nome/:nick/:email", (request, response) -> usuarioService.insert(request, response));
        
        post("/usuario/Atualizar/:token/:id", (request, response) -> usuarioService.update(request, response));

        get("/usuario/get/:token_usuario", (request, response) -> usuarioService.get(request, response));
        
        post("/usuario/login/:email/:token", (request, response) -> usuarioService.login(request, response));

        get("/usuario/recuperarSenha/:email/:token_usuario", (request, response) -> usuarioService.confirmarEmail(request, response));

        post("/usuario/recuperarSenha/:token_usuario", (request, response) -> usuarioService.mudarSenhaToken(request, response));

        // get("/usuario/delete/:codigo", (request, response) -> usuarioService.delete(request, response));

             
    }
}