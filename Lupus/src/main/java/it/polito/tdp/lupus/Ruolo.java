package it.polito.tdp.lupus;

public class Ruolo {
	
	private String ruolo;
	private String team;
	
	public Ruolo(String ruolo, String team) {
		super();
		this.ruolo = ruolo;
		this.team = team;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return ruolo + ", " + team;
	}
	
	

}
