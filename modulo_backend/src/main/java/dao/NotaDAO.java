package dao;

import model.Nota;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe NotaDAO que herda a superclasse DAO - Data Access Object
 * Conexao com o BD e' feita pela classe Pai
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao construidas as query's SQL para acesso ao BD
 * 
 * @method construtor
 * @method finalize
 * @method get (All)
 * @method post
 * @method delete
 * @method update
 */
public class NotaDAO extends DAO {
	/**
	 * Construtor padrao que referencia a classe Pai
	 */
	public NotaDAO() {
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
	 * Metodo GET para retornar todas as Notas referentes a um unico usuario
	 * A selecao do Usuario e' feita atraves do token de acesso unico fornecido
	 * durante o login
	 * 
	 * As notas sao construidas em uma Pilha Simplismente Encadeada contendo um topo
	 * de referencia e vazio
	 * 
	 * @see Nota.java
	 * @print erro se existir
	 * @param chave token unico de acesso do usuario
	 * @return topo da pilha
	 */
	public Nota get(String tokenUsuario) {
		Nota notas = new Nota();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.nota WHERE id_usuario = ( SELECT id FROM planejy.usuario WHERE token = '"
					+ tokenUsuario + "' )";
			ResultSet rs = st.executeQuery(sql);
			// Para cada linha retornada adicionar uma nota a Pilha
			while (rs.next()) {
				Nota p = new Nota(rs.getLong("chave"), rs.getInt("id_usuario"), rs.getString("titulo"),
						rs.getDate("dia"),
						rs.getString("descricao"), rs.getTime("horario"), rs.getString("categoria"),
						rs.getString("cor"));
				notas.add(p);
			}
			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return notas;
	}

	/**
	 * Metodo POST para adicionar ao Banco de Dados a nota desejada
	 * 
	 * Os dados da nota sao recebidos atraves de uma String body que e' parseada no
	 * construtor da classe. E necessario um token de acesso unico do usuario para
	 * fazer a insercao da nota
	 * 
	 * @see Nota.java
	 * @print erro se existir
	 * @param token token de acesso unico do usuario
	 * @param body  dados da nota para construtor da classe
	 * @return status da query, true se bem sucedido
	 */
	public boolean post(String token, String body) {
		// Falso ate se tornar verdadeiro
		boolean status = false;
		// Criacao da nota atraves do body
		Nota nota = new Nota(body);
		try {
			// ID selecionado atraves do token
			String sql = "INSERT INTO planejy.nota (id_usuario, titulo, dia, descricao, horario, categoria, cor) VALUES ((SELECT id FROM planejy.usuario WHERE token = '"
					+ token + "'), '" + nota.getTitulo() + "', '" + nota.getDia().toString() + "', '"
					+ nota.getDescricao() + "', '" + nota.getHorario().toString() + "', '" + nota.getCategoria()
					+ "','" + nota.getCor() + "')";
			// Conexao
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			// Fechar conexao
			st.close();
			// Deu tudo certo!
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// limpeza da variavel
		nota = null;
		return status;
	}

	/**
	 * metodo DELETE para apagar uma nota do Banco de Dados
	 * 
	 * O metodo utiliza a selecao da nota atraves da chave da nota e a autenticacao
	 * da acao e' feita atraves do token unico de acesso do usuario
	 * 
	 * @print erro se existir
	 * @param tokenUsuario token de acesso unico do usuario
	 * @param chave        primary key da nota
	 * @return status da query, true se bem sucedido
	 */
	public boolean delete(String tokenUsuario, String chave) {
		// Falso ate se tornar verdadeiro
		boolean status = false;
		try {
			// Conexao
			Statement st = conexao.createStatement();
			// ID selecionado atraves do token
			st.executeUpdate("DELETE FROM planejy.nota WHERE chave = " + chave
					+ " AND id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "')");
			// Fechar conexao
			st.close();
			// Deu tudo certo!
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	/**
	 * Metodo UPDATE para atualizar uma nota no Banco de Dados
	 * 
	 * Os dados da nota sao recebidos atraves de uma String body que e' parseada
	 * no construtor da classe. E necessario um token de acesso unico do usuario
	 * para fazer a atualizacao da nota
	 * 
	 * @see Nota.java
	 * @print erro se existir
	 * @param token token de acesso unico do usuario
	 * @param chave primary key da nota
	 * @param body  dados da nota para construtor da classe
	 * @return status da query, true se bem sucedido
	 */
	public boolean update(String token, long chave, String body) {
		// Falso ate se tornar verdadeiro
		boolean status = false;
		// Criacao da nota atraves do body
		Nota nota = new Nota(body, chave);
		try {
			// Id selecionado atraves do token de validacao
			String sql = "UPDATE planejy.nota SET titulo = '" + nota.getTitulo() + "', dia = '"
					+ nota.getDia().toString() + "', descricao = '" + nota.getDescricao() + "', horario = '"
					+ nota.getHorario().toString() + "', categoria = '" + nota.getCategoria() + "', cor = '"
					+ nota.getCor() + "' WHERE chave = " + nota.getChave()
					+ " AND id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + token + "')";
			// conexao
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			// fechar conexao
			st.close();
			// Deu tudo certo!
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// limpeza da variavel
		nota = null;
		return status;
	}
}