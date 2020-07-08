package it.polito.tdp.denvercrimes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.denvercrimes.model.Evento.EventType;

public class Simulatore {
	
	//INPUT
	private Distretto centrale;
	private Model model;
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	
	//OUTPUT
	private int malgestiti;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;
	
	//MODELLO DEL MONDO
	private Map<Distretto, Integer> distrettoAgenti;
	private int agenti;
	private List<Event> crimini;
	
	//STAMPA OUTPUT
	public int getMalgestiti() {
		return malgestiti;
	}

	//SIMULAZIONE
	public void inizia(int anno, int mese, int giorno, int N, Graph<Distretto, DefaultWeightedEdge> grafo) {
		this.centrale = this.model.prendiCentrale(anno);
		this.agenti = N;
		this.grafo = grafo;
		this.crimini = this.model.prendiEventi(anno, mese, giorno);
		this.distrettoAgenti = new HashMap<>();
		this.coda = new PriorityQueue<>();
		this.malgestiti = 0;
		
		//tutti gli agenti si trovano nella centrale
		distrettoAgenti.put(centrale, agenti);
		
		for(Distretto d: this.model.elencoVertici()) {
			if(!d.equals(centrale))
				this.distrettoAgenti.put(d, 0);
		}
		
		//inizializzo coda degli eventi con tutti gli eventi criminosi
		for(Event e: this.crimini) {
			this.coda.add(new Evento(e, EventType.CRIMINE));
		}
		
	}
	
	public void avvia() {
		while(!this.coda.isEmpty()) {
			Evento e = this.coda.poll();
			processEvent(e);
		}
	}
	
	public void processEvent(Evento e) {
		
		switch(e.getTipo()) {

		case CRIMINE:
			//agenti disponibili?
			if(cercaDistretto(e.getEvento())!= null) {
				
				Distretto attuale = agenteLibero(cercaDistretto(e.getEvento()));
				
				if(attuale != null) {
					
					//un agente viene inviatoa destinazione quindi
					this.distrettoAgenti.put(attuale, this.distrettoAgenti.get(attuale)-1);
					
					double distanza = 0;
					if(attuale.equals(cercaDistretto(e.getEvento()))) {
						distanza = 0;
					}else {
						distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(cercaDistretto(e.getEvento()), attuale));
					}
					Long arrivo = (long) ((distanza/60)*60);
					
					e.getEvento().setReported_date(e.getEvento().getReported_date().plusMinutes(arrivo));
					Event nuovo = e.getEvento();
					
					this.coda.add(new Evento(nuovo, EventType.ARRIVO));
					
				}else
					this.malgestiti++;
			
			}
			break;
			
		case ARRIVO:
			//com'è gestito?
			if(e.getEvento().getOffense_category_id().equals("all_other_crimes")) {
				//1 o 2 ore
				double prob = Math.random();
				if(prob>0.5) {
					e.getEvento().setReported_date(e.getEvento().getReported_date().plusMinutes(60));
				}else
					e.getEvento().setReported_date(e.getEvento().getReported_date().plusMinutes(2*60));
				
			}else {
				//2 ore
				e.getEvento().setReported_date(e.getEvento().getReported_date().plusMinutes(2*60));

			}
			if(e.getEvento().getReported_date().isAfter(e.getEvento().getReported_date().plusMinutes(15))) {
				this.malgestiti++;
			}else {
				//agente torna libero
				this.distrettoAgenti.put(cercaDistretto(e.getEvento()), this.distrettoAgenti.get(cercaDistretto(e.getEvento()))+1);
			}
			
			break;
			
		}
	}
	
	public Distretto cercaDistretto(Event eve) {
		for(Distretto d: this.grafo.vertexSet()) {
			if(eve.getDistrict_id().equals(d.getCodice())) {
				return d;
			}
		}
		return null;
	}
	
	public Distretto agenteLibero(Distretto attuale) {
		//boolean libero = false;
		double distanza = Double.MAX_VALUE;
		Distretto destinazione = null;
		
		for(Distretto d: this.distrettoAgenti.keySet()) {
			if(distrettoAgenti.get(d)>0) {
				//ci sono agenti
				if(d.equals(attuale)) {
					distanza = 0;
					//libero = true;
					destinazione = d;
				}else {
					//non ce ne sono in quel distretto guardo i piu vicini
					for(Distretto vicino: Graphs.neighborListOf(this.grafo, attuale)) {
						if(this.grafo.getEdgeWeight(this.grafo.getEdge(attuale, vicino))<distanza) {
							distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(attuale, vicino));
							//libero = true;
							destinazione = vicino;
						}
					}
				}
					
			}
		}
		
		return destinazione;
	}
	
}
