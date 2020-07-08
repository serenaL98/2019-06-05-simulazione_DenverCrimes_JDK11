package it.polito.tdp.denvercrimes.model;

public class Collegamento implements Comparable<Collegamento>{

	private Distretto d1;
	private Distretto d2;
	private Double distanza;
	
	
	public Collegamento(Distretto d1, Distretto d2, double distanza) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.distanza = distanza;
	}
	
	
	public Distretto getD1() {
		return d1;
	}
	public void setD1(Distretto d1) {
		this.d1 = d1;
	}
	public Distretto getD2() {
		return d2;
	}
	public void setD2(Distretto d2) {
		this.d2 = d2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}


	@Override
	public int compareTo(Collegamento o) {
		return this.distanza.compareTo(o.getDistanza());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
		result = prime * result + ((d2 == null) ? 0 : d2.hashCode());
		result = prime * result + ((distanza == null) ? 0 : distanza.hashCode());
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
		Collegamento other = (Collegamento) obj;
		if (d1 == null) {
			if (other.d1 != null)
				return false;
		} else if (!d1.equals(other.d1))
			return false;
		if (d2 == null) {
			if (other.d2 != null)
				return false;
		} else if (!d2.equals(other.d2))
			return false;
		if (distanza == null) {
			if (other.distanza != null)
				return false;
		} else if (!distanza.equals(other.distanza))
			return false;
		return true;
	}
	
	
}
