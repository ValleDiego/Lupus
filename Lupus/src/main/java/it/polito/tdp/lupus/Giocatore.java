package it.polito.tdp.lupus;

public class Giocatore {
	
	private String nome;
	private Ruolo ruolo;
	private String salute;
	private boolean sospetto;
	private boolean bloccato;
	
	public Giocatore(String nome, Ruolo ruolo) {
		super();
		this.nome = nome;
		this.ruolo = ruolo;
		this.salute = "Alive";
		this.sospetto = false;//mi devo ricordare di settare i sospetti
		this.bloccato = false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public String getSalute() {
		return salute;
	}

	public void setSalute(String salute) {
		this.salute = salute;
	}

	public boolean isSospetto() {
		return sospetto;
	}

	public void setSospetto(boolean sospetto) {
		this.sospetto = sospetto;
	}

	public boolean isBloccato() {
		return bloccato;
	}

	public void setBloccato(boolean bloccato) {
		this.bloccato = bloccato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Giocatore [nome=" + nome + ", ruolo=" + ruolo.toString() + ", salute=" + salute + "]";
	}
	
	
	
	

}
