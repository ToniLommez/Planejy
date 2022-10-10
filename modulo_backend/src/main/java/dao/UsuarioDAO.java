package dao;

import model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.*;
import java.math.*;

/**
 * Classe UsuarioDAO que herda a superclasse DAO - Data Access Object
 * Conexao com o BD e' feita pela classe Pai
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao construidas as query's SQL para acesso ao BD
 * 
 * @method construtor
 * @method finalize
 * @method md5
 * @method get
 * @method login
 * @method insert
 * @method update
 * @method confirmarEmail
 * @method mudarSenhaToke
 */
public class UsuarioDAO extends DAO {
	/**
	 * Construtor padrao que referencia a classe Pai
	 */
	public UsuarioDAO() {
		super();
		conectar();
	}

	/**
	 * Fechar conexao com o BD
	 */
	public void finalize() {
		close();
	}

	/**
	 * Metodo de criptografia em Hash md5
	 * 
	 * O tamanho da string gerada e' definida no toString do return
	 * 
	 * @print erro se existir
	 * @param s String a ser criptografada
	 * @return String criptografada ou "erro"
	 */
	public static String md5(String s) {
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			return (new BigInteger(1, m.digest()).toString(16));
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
		}
		return "erro";
	}

	/**
	 * Metodo GET para retornar os dados do usuario guardados no BD
	 * os dados do usuario serao recuperados atraves do Token de acesso unico do
	 * usuario que e' gerado durante o login
	 * 
	 * @see Usuario.java
	 * @print erro se existir
	 * @param token token unico de acesso do usuario
	 * @return objeto Usuario construido
	 */
	public Usuario get(String token) {
		// Objeto vazio para popular
		Usuario usuario = new Usuario();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.usuario WHERE token = '" + token + "'";
			ResultSet rs = st.executeQuery(sql);
			// Se receber, popular
			if (rs.next()) {
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getDate("nascimento"),
						rs.getString("nick"), rs.getString("senha"), rs.getString("email"),
						rs.getString("genero"), rs.getString("token"));
			}
			// Fim de conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	/**
	 * Metodo LOGIN para comparar se email e senha estao corretos e em seguida
	 * registrar um token de acesso unico no banco de dados
	 * A senha sera criptografada em md5
	 * 
	 * @see md5
	 * @print erro se existir
	 * @param email
	 * @param senha
	 * @param token para validacao do login
	 * @return id do usuario logado
	 */
	public int login(String email, String senha, String token) {
		// Criptografia
		senha = md5(senha);
		// Nao encontrado ate se provar o contrario
		int id = -1;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT id FROM planejy.usuario WHERE email = '" + email + "' AND senha = '" + senha + "'";
			ResultSet rs = st.executeQuery(sql);
			// Se retornar algo salvar
			if (rs.next()) {
				id = (rs.getInt("id"));
			} else {
				id = -1;
			}
			// Fim de conexao
			st.close();
			// Se Id for encontrado efetuar o login e registrar o Token de acesso unico
			if (id != -1) {
				// Nova Conexao para registro
				Statement stToken = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				String sqlToken = "UPDATE planejy.usuario SET token = '" + token + "' WHERE id = " + id;
				stToken.executeQuery(sqlToken);
				// Fim da conexao
				stToken.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

	/**
	 * Metodo POST para registrar um novo usuario no Banco de Dados
	 * 
	 * E importante dizer que os dados registrados nao correspondem a todos os dados
	 * existentes do usuario, demais dados devem utilizar o metodo update
	 * 
	 * A senha sera criptografada em md5 para depois o objeto Usuario ser cadastrado
	 * 
	 * @see md5
	 * @print erro se existir
	 * @param nome
	 * @param nick
	 * @param senha sem criptografia
	 * @param email
	 * @return Objeto JSON com o status de constraint do BD
	 */
	public String insert(String nome, String nick, String senha, String email) {
		// Objeto JSON com o status da constraint
		String result = "";
		// Criptografia da senha
		senha = md5(senha);
		try {
			// objetos de conexao
			Statement st;
			String sql;
			ResultSet rs;

			// Validacao de Nick
			st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "SELECT nick FROM planejy.usuario WHERE nick = '" + nick + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("{ \"Usuario\": [{ \"nick\":true , ");
			} else {
				result += ("{ \"Usuario\": [{ \"nick\":false , ");
			}

			// Validacao de Email
			sql = "SELECT email FROM planejy.usuario WHERE email = '" + email + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("\"email\":true , ");
			} else {
				result += ("\"email\":false , ");
			}

			// Insercao propriamente dita
			sql = "INSERT INTO planejy.usuario (nome, nick, senha, email) VALUES ('" + nome + "', '" + nick + "', '"
					+ senha + "', '" + email + "')";
			st.execute(sql);
			result += ("\"sucesso\":true } ] }");

			// Fim de conexao
			st.close();
		} catch (Exception e) {
			result += ("\"sucesso\":false } ] }");
			System.err.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Metodo UPDATE para atualizar os dados de um usuario no Banco de Dados
	 * 
	 * O metodo utiliza o token de acesso unico do usuario para validar a sua
	 * entrada Mas tambem utiliza o ID, por mesmo que redundante mas para seguranca
	 * da validacao
	 * 
	 * Os dados do usuario a serem atualizado sao recebidos atraves da string 'body'
	 * que sera parseada dentro do construtor da classe Usuario
	 * 
	 * @see Usuario.java
	 * @print erro se existir
	 * @param token token de validacao unico do usuario
	 * @param id
	 * @param body  dados a serem atualizados
	 * @return Objeto JSON com o status de constraint do BD
	 */
	public String update(String token, int id, String body) {
		// Objeto JSON com o status da constraint
		String result = "";
		// Criacao de um usuario a ser populado
		Usuario usuario = new Usuario(body);
		try {
			// Conexao
			Statement st;
			String sql;
			ResultSet rs;

			// Validacao de Nick
			st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "SELECT nick FROM planejy.usuario WHERE nick = '" + usuario.getNick() + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("{ \"Usuario\": [{ \"nick\":true , ");
			} else {
				result += ("{ \"Usuario\": [{ \"nick\":false , ");
			}

			// Validacao de Email
			sql = "SELECT email FROM planejy.usuario WHERE email = '" + usuario.getEmail() + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("\"email\":true , ");
			} else {
				result += ("\"email\":false , ");
			}

			// Update propriamente dito
			sql = "UPDATE planejy.usuario SET email = '" + usuario.getEmail() + "', nome = '" + usuario.getNome()
					+ "', nascimento = '" + usuario.getNascimento() + "', nick = '" + usuario.getNick()
					+ "', genero = '" + usuario.getGenero().charAt(0) + "'WHERE id = " + id + " AND token = '" + token
					+ "'";
			PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.executeUpdate();
			result += ("\"sucesso\":true } ] }");

			// fim de conexao
			preparedStatement.close();
			st.close();
		} catch (Exception e) {
			result += ("\"sucesso\":false } ] }");
			System.err.println(e.getMessage());
		}
		// limpeza da variavel
		usuario = null;
		return result;
	}

	/**
	 * Metodo para confirmacao de existencia de email para mudanca de senha
	 * Caso o email seja valido sera inserido no Banco de Dados um token de
	 * recuperacao de senha
	 * 
	 * @see mudarSenhaToken
	 * @print erro se exitir
	 * @param token token de recuperacao de senha
	 * @param email email a ser verificado
	 * @return id do usuario a ter a senha alterada
	 */
	public int confirmarEmail(String token, String email) {
		// Usuario nao encontrado ate se provar o contrario
		int id = -1;
		try {
			// Recuperar o ID equivalente ao email e Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT id FROM planejy.usuario WHERE email = '" + email + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = (rs.getInt("id"));
			} else {
				id = -1;
			}
			// Fim de conexao
			st.close();

			// Se o email existir o token de recuperacao de senha sera registrado a conta
			if (id != -1) {
				// Nova conexao
				Statement stToken = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				String sqlToken = "UPDATE planejy.usuario SET token = '" + token + "' WHERE id = " + id;
				stToken.executeQuery(sqlToken);
				// Fim de conexao
				stToken.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

	/**
	 * Metodo para efetivar a mudanca de senha do usuario
	 * 
	 * O metodo fara a mudanca de senha atraves da validacao do Token de recuperacao
	 * gerado. A senha entregue sera criptografada em MD5 antes de ser efetivamente
	 * armazenada
	 * 
	 * @see confirmarEmail
	 * @see md5
	 * @print erro se existir
	 * @param token token de recuperacao de senha
	 * @param senha nova senha a ser inserida
	 * @return id do usuario que teve a senha alterada
	 */
	public int mudarSenhaToken(String token, String senha) {
		// Usuario nao encontrado ate se provar o contrario
		int id = -1;
		// criptografia da senha
		senha = md5(senha);
		try {
			// Recuperacao do ID atraves do token gerado e conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT id FROM planejy.usuario WHERE token = '" + token + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = (rs.getInt("id"));
			} else {
				id = -1;
			}
			// Fim de conexao
			st.close();

			// Efetivar a mudanca de senha
			if (id != -1) {
				// Conexao
				Statement stToken = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				String sqlToken = "UPDATE planejy.usuario SET senha = '" + senha + "' WHERE id = " + id;
				stToken.executeQuery(sqlToken);
				// Fim de conexao
				stToken.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return id;
	}
}