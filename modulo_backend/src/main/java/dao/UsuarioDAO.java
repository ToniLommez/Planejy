package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO extends DAO {
	public UsuarioDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	/*
	 * public Usuario get(int chave) {
	 * Usuario usuario = null;
	 * try {
	 * Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY);
	 * String sql = "SELECT * FROM planejy.usuario WHERE chave=" + chave;
	 * ResultSet rs = st.executeQuery(sql);
	 * if (rs.next()) {
	 * usuario = new Usuario(rs.getInt("chave"), rs.getString("imagem"),
	 * rs.getString("imagem_alt"),
	 * rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
	 * rs.getString("autor"), rs.getDate("dia").toLocalDate());
	 * }
	 * st.close();
	 * } catch (Exception e) {
	 * System.err.println(e.getMessage());
	 * }
	 * return usuario;
	 * }
	 */
	/*
	 * public Usuario get() {
	 * return get();
	 * }
	 */
	/*
	 * public List<Usuario> getOrderByCodigo() {
	 * return get("dia");
	 * }
	 */

	public Usuario get(String token) {
		Usuario usuario = new Usuario();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.usuario WHERE token = '" + token + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
		    // (int id, String nome, LocalDate nascimento, String nick, String senha, String email, String genero,String token)
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getDate("nascimento").toLocalDate(),
									   rs.getString("nick"), rs.getString("senha"), rs.getString("email"),
									   rs.getString("genero"), rs.getString("token"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public int login(String email, String senha, String token) {
		int id = -1;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT id FROM planejy.usuario WHERE email = '" + email + "' AND senha = '" + senha + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				id = (rs.getInt("id"));
			} else {
				id = -1;
			}
			st.close();
		    if (id != -1) {
		        System.out.printf("teste1");
		        Statement stToken = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        String sqlToken = "UPDATE planejy.usuario SET token = '" + token + "' WHERE id = " + id;
		        stToken.executeQuery(sqlToken);
		        stToken.close();
		    }
		} catch (Exception e) {
		    System.out.printf("teste2");
			System.err.println(e.getMessage());
		}
		return id;
	}

	public boolean post(String sql) {
		boolean status = false;
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	/*
	 * public boolean delete(int codigo) {
	 * boolean status = false;
	 * try {
	 * Statement st = conexao.createStatement();
	 * st.executeUpdate("DELETE FROM x WHERE codigo = " + codigo);
	 * st.close();
	 * status = true;
	 * } catch (SQLException u) {
	 * throw new RuntimeException(u);
	 * }
	 * return status;
	 * }
	 */

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