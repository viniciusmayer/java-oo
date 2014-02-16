package br.com.ftec.ads.poo.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.ftec.ads.poo.dao.UsuarioDAO;
import br.com.ftec.ads.poo.entidade.Exportavel;
import br.com.ftec.ads.poo.entidade.Usuario;

public class Principal {

	private static final String ARQUIVOS_USUARIOS_CSV = "arquivos/usuarios.csv";

	public static void main(String[] args) {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<Exportavel> objetosExportaveis = null;
		Scanner scanner = new Scanner(System.in);
		String opcao = null;
		do {
			opcao = menu(scanner);
			switch (opcao) {
			case "1":
				criarUsuario(usuarios, scanner);
				break;
			case "2":
				objetosExportaveis = new ArrayList<Exportavel>(usuarios);
				listar(objetosExportaveis);
				break;
			case "3":
				deletarTodosUsuarios(usuarios, scanner);
				break;
			case "4":
				pesquisarUsuarios(usuarios, scanner);
				break;
			case "5":
				deletar(usuarios, scanner);
				break;
			case "6":
				objetosExportaveis = new ArrayList<Exportavel>(usuarios);
				exportar(objetosExportaveis, ARQUIVOS_USUARIOS_CSV);
				break;
			case "7":
				importar(usuarios, ARQUIVOS_USUARIOS_CSV, scanner);
				break;
			case "8":
				inserirUsuario(scanner);
				break;
			case "9":
				selecionarTodosUsuarios();
				break;
			case "10":
				deletarTodosUsuarios(scanner);
				break;
			case "11":
				selecionarUsuarios(scanner);
				break;
			case "12":
				deletarUsuarios(scanner);
				break;
			case "13":
				exportarUsuarios();
				break;
			case "14":
				importarUsuarios(usuarios, scanner);
				break;
			default:
				break;
			}
		} while (!opcao.equals("0"));
	}

	private static void importarUsuarios(List<Usuario> usuarios, Scanner scanner) {
		importar(usuarios, ARQUIVOS_USUARIOS_CSV, scanner);
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		Integer i = 0;
		for (Usuario usuario: usuarios) {
			try {
				i += usuarioDAO.inserir(usuario);
			} catch (ClassNotFoundException e) {
				System.err.println("-> Erro ao inserir usuarios: " + e.getMessage());
				e.printStackTrace();
				return;
			} catch (SQLException e) {
				System.err.println("-> Erro ao inserir usuarios: " + e.getMessage());
				e.printStackTrace();
				return;
			}
		}
		System.out.println("-> "+i+" usuarios inseridos com sucesso");
	}

	private static void exportarUsuarios() {
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		try {
			List<Usuario> selecionarTodos = usuarioDAO.selecionarTodos();
			List<Exportavel> objetos = new ArrayList<Exportavel>(selecionarTodos);
			exportar(objetos, ARQUIVOS_USUARIOS_CSV);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deletarUsuarios(Scanner scanner) {
		try {
			List<Usuario> us = pesquisarUsuariosParaDeletar(scanner);
			List<Exportavel> objetos = new ArrayList<Exportavel>(us);
			listar(objetos);
			deletarUsuarios(us, scanner);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("-> Erro ao selecionar/deletar os usuarios: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("-> Erro ao selecionar/deletar os usuarios: " + e.getMessage());
		}
	}

	private static List<Usuario> pesquisarUsuariosParaDeletar(Scanner scanner) throws ClassNotFoundException, SQLException {
		System.out.println();
		System.out.println("=> Pesquisar usuarios");
		System.out.print("Email: ");
		String email = scanner.nextLine();

		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		List<Usuario> usuarios = usuarioDAO.selecionarPorEmail(email);
		return usuarios;
	}

	private static void deletarUsuarios(List<Usuario> us, Scanner scanner) throws ClassNotFoundException, SQLException {
		System.out.println();
		System.out.println("=> Deletar os usuarios encontrados");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}

		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		Integer deletarTodos = usuarioDAO.deletar(us);
		System.out.println("-> " + deletarTodos + " objetos deletados com sucesso");
	}

	private static void selecionarUsuarios(Scanner scanner) {
		System.out.println();
		System.out.println("=> Pesquisar usuarios");
		System.out.print("Email: ");
		String email = scanner.nextLine();

		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		try {
			List<Usuario> usuarios = usuarioDAO.selecionarPorEmail(email);
			List<Exportavel> objetos = new ArrayList<Exportavel>(usuarios);
			listar(objetos);
		} catch (ClassNotFoundException e) {
			System.err.println("-> Erro ao selecionar os usuarios: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("-> Erro ao selecionar os usuarios: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static void deletarTodosUsuarios(Scanner scanner) {
		System.out.println();
		System.out.println("=> Deletar todos os objetos");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}

		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		try {
			Integer deletarTodos = usuarioDAO.deletarTodos();
			System.out.println("-> " + deletarTodos + " objetos deletados com sucesso");
		} catch (ClassNotFoundException e) {
			System.err.println("-> Erro ao deletar todos os usuarios: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("-> Erro ao deletar todos os usuarios: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void selecionarTodosUsuarios() {
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		try {
			List<Usuario> selecionarTodos = usuarioDAO.selecionarTodos();
			List<Exportavel> objetos = new ArrayList<Exportavel>(selecionarTodos);
			listar(objetos);
		} catch (ClassNotFoundException e) {
			System.err.println("-> Erro ao selecionar todos os usuarios: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("-> Erro ao selecionar todos os usuarios: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void inserirUsuario(Scanner scanner) {
		System.out.println();
		System.out.println("=> Inserir usuario");
		System.out.print("-> Email: ");
		String email = scanner.nextLine();
		String senha = null;
		String confirmarSenha = null;
		do {
			System.out.print("-> Senha: ");
			senha = scanner.nextLine();
			System.out.print("-> Confirmar senha: ");
			confirmarSenha = scanner.nextLine();
		} while (!senha.equals(confirmarSenha));
		Usuario usuario = new Usuario(email, senha);
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		try {
			Integer i = usuarioDAO.inserir(usuario);
			System.out.println("-> " + i + " usuario inserido com sucesso");
		} catch (ClassNotFoundException e) {
			System.err.println("-> Erro ao inserir usuario: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("-> Erro ao inserir usuario: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void importar(List<Usuario> usuarios, String nomeArquivo, Scanner scanner) {
		System.out.println();
		System.out.println("=> Importar usuarios");
		System.out.println("-> Todos os usuarios existentes serao deletados.");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}
		usuarios.clear();
		System.out.println("-> Usuarios deletados com sucesso");
		File file = new File(nomeArquivo);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("-> Erro ao importar usuarios (arquivo nao encontrado): " + e.getMessage());
			e.printStackTrace();
			return;
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String readLine = null;
		try {
			while ((readLine = bufferedReader.readLine()) != null) {
				String[] split = readLine.split(";");
				String idAsString = split[0];
				Long id = new Long(idAsString);
				String email = split[1];
				String senha = split[2];
				Usuario usuario = new Usuario(id, email, senha);
				usuarios.add(usuario);
			}
			System.out.println("-> Usuarios importados com sucesso");
		} catch (IOException e) {
			System.err.println("-> Erro ao importar usuarios (ler arquivo): " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void exportar(List<Exportavel> objetos, String nomeArquivo) {
		System.out.println();
		System.out.println("=> Exportar objetos");
		File file = new File(nomeArquivo);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			System.err.println("-> Erro ao exportar (criar o arquivo): " + e.getMessage());
			e.printStackTrace();
			return;
		}
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			for (Exportavel exportavel : objetos) {
				bufferedWriter.write(exportavel.exportar());
			}
		} catch (IOException e) {
			System.err.println("-> Erro ao exportar (escrever no arquivo): " + e.getMessage());
			e.printStackTrace();
			return;
		}
		try {
			bufferedWriter.close();
			System.out.println("-> Objetos exportados com sucesso: " + file.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("-> Erro ao exportar (salvar o arquivo): " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void deletar(List<Usuario> usuarios, Scanner scanner) {
		System.out.println();
		System.out.println("=> Deletar usuarios");
		System.out.print("Email: ");
		String email = scanner.nextLine();
		List<Usuario> usuariosParaDeletar = new ArrayList<Usuario>();
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().toUpperCase().contains(email.toUpperCase())) {
				usuariosParaDeletar.add(usuario);
			}
		}
		if (usuariosParaDeletar.isEmpty()) {
			System.out.println("-> Nenhum usuario encontrado");
			return;
		}
		System.out.println("-> " + usuariosParaDeletar.size() + " usuarios encontrados");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}
		usuarios.removeAll(usuariosParaDeletar);
		System.out.println("-> Usuarios deletados com sucesso");
	}

	private static void pesquisarUsuarios(List<Usuario> usuarios, Scanner scanner) {
		System.out.println();
		System.out.println("=> Pesquisar usuarios");
		boolean encontrou = false;
		System.out.print("Email: ");
		String email = scanner.nextLine();
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().toUpperCase().contains(email.toUpperCase())) {
				encontrou = true;
				System.out.println(usuario.imprimir());
			}
		}
		if (!encontrou) {
			System.out.println("-> Nenhum usuario encontrado");
		}
	}

	private static void deletarTodosUsuarios(List objetos, Scanner scanner) {
		System.out.println();
		System.out.println("=> Deletar todos os objetos");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}
		objetos.clear();
		System.out.println("-> Objetos deletados com sucesso");
	}

	private static void listar(List<Exportavel> objetos) {
		System.out.println();
		System.out.println("=> Listar objetos");
		if (objetos.isEmpty()) {
			System.out.println("-> Nenhum usuario encontrado");
			return;
		}
		for (Exportavel exportavel : objetos) {
			System.out.println(exportavel.imprimir());
		}
	}

	private static void criarUsuario(List<Usuario> usuarios, Scanner scanner) {
		System.out.println();
		System.out.println("=> Criar usuario");
		System.out.print("-> Email: ");
		String email = scanner.nextLine();
		String senha = null;
		String confirmarSenha = null;
		do {
			System.out.print("-> Senha: ");
			senha = scanner.nextLine();
			System.out.print("-> Confirmar senha: ");
			confirmarSenha = scanner.nextLine();
		} while (!senha.equals(confirmarSenha));
		Usuario usuario = new Usuario(email, senha);
		usuarios.add(usuario);
		System.out.println("-> Usuario criado com sucesso");
	}

	private static String menu(Scanner scanner) {
		System.out.println();
		System.out.println("=> Menu");
		System.out.println("--> Operacoes com usuario em memoria");
		System.out.println("1. Criar usuario");
		System.out.println("2. Listar usuarios");
		System.out.println("3. Deletar todos os usuarios");
		System.out.println("4. Pesquisar usuarios");
		System.out.println("5. Deletar usuarios");
		System.out.println("--> Operacoes com usuario em memoria <-> arquivo");
		System.out.println("6. Exportar usuarios");
		System.out.println("7. Importar usuarios");
		System.out.println("--> Operacoes com usuario em memoria <-> banco de dados");
		System.out.println("8. Inserir usuario");
		System.out.println("9. Selecionar todos usuarios");
		System.out.println("10. Deletar todos os usuarios");
		System.out.println("11. Selecionar usuarios");
		System.out.println("12. Deletar usuarios");
		System.out.println("--> Operacoes com usuario em arquivo <-> banco de dados");
		System.out.println("13. Exportar usuarios de banco de dados para arquivo");
		System.out.println("14. Importar usuarios de arquivo para banco de dados");
		System.out.println("0. Sair");
		System.out.print("-> Digite a opcao desejada: ");
		String opcao = scanner.nextLine();
		return opcao;
	}

}
