package dao;

import model.Profissional;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public Profissional getAll(String tokenUsuario) {
		// Objeto vazio para ser populado
		Profissional profissional = new Profissional();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.profissional";
			ResultSet rs = st.executeQuery(sql);
			// Para cada registro adicionar a pilha
			while (rs.next()) {
				double notas[] = getAvaliacao(rs.getInt("registro"));
				Profissional p = new Profissional(rs.getInt("registro"), rs.getString("nome"), rs.getString("servico"),
						rs.getFloat("preco"), rs.getString("foto"), rs.getString("facebook"), rs.getString("twitter"),
						rs.getString("instagram"), rs.getString("linkedin"),
						notas[0], (int) notas[1],
						getNotaUsuario(tokenUsuario, rs.getInt("registro")));
				profissional.add(p);
			}
			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return profissional;
	}

	/**
	 * Metodo POST para inserir a avaliacao de um profissional no banco de dados
	 * 
	 * @print erro de existir
	 * @return sucesso da operacao
	 */
	public boolean avaliar(String tokenUsuario, int registro_profissional, int nota) {
		// False ate se provar o contrario
		boolean status = false;
		try {
			// objetos de conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql;
			ResultSet rs;

			// Testar se ja existe avaliacao
			boolean existe = true;
			sql = "SELECT * FROM planejy.recomendacao_profissional ";
			sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "') ";
			sql += "AND registro_profissional = " + registro_profissional;
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				existe = false;
			}

			// se existir UPDATE, se nao INSERT
			if (existe) {
				sql = "UPDATE Planejy.Recomendacao_Profissional ";
				sql += "SET avaliacao = " + nota + ", cliques = (cliques + 1) ";
				sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "') ";
				sql += "AND registro_profissional = " + registro_profissional;
			} else {
				sql = "INSERT INTO Planejy.Recomendacao_Profissional (id_usuario, registro_profissional, cliques, avaliacao) ";
				sql += "VALUES ((SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "'), ";
				sql += registro_profissional + ", 1, " + nota + ");";
			}

			// execucao
			st.executeUpdate(sql);
			// fechar conexao
			st.close();
			// Deu tudo certo!
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return status;
	}

	public double[] getAvaliacao(int registro_profissional) {
		// Inicializacao de valores
		double totalNotas = 0;
		double numNotas = 0;
		double notaFinal = 0;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.Recomendacao_Profissional WHERE registro_profissional = "
					+ registro_profissional;
			ResultSet rs = st.executeQuery(sql);
			// Para cada registro atualizar valores
			while (rs.next()) {
				totalNotas += rs.getInt("avaliacao");
				numNotas++;
			}
			// Calcular nota
			if (numNotas > 0) {
				notaFinal = totalNotas / numNotas;
			}
			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			notaFinal = -1;
		}
		double result[] = new double[2];
		result[0] = notaFinal;
		result[1] = numNotas;
		return result;
	}

	public int getNotaUsuario(String tokenUsuario, int registro_profissional) {
		// Inicializacao de valores
		int notaUsuario = 0;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT avaliacao FROM planejy.Recomendacao_Profissional WHERE registro_profissional = "
					+ registro_profissional + " AND id_usuario = (SELECT id FROM planejy.usuario WHERE token = '"
					+ tokenUsuario + "') ";
			ResultSet rs = st.executeQuery(sql);
			// Para cada registro atualizar valores
			if (rs.next()) {
				notaUsuario = rs.getInt("avaliacao");
			}
			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return notaUsuario;
	}
}