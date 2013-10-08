package br.com.ftec.ads.poo.entidade;

public abstract class Entidade implements Exportavel{

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}