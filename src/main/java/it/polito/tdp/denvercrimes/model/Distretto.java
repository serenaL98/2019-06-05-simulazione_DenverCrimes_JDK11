package it.polito.tdp.denvercrimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	
	private Integer codice;
	private LatLng centro;
	
	public Distretto(Integer codice, LatLng centro) {
		super();
		this.codice = codice;
		this.centro = centro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centro == null) ? 0 : centro.hashCode());
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
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
		Distretto other = (Distretto) obj;
		if (centro == null) {
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		return true;
	}

	public Integer getCodice() {
		return codice;
	}
	public void setCodice(Integer codice) {
		this.codice = codice;
	}
	public LatLng getCentro() {
		return centro;
	}
	public void setCentro(LatLng centro) {
		this.centro = centro;
	}
	
	

}
