package dao;

import model.Nota;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NotaDAO extends DAO {
	public NotaDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	/*
	 * public Nota get(int chave) {
	 * Nota nota = null;
	 * try {
	 * Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY);
	 * String sql = "SELECT * FROM planejy.nota WHERE chave=" + chave;
	 * ResultSet rs = st.executeQuery(sql);
	 * if (rs.next()) {
	 * nota = new Nota(rs.getInt("chave"), rs.getString("imagem"),
	 * rs.getString("imagem_alt"),
	 * rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
	 * rs.getString("autor"), rs.getDate("dia").toLocalDate());
	 * }
	 * st.close();
	 * } catch (Exception e) {
	 * System.err.println(e.getMessage());
	 * }
	 * return nota;
	 * }
	 */

	/*
	 * public List<Nota> getOrderByCodigo() {
	 * return get("dia");
	 * }
	 */

	public Nota get(String token_usuario) {
		Nota notas = new Nota();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.nota WHERE id_usuario = ( SELECT id FROM planejy.usuario WHERE token = '"
					+ token_usuario + "' )";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Nota p = new Nota(rs.getLong("chave"), rs.getInt("id_usuario"), rs.getString("titulo"),
						rs.getDate("dia"),
						rs.getString("descricao"), rs.getTime("horario"), rs.getString("categoria"),
						rs.getString("cor"));
				notas.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return notas;
	}

	public boolean post(String token, String body) {
		boolean status = false;
		try {
			Nota nota = new Nota(body);
			String sql = "INSERT INTO planejy.nota (id_usuario, titulo, dia, descricao, horario, categoria, cor) VALUES ((SELECT id FROM planejy.usuario WHERE token = '"
					+ token + "'), '" + nota.get_titulo() + "', '" + nota.get_dia().toString() + "', '"
					+ nota.get_descricao() + "', '" + nota.get_horario().toString() + "', '" + nota.get_categoria()
					+ "','" + nota.get_cor() + "')";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean delete(String token_usuario, String chave) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM planejy.nota WHERE chave = " + chave + " AND id_usuario = (SELECT id FROM planejy.usuario WHERE token = '" + token_usuario + "')");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	/*
	 * public int getLastCodigo() {
	 * Usuario[] usuarios = null;
	 * 
	 * try {
	 * Statement st =
	 * conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.
	 * CONCUR_READ_ONLY);
	 * ResultSet rs = st.executeQuery("SELECT * FROM x");
	 * if(rs.next()){
	 * rs.last();
	 * usuarios = new Usuario[rs.getRow()];
	 * rs.beforeFirst();
	 * 
	 * for(int i = 0; rs.next(); i++) {
	 * usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"),
	 * rs.getString("senha"), rs.getString("sexo"));
	 * }
	 * }
	 * st.close();
	 * } catch (Exception e) {
	 * System.err.println(e.getMessage());
	 * }
	 * 
	 * int novoCodigo = 0;
	 * if (usuarios != null) {
	 * novoCodigo = usuarios[usuarios.length-1].getCodigo() + 1;
	 * }
	 * else {
	 * novoCodigo = 1;
	 * }
	 * 
	 * return novoCodigo;
	 * }
	 */
}