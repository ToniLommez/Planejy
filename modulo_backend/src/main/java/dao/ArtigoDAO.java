package dao;

import model.Artigo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe ArtigoDAO que herda a superclasse DAO - Data Access Object
 * Conexao com o BD e' feita pela classe Pai
 * 
 * Hierarquia de chamada: Aplicacao -> Service -> DAO -> Model
 * Aqui sao construidas as query's SQL para acesso ao BD
 * 
 * A classe Artigo serve apenas para consulta e nao possui POST
 * 
 * @method construtor
 * @method finalize
 * @method get
 * @method getAll
 */
public class ArtigoDAO extends DAO {
	/**
	 * Construtor padrao que referencia a classe Pai
	 */
	public ArtigoDAO() {
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
	 * Metodo GET para selecionar um artigo atraves de sua CHAVE
	 * 
	 * @see Artigo.java
	 * @print erro se existir
	 * @param chave Chave Primaria da tabela
	 * @return Objeto Artigo contendo os dados necessarios para consulta ou vazio se
	 *         um erro for gerado
	 */
	public Artigo get(int chave, String tokenUsuario) {
		Artigo artigo = null;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.artigo WHERE chave=" + chave;
			ResultSet rs = st.executeQuery(sql);
			// se algo for retornado, chamar o construtor
			if (rs.next()) {
				artigo = new Artigo(rs.getInt("chave"), rs.getString("imagem"), rs.getString("imagem_alt"),
						rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
						rs.getString("autor"), rs.getDate("dia").toLocalDate(), 
						getAvaliacao(rs.getInt("chave")),
						getNotaUsuario(tokenUsuario, rs.getInt("chave")));
			}
			// fechar a conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return artigo;
	}

	public Artigo getAll() {
		Artigo artigo = new Artigo();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.artigo";
			ResultSet rs = st.executeQuery(sql);
			// Para cada string retornada, adicionar a pilha
			while (rs.next()) {
				Artigo p = new Artigo(rs.getInt("chave"), rs.getString("imagem"), rs.getString("imagem_alt"),
						rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
						rs.getString("autor"), rs.getDate("dia").toLocalDate(),
						getAvaliacao(rs.getInt("chave")));
				artigo.add(p);
			}
			// fechar a conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return artigo;
	}

	public double getAvaliacao(int chave_artigo) {
		// Inicializacao de valores
		int totalNotas = 0;
		int numNotas = 0;
		double notaFinal = 0;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.Entrega_Artigo WHERE chave_artigo = "
					+ chave_artigo;
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
		return notaFinal;
	}

	public int getNotaUsuario(String tokenUsuario, int chave_artigo) {
		// Inicializacao de valores
		int notaUsuario = 0;
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT avaliacao FROM planejy.Entrega_Artigo WHERE chave_artigo = "
					+ chave_artigo + " AND id_usuario = (SELECT id FROM planejy.usuario WHERE token = '"
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

	public boolean avaliar(String tokenUsuario, int chave, int nota) {
		// False ate se provar o contrario
		boolean status = false;
		try {
			// objetos de conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql;
			ResultSet rs;

			// Testar se ja existe avaliacao
			boolean existe = true;
			sql = "SELECT * FROM Planejy.Entrega_artigo ";
			sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "') ";
			sql += "AND chave_artigo = " + chave;
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				existe = false;
			}

			// Se existir UPDATE, se nao INSERT
			if (existe) {
				sql = "UPDATE Planejy.Entrega_artigo ";
				sql += "SET avaliacao = " + nota + ", cliques = (cliques + 1) ";
				sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "') ";
				sql += "AND chave_artigo = " + chave;
			} else {
				sql = "INSERT INTO Planejy.Entrega_artigo (id_usuario, chave, cliques, avaliacao) ";
				sql += "VALUES ((SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "'), ";
				sql += chave + ", 1, " + nota + ");";
			}
			// Execucao
			st.executeUpdate(sql);

			// Fechar conexao
			st.close();
			// Deu tudo certo!
			status = atualizarClassificacaoUsuario(tokenUsuario, chave);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return status;
	}

	public boolean atualizarClassificacaoUsuario(String tokenUsuario, int chave_artigo) {
		// False ate se provar o contrario
		boolean status = false;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql;
			ResultSet rs;

			// Recuperar a quantidade de classificacoes do profissional
			sql = "SELECT count(chave_artigo) FROM planejy.tipo_de_usuario_do_artigo ";
			sql += "WHERE chave_artigo = " + chave_artigo;
			rs = st.executeQuery(sql);
			rs.next();
			int numClassificacoes = rs.getInt("count");

			// Recuperar a classe das classificacoes/* */
			sql = "SELECT tipo_usuario FROM planejy.tipo_de_usuario_do_artigo ";
			sql += "WHERE chave_artigo = " + chave_artigo;
			rs = st.executeQuery(sql);
			String classificacoes[] = new String[numClassificacoes];
			int i = 0;
			String tmp;
			while (rs.next()) {
				classificacoes[i] = rs.getString("tipo_usuario");
				tmp = "";
				for (int j = 0; j < classificacoes[i].length(); j++) {
					if (classificacoes[i].charAt(j) == '-') {
						tmp += "_";
					} else if (classificacoes[i].charAt(j) == ' ') {
						tmp += "_";
					} else {
						tmp += classificacoes[i].charAt(j);
					}
				}
				classificacoes[i] = tmp;
				i++;
			}

			// Atualizar a classificacao do usuario
			sql = "UPDATE planejy.classificacao_usuario ";
			sql += "SET ";
			for (i = 0; i < numClassificacoes; i++) {
				sql += classificacoes[i] + " = (" + classificacoes[i] + " + 1)";
				if (i < (numClassificacoes - 1)) {
					sql += ", ";
				} else {
					sql += " ";
				}
			}
			sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "') ";
			// Execucao
			st.executeUpdate(sql);

			// Fechar conexao
			st.close();
			// Deu tudo certo!
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}