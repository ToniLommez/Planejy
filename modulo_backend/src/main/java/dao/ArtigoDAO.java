package dao;

import model.Artigo;

// import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ArtigoDAO extends DAO {	
	public ArtigoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}

	public Artigo get(int chave) {
	    Artigo artigo = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.artigo WHERE chave="+chave;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 artigo = new Artigo(rs.getInt("chave"), rs.getString("imagem"), rs.getString("imagem_alt"), 
	                				 rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
	                				 rs.getString("autor"), rs.getDate("dia").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return artigo;
	}
	
	
	public List<Artigo> get() {
		return get("");
	}

	
	public List<Artigo> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	// public List<Artigo> getOrderByLogin() {
	// 	return get("login");		
	// }
	
	private List<Artigo> get(String orderBy) {
		List<Artigo> artigos = new ArrayList<Artigo>();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM planejy.artigo" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Artigo p = new Artigo(rs.getInt("chave"), rs.getString("imagem"), rs.getString("imagem_alt"), 
                                      rs.getString("titulo"), rs.getString("conteudo"), rs.getString("resumo"),
                                      rs.getString("autor"), rs.getDate("dia").toLocalDate());
	            artigos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return artigos;
	}
	
	/*
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			String sql = "UPDATE x SET login = '" + usuario.getLogin() + "', "
					   + "senha = '" + usuario.getSenha() + "', " 
					   + "sexo = '" + usuario.getSexo()
					   + "' WHERE codigo = " + usuario.getCodigo();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	*/
	
	/*
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM x WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	*/
	
	/*
	public int getLastCodigo() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM x");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"), 
	                		                  rs.getString("senha"), rs.getString("sexo"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		int novoCodigo = 0;
		if (usuarios != null) {
			novoCodigo = usuarios[usuarios.length-1].getCodigo() + 1;
		}
		else {
			novoCodigo = 1;
		}
		
		return novoCodigo;
	}
	*/
}