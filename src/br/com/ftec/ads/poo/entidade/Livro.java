package br.com.ftec.ads.poo.entidade;

import br.com.ftec.ads.poo.util.Utilitarios;

public class Livro extends Entidade {

	private String titulo;
	private String autor;

	public Livro() {
	}

	public Livro(String titulo, String autor) {
		Long nextLong = Utilitarios.nextLong();
		this.setId(nextLong);
		this.titulo = titulo;
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String imprimir() {
		String imprimir = "";
		imprimir += "Id: ";
		imprimir += this.getId();
		imprimir += "\n";
		imprimir += "Titulo: ";
		imprimir += this.titulo;
		imprimir += "\n";
		imprimir += "Autor: ";
		imprimir += this.autor;
		imprimir += "\n";
		return imprimir;
	}

	public String exportar() {
		String exportar = "";
		exportar += this.getId();
		exportar += ";";
		exportar += this.titulo;
		exportar += ";";
		exportar += this.autor;
		exportar += "\n";
		return exportar;
	}

}
