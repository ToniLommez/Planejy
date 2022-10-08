package dao;

import model.Profissional;

// import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.SQLException;
import java.sql.Statement;

public class ProfissionalDAO extends DAO {
	public ProfissionalDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	/*
	 * public Profissional get(int chave) {
	 * Profissional profissional = null;
	 * try {
	 * Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY);
	 * String sql = "SELECT * FROM planejy.profissional WHERE chave=" + chave;
	 * ResultSet rs = st.executeQuery(sql);
	 * if (rs.next()) {
	 * profissional = new Profissional(rs.getInt("chave"), rs.getString("imagem"),
	 * rs.getString("imagem_alt"),
	 * rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
	 * rs.getString("autor"), rs.getDate("dia").toLocalDate());
	 * }
	 * st.close();
	 * } catch (Exception e) {
	 * System.err.println(e.getMessage());
	 * }
	 * return profissional;
	 * }
	 */
	public Profissional getAll() {
		Profissional profissional = new Profissional();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.profissional";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Profissional p = new Profissional(rs.getInt("registro"), rs.getString("nome"), rs.getString("servico"), rs.getFloat("preco"),
				                                  rs.getString("foto"), rs.getString("facebook"), rs.getString("twitter"), 
				                                  rs.getString("instagram"), rs.getString("linkedin"));
				profissional.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return profissional;
	}

	// public List<Profissional> getOrderByLogin() {
	// return get("login");
	// }

	/*
	 * public boolean update(Usuario usuario) {
	 * boolean status = false;
	 * try {
	 * String sql = "UPDATE x SET login = '" + usuario.getLogin() + "', "
	 * + "senha = '" + usuario.getSenha() + "', "
	 * + "sexo = '" + usuario.getSexo()
	 * + "' WHERE codigo = " + usuario.getCodigo();
	 * PreparedStatement st = conexao.prepareStatement(sql);
	 * st.executeUpdate();
	 * st.close();
	 * status = true;
	 * } catch (SQLException u) {
	 * throw new RuntimeException(u);
	 * }
	 * return status;
	 * }
	 */

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