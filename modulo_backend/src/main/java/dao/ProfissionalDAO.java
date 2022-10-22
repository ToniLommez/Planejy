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

	public Profissional getAll(String tokenUsuario) {
		// Objeto vazio para ser populado
		Profissional profissional = new Profissional();
		try {
			// Conexao
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			String sql = "";

			// Criar dicionario para traducao de classificacao do profissional
			sql += "SELECT * FROM planejy.classificacao_usuario ";
			sql += "WHERE id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + tokenUsuario + "')";
			rs = st.executeQuery(sql);
			rs.next();
			String nome[] = {"Trabalho", "Estudo", "Faculdade", "Curso", "Vida noturna", "Descanso", "Dia-a-dia", "Social", "Social Profissional", "saude"};
			int nota[] = {
				rs.getInt("trabalho"),
				rs.getInt("estudo"),
				rs.getInt("faculdade"),
				rs.getInt("curso"),
				rs.getInt("vida_noturna"),
				rs.getInt("descanso"),
				rs.getInt("dia_a_dia"),
				rs.getInt("social"),
				rs.getInt("social_profissional"),
				rs.getInt("saude")
			};
			Profissional.normalizarNotas(nota, nome);

			// Recuperar registros dos profissionais
			sql = "";
			sql += "SELECT A.*, string_agg(B.tipo_usuario, ',') AS classificacao ";
			sql += "FROM planejy.profissional AS A ";
			sql += "INNER JOIN planejy.tipo_de_usuario_do_profissional AS B ";
			sql += "ON A.registro = B.registro_profissional ";
			sql += "GROUP BY A.registro ";
			sql += "ORDER BY A.registro";
			rs = st.executeQuery(sql);
			// Para cada registro adicionar a pilha
			while (rs.next()) {
				double notas[] = getAvaliacao(rs.getInt("registro"));
				Profissional p = new Profissional(rs.getInt("registro"), rs.getString("nome"), rs.getString("servico"),
						rs.getFloat("preco"), rs.getString("foto"), rs.getString("facebook"), rs.getString("twitter"),
						rs.getString("instagram"), rs.getString("linkedin"), notas[0], (int) notas[1],
						getNotaUsuario(tokenUsuario, rs.getInt("registro")), rs.getString("classificacao"));
				p.notaFinal(nome, nota);
				profissional.add(p);
			}

			profissional.ordenar();

			// Fechar conexao
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return profissional;
	}

	public Profissional getNth(String tokenUsuario, int registro) {
		Profissional profissional = new Profissional();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "";
			sql += "SELECT A.*, string_agg(B.tipo_usuario, ',') AS classificacao ";
			sql += "FROM planejy.profissional AS A ";
			sql += "INNER JOIN planejy.tipo_de_usuario_do_profissional AS B ";
			sql += "ON A.registro = B.registro_profissional ";
			sql += "GROUP BY A.registro ";
			sql += "HAVING A.registro = " + registro;
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				double notas[] = getAvaliacao(rs.getInt("registro"));

				Profissional p = new Profissional(rs.getInt("registro"), rs.getString("nome"), rs.getString("servico"),
						rs.getFloat("preco"), rs.getString("foto"), rs.getString("facebook"), rs.getString("twitter"),
						rs.getString("instagram"), rs.getString("linkedin"), notas[0], (int) notas[1],
						getNotaUsuario(tokenUsuario, rs.getInt("registro")), rs.getString("classificacao"));
				profissional.add(p);
			}

			st.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return profissional;
	}

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

			// Se existir UPDATE, se nao INSERT
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
			// Execucao
			st.executeUpdate(sql);

			// Fechar conexao
			st.close();
			// Deu tudo certo!
			status = atualizarClassificacaoUsuario(tokenUsuario, registro_profissional);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return status;
	}

	public boolean atualizarClassificacaoUsuario(String tokenUsuario, int registro_profissional) {
		// False ate se provar o contrario
		boolean status = false;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql;
			ResultSet rs;

			// Recuperar a quantidade de classificacoes do profissional
			sql = "SELECT count(registro_profissional) FROM planejy.tipo_de_usuario_do_profissional ";
			sql += "WHERE registro_profissional = " + registro_profissional;
			rs = st.executeQuery(sql);
			rs.next();
			int numClassificacoes = rs.getInt("count");

			// Recuperar a classe das classificacoes/* */
			sql = "SELECT tipo_usuario FROM planejy.tipo_de_usuario_do_profissional ";
			sql += "WHERE registro_profissional = " + registro_profissional;
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