package br.com.ftec.ads.poo.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.ftec.ads.poo.entidade.Exportavel;
import br.com.ftec.ads.poo.entidade.Livro;
import br.com.ftec.ads.poo.entidade.Usuario;

public class Principal {

	public static void main(String[] args) {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<Livro> livros = new ArrayList<Livro>();
		List<Exportavel> objetosExportaveis = null;
		Scanner scanner = new Scanner(System.in);
		String opcao = null;
		do {
			opcao = menu(scanner);
			switch (opcao) {
			case "1":
				criarUsuario(usuarios, scanner);
				break;
			case "11":
				criarLivro(livros, scanner);
				break;
			case "2":
				objetosExportaveis = new ArrayList<Exportavel>(usuarios);
				listar(objetosExportaveis);
				break;
			case "22":
				objetosExportaveis = new ArrayList<Exportavel>(livros);
				listar(objetosExportaveis);
				break;
			case "3":
				deletarTodos(usuarios, scanner);
				break;
			case "33":
				deletarTodos(livros, scanner);
				break;
			case "4":
				pesquisar(usuarios, scanner);
				break;
			case "5":
				deletar(usuarios, scanner);
				break;
			case "6":
				objetosExportaveis = new ArrayList<Exportavel>(usuarios);
				exportar(objetosExportaveis, "arquivos/usuarios.csv");
				break;
			case "66":
				objetosExportaveis = new ArrayList<Exportavel>(livros);
				exportar(objetosExportaveis, "arquivos/livros.csv");
				break;
			case "7":
				importar(usuarios, "arquivos/usuarios.csv", scanner);
				break;
			default:
				break;
			}
		} while (!opcao.equals("0"));
	}

	private static void importar(List<Usuario> usuarios, String nomeArquivo,
			Scanner scanner) {
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
			System.out
					.println("-> Erro ao importar usuarios (arquivo nao encontrado): ");
			e.printStackTrace();
			return;
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String readLine = null;
		try {
			while ((readLine = bufferedReader.readLine()) != null) {
				String[] split = readLine.split(";");
				String email = split[0];
				String senha = split[1];
				Usuario usuario = new Usuario(email, senha);
				usuarios.add(usuario);
			}
			System.out.println("-> Usuarios importados com sucesso");
		} catch (IOException e) {
			System.out.println("-> Erro ao importar usuarios (ler arquivo): ");
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
			System.err.println("-> Erro ao exportar (criar o arquivo): ");
			e.printStackTrace();
			return;
		}
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			for (Exportavel exportavel : objetos) {
				bufferedWriter.write(exportavel.exportar());
			}
		} catch (IOException e) {
			System.err.println("-> Erro ao exportar (escrever no arquivo): ");
			e.printStackTrace();
			return;
		}
		try {
			bufferedWriter.close();
			System.out.println("-> Objetos exportados com sucesso: "
					+ file.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("-> Erro ao exportar (salvar o arquivo): ");
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
		System.out.println("-> " + usuariosParaDeletar.size()
				+ " usuarios encontrados");
		System.out.print("-> Confirmar a operacao? [sim]: ");
		String confirmar = scanner.nextLine();
		if (!confirmar.equalsIgnoreCase("sim")) {
			System.out.println("-> Operacao cancelada");
			return;
		}
		usuarios.removeAll(usuariosParaDeletar);
		System.out.println("-> Usuarios deletados com sucesso");
	}

	private static void pesquisar(List<Usuario> usuarios, Scanner scanner) {
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

	private static void deletarTodos(List objetos, Scanner scanner) {
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
		for (Exportavel exportavel: objetos) {
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

	private static void criarLivro(List<Livro> livros, Scanner scanner) {
		System.out.println();
		System.out.println("=> Criar livro");
		System.out.print("-> Titulo: ");
		String tituto = scanner.nextLine();
		System.out.print("-> Autor: ");
		String autor = scanner.nextLine();
		Livro livro = new Livro(tituto, autor);
		livros.add(livro);
		System.out.println("-> Livro criado com sucesso");
	}

	private static String menu(Scanner scanner) {
		System.out.println();
		System.out.println("=> Menu");
		System.out.println("1. Criar usuario");
		System.out.println("11. Criar livro");
		System.out.println("2. Listar usuarios");
		System.out.println("22. Listar livros");
		System.out.println("3. Deletar todos os usuarios");
		System.out.println("33. Deletar todos os livros");
		System.out.println("4. Pesquisar usuarios");
		System.out.println("5. Deletar usuarios");
		System.out.println("6. Exportar usuarios");
		System.out.println("66. Exportar livros");
		System.out.println("7. Importar usuarios");
		System.out.println("0. Sair");
		System.out.print("-> Digite a opcao desejada: ");
		String opcao = scanner.nextLine();
		return opcao;
	}

}