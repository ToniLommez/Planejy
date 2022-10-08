package dao;

import model.Usuario;

import java.sql.ResultSet;
import java.sql.Statement;
import java.security.*;
import java.math.*;

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
    public static String md5(String s){
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
	    senha = md5(senha);
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
		        Statement stToken = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        String sqlToken = "UPDATE planejy.usuario SET token = '" + token + "' WHERE id = " + id;
		        stToken.executeQuery(sqlToken);
		        stToken.close();
		    }
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

	public String insert(String nome, String nick, String senha, String email) {
		String result = "";
		senha = md5(senha);
		try {
			Statement st;
			String sql;
			ResultSet rs;
			st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "SELECT nick FROM planejy.usuario WHERE nick = '" + nick + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("{ \"Usuario\": [{ \"nick\":true , ");
			} else {
				result += ("{ \"Usuario\": [{ \"nick\":false , ");
			}

			
			sql = "SELECT email FROM planejy.usuario WHERE email = '" + email + "'";
			rs = st.executeQuery(sql);
			if (!rs.next()) {
				result += ("\"email\":true , ");
			} else {
				result += ("\"email\":false , ");
			}

			sql = "INSERT INTO planejy.usuario (nome, nick, senha, email) VALUES ('" + nome + "', '" + nick + "', '" + senha + "', '" + email + "')";
			st.execute(sql);

			result += ("\"sucesso\":true } ] }");

			st.close();
		} catch (Exception e) {
		    result += ("\"sucesso\":false } ] }");
			System.err.println(e.getMessage());
		}
		return result;
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