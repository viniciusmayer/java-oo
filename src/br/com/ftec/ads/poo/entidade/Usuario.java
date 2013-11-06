package br.com.ftec.ads.poo.entidade;

public class Usuario extends Entidade {

	private String email;
	private String senha;

	public Usuario() {
	}

	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public Usuario(Long id, String email, String senha) {
		this.setId(id);
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String imprimir() {
		String imprimir = "";
		imprimir += "Id: ";
		imprimir += this.getId();
		imprimir += "\n";
		imprimir += "Email: ";
		imprimir += this.email;
		imprimir += "\n";
		imprimir += "Senha: ";
		imprimir += this.senha;
		imprimir += "\n";
		return imprimir;
	}

	public String exportar() {
		String exportar = "";
		exportar += this.getId();
		exportar += ";";
		exportar += this.email;
		exportar += ";";
		exportar += this.senha;
		exportar += "\n";
		return exportar;
	}

}