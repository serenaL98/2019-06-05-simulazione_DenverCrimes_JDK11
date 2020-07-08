package it.polito.tdp.denvercrimes.model;

public class Evento {
	
	private Event evento;
	private EventType tipo;
	
	public enum EventType{
		CRIMINE,ARRIVO
	}

	public Evento(Event evento, EventType tipo) {
		super();
		this.evento = evento;
		this.tipo = tipo;
	}

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

}
