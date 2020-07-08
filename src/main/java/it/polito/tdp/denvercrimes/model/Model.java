package it.polito.tdp.denvercrimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.denvercrimes.db.EventsDAO;

public class Model {
	
	private EventsDAO dao;
	private List<Integer> anni;
	
	//Grafo semplice, pesato, non orientato
	private Graph<Distretto, DefaultWeightedEdge> grafo;
	private List<Distretto> distretti;
	private List<Collegamento> collegamenti;
	
	private Map<Integer, Distretto> mappa;
	
	public Model() {
		this.dao = new EventsDAO();
		this.anni = new ArrayList<>(this.dao.prendiAnno());
		
		this.distretti = new ArrayList<>();
		this.collegamenti = new ArrayList<>();
		
		this.mappa = new HashMap<>();
	}

	public List<Integer> prendiAnni(){
		return this.anni;
	}
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.distretti = this.dao.prendiDistrettiAnno(anno);
		
		for(Distretto d: this.distretti) {
			mappa.put(d.getCodice(), d);
		}
		
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.distretti);
		
		for(Distretto d: this.distretti) {
			for(Distretto t: this.distretti) {
				if(!d.getCodice().equals(t.getCodice()) && !this.grafo.containsEdge(d, t)) {
					double distanza =  LatLngTool.distance(d.getCentro(), t.getCentro(), LengthUnit.KILOMETER);
					Collegamento col = new Collegamento(d, t, distanza);
					this.collegamenti.add(col);
					Graphs.addEdge(this.grafo, d, t, distanza);
				}
			}
		}
		
		System.out.println("vertici: "+this.grafo.vertexSet().size());
		System.out.println("archi: "+this.grafo.edgeSet().size());
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Distretto> elencoVertici(){
		return this.grafo.vertexSet();
	}
	
	public String distrettiAdiacenti(Distretto di) {
		
		List<Collegamento> ordinata = new ArrayList<>();
		Collegamento temp = null;
		
		for(Distretto d: Graphs.neighborListOf(this.grafo, di)) {
			temp = new Collegamento( di, d, this.grafo.getEdgeWeight(this.grafo.getEdge(di, d)) );
			ordinata.add(temp);
		}
		Collections.sort(ordinata);
		
		String stampa = "";
		for(Collegamento c: ordinata) {
			stampa += c.getD2().getCodice()+" --> "+c.getDistanza()+"\n";
		}
		return stampa;
	}
	
	//---PUNTO 2---
	private List<Integer> mesi;
	private List<Integer> giorni;
	private Distretto centrale;
	private List<Event> eventiGiorno;
	private Simulatore simu;
	
	public List<Integer> prendiMese(int anno){
		this.mesi = new ArrayList<>();
		
		this.mesi = this.dao.prendiMeseAnno(anno);
		
		return this.mesi;
	}
	
	public List<Integer> prendiGiorno(int anno, int mese){
		this.giorni = new ArrayList<>(this.dao.prendiGiornoMeseAnno(anno, mese));
		
		return this.giorni;
	}
	
	public Distretto prendiCentrale(int anno) {
		this.centrale = this.dao.trovaCentrale(anno, mappa);
		return this.centrale;
	}
	
	public List<Event> prendiEventi(int anno, int mese, int gg){
		this.eventiGiorno = new ArrayList<>(this.dao.prendiEventi(anno, mese, gg));
		return this.eventiGiorno;
	}
	
	public void simula(int anno, int mese, int giorno, int N) {
		this.simu = new Simulatore();
		this.simu.inizia(anno, mese, giorno, N, this.grafo);
		this.simu.avvia();
	}
	
	public int malGestiti() {
		return this.simu.getMalgestiti();
	}
}
