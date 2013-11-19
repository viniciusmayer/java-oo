package br.com.ftec.ads.poo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ftec.ads.poo.entidade.Usuario;

public class UsuarioDAO extends BaseDAO {

	private static UsuarioDAO usuarioDAO;

	/*
	 * factory method
	 */
	public static UsuarioDAO getInstance() {
		if (usuarioDAO == null) {
			usuarioDAO = new UsuarioDAO();
		}
		return usuarioDAO;
	}

	private UsuarioDAO() {
	}

	public Integer inserir(Usuario usuario) throws SQLException, ClassNotFoundException {
		PreparedStatement prepareStatement = null;
		prepareStatement = super.getConnection().prepareStatement("insert into usuario (email, senha) values (?, ?);");
		prepareStatement.setString(1, usuario.getEmail());
		prepareStatement.setString(2, usuario.getSenha());
		int executeUpdate = prepareStatement.executeUpdate();
		return executeUpdate;
	}

	public Integer deletarTodos() throws SQLException, ClassNotFoundException {
		PreparedStatement prepareStatement = null;
		prepareStatement = super.getConnection().prepareStatement("delete from usuario;");
		int executeUpdate = prepareStatement.executeUpdate();
		return executeUpdate;
	}

	public List<Usuario> selecionarTodos() throws SQLException, ClassNotFoundException {
		PreparedStatement prepareStatement = null;
		prepareStatement = this.getConnection().prepareStatement("select * from usuario;");

		ResultSet executeQuery = null;
		executeQuery = prepareStatement.executeQuery();

		List<Usuario> lu = new ArrayList<Usuario>();
		while (executeQuery.next()){
			long id = executeQuery.getLong("id");
			String email = executeQuery.getString("email");
			String senha = executeQuery.getString("senha");
			Usuario u = new Usuario(id, email, senha);
			lu.add(u);
		}
		return lu;
	}

	public List<Usuario> selecionarPorEmail(String email) throws SQLException, ClassNotFoundException {
		PreparedStatement prepareStatement = null;
		prepareStatement = this.getConnection().prepareStatement("select * from usuario where email like '%" + email + "%';");

		ResultSet executeQuery = null;
		executeQuery = prepareStatement.executeQuery();

		List<Usuario> lu = new ArrayList<Usuario>();
		while (executeQuery.next()){
			long id = executeQuery.getLong("id");
			String emailUsuario = executeQuery.getString("email");
			String senha = executeQuery.getString("senha");
			Usuario u = new Usuario(id, emailUsuario, senha);
			lu.add(u);
		}
		return lu;
	}

	public Integer deletar(List<Usuario> us) throws ClassNotFoundException, SQLException {
		String ids = "";
		for (Usuario usuario : us) {
			ids += usuario.getId();
			ids += ",";
		}
		ids = ids.substring(0, ids.length() - 1);

		PreparedStatement prepareStatement = null;
		prepareStatement = super.getConnection().prepareStatement("delete from usuario where id in ("+ids+");");

		int executeUpdate = prepareStatement.executeUpdate();
		return executeUpdate;
	}
}