package it.polito.tdp.meteo.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> citta;
	private List<Citta> bestSoluzione; //schema dati in cui inserisco la soluzione migliore trovata con la proc ricorsiva
	private double bestCosto;
	
	public Model() {
		MeteoDAO m = new MeteoDAO();
		
		this.citta = m.getAllCitta();
		
	}
	
	public double getCosto() {
		return bestCosto;
	}
	
	public List<Citta> getAllCitta(){
		
		
		
		return citta;
	}
	
	// of course you can change the String output with what you think works best
	public Double getUmiditaMedia(int mese, Citta c) {
		
		MeteoDAO m = new MeteoDAO();
			
		
		return m.getUmiditaMedia(mese, c);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
	
		//creo lista per soluzione parziale
		List<Citta>  parziale = new ArrayList<>();
		
		MeteoDAO m = new MeteoDAO();
		
		for(Citta c : citta) {
			m.getAllRilevamentiLocalitaMese(mese, c);
		}
		
		//INIZIALIZZO PROCEDURA RICORSIVA
		
		cerca(parziale,0);
		
		return bestSoluzione;
	}

	private void cerca(List<Citta> parziale, int livello) {
		
		//CASO TERMINALE
		if(livello == this.NUMERO_GIORNI_TOTALI) {
			//calcolo il costo della soluzione parziale
			double costo = this.calcolaCosto(parziale);
			//se non ho ancora trovato una soluzione aggiungo questa trovata alla soluzione parziale
			//se invece ho gia trovato una soluzione verifico che la nuova soluzione abbia un costo minore di quella precedentemente trovata
			
			if(bestSoluzione == null || costo < this.calcolaCosto(bestSoluzione)) {
				bestCosto = costo;
				bestSoluzione = new ArrayList<>(parziale);
			}
			
		}
		
		
		
		
		//CASO NORMALE
		
		//itero sulle citta presenti nel database
		for(Citta c : citta) {
			if(aggiuntaValida(c,parziale)) {
				parziale.add(c);
				cerca(parziale,livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	/**
	 * calcolo il costo totale della soluzione parziale, sommando tutte le umidita e aggiungendo 100 ogni volta che cambio citta
	 * @param parziale soluzione parziale passata come parametro dalla funzione ricorsiva
	 * @return costo totale della soluzione
	 */
	private double calcolaCosto(List<Citta> parziale) {
		
		double costo = 0.0;
		
		//sommo tutte le umidita trovate nei primi 15 giorni 
		
		for(int gg =1; gg<this.NUMERO_GIORNI_TOTALI; gg++) {
			//prendo la citta dalla soluzione parziale
			Citta c = parziale.get(gg-1);
			//seleziono dalla lista dei rilevamenti di quella citta il rilevamento del giorno selezionato e ne prendo l'umidita trovata
			double umid = c.getRilevamenti().get(gg-1).getUmidita();
			//sommo l'umidita alla variabile di costo totale;
			costo += umid;
		}
		
		//aggiungo 100 al costo totale ogni volta che cambio citta nei primi 15 giorni
		
		for(int gg =1; gg<this.NUMERO_GIORNI_TOTALI; gg++) {
			if(!parziale.get(gg-1).equals(parziale.get(gg))) {
				costo+=100;
			}
		}
		
		return costo;
	}

	private boolean aggiuntaValida(Citta c, List<Citta> parziale) {
	
		int count = 0;
		
		for(Citta cit : parziale) {
			if(c.equals(cit)) {
			count++;
		}
	}
		//controllo che la citta (count) non compaia piu di 6 volte nella soluzione
		
		if(count >= this.NUMERO_GIORNI_CITTA_MAX) {
//			se si verifica questa condizione non aggiungo la citta alla soluzione parziale nella proc ricorsiva
			return false;
		}
		
		//verifico che il tecnico stia almeno per 3 giorni CONSECUTIVI nella stessa citta
		if(parziale.size() == 0) {
			// non ho inserito ancora alcuna citta perciò posso tranquillamente inserirla;
			return true;
		}
		
		if(parziale.size() == 1 || parziale.size() == 2){
			//sono al secondo o terzo giorno quindi sono obbligato a inserire la stessa citta poichè non posso cambiarla
			return parziale.get(parziale.size()-1).equals(c);
		}
		
		//se ho passato i controlli precedenti posso tranquillamente rimanere nella stessa citta
		if(parziale.get(parziale.size()-1).equals(c)) {
		//controllo se l'ultima citta inserita e uguale a quella che voglio inserire posso tranquillamente inserirla nuovamente
			return true;
		}
		
		//se la citta passata come parametro e diversa dall'ultima citta inserita 
		//allora verifico che nei precedenti tre giorni sono rimasto nella stessa citta
		
		if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
				&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)) ) {
			return true;
		}
		
		
		return false;
	}
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
