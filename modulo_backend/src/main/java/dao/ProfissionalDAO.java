package dao;

import model.Profissional;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Classe ProfissionalDAO que herda a superclasse DAO - Data Access Object
 * Conexao com o BD e' feita pela classe Pai
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao construidas as query's SQL para acesso ao BD
 * 
 * A classe Profissional serve apenas para consulta e nao possui POST
 * 
 * @method construtor
 * @method finalize
 * @method getAll
 */
public class ProfissionalDAO extends DAO {
	/**
	 * Construtor padrao que referencia a classe Pai
	 */
	public ProfissionalDAO() {
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
	 * Metodo GET para retornar todos os profissionais
	 * Sera construida uma Pilha Simplismente Encadeada possuindo um TOPO de
	 * referencia e vazio
	 * 
	 * @see Profissional.java
	 * @print erro de existir
	 * @return Topo da Pilha
	 */
	public Profissional getAll() {
		// Objeto vazio para ser populado
		Profissional profissional = new Profissional();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.profissional";
			ResultSet rs = st.executeQuery(sql);
			// Para cada registro adicionar a pilha
			while (rs.next()) {
				Profissional p = new Profissional(rs.getInt("registro"), rs.getString("nome"), rs.getString("servico"),
						rs.getFloat("preco"),
						rs.getString("foto"), rs.getString("facebook"), rs.getString("twitter"),
						rs.getString("instagram"), rs.getString("linkedin"));
				profissional.add(p);
			}
			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return profissional;
	}
}