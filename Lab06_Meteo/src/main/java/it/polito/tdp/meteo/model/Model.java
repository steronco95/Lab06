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
	private Map<String,Citta> citta = new HashMap<>();
	private MeteoDAO meteo = new MeteoDAO();
	private List<Rilevamento> rilevamenti;
	private ArrayList<Rilevamento> bestSoluzione;
	private int bestCosto;
	
	
	public Model() {

		
	}

	public List<Rilevamento> getRilevamenti(){
		return rilevamenti;
	}
	
	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		
		citta.put("genova",new Citta("genova"));
		citta.put("torino",new Citta("torino"));
		citta.put("milano",new Citta("milano"));
		
		meteo.setCitta(citta);
		
		for(Citta c : citta.values()) {
			meteo.getAllRilevamentiLocalitaMese(mese, c.getNome());
		}
		
		StringBuffer result = new StringBuffer();
		
		for(Citta c : citta.values()) {
			result.append(c.getNome()).append(": ").append(c.getMedia()+ "\n");
		}
		
		return result.toString();
	}
	
	// of course you can change the String output with what you think works best
	public List<Rilevamento> trovaSequenza(int mese) throws Exception {
	
		int genova = 0;
		int milano = 0;
		int torino = 0;
		
		LocalDate in = LocalDate.of(2013, mese, 01);
		LocalDate fi = LocalDate.of(2013, mese, 15);
		
		Date inizio = java.sql.Date.valueOf(in);
		Date fine = java.sql.Date.valueOf(fi);
				
		rilevamenti = meteo.getListaCitta(inizio, fine);
		
		for(Rilevamento ri : rilevamenti) {
			if(ri.getLocalita().equals("Genova")) {
				genova ++;
			}else if(ri.getLocalita().equals("Torino")) {
				torino++;
			}else if(ri.getLocalita().equals("Milano")) {
				milano ++;
			}
		}
		
		if(genova ==0 || torino ==0|| milano ==0) {
			throw new Exception ("non sono state visitate tutte le citta");
		}
		
		bestCosto = this.calcolaCosto(rilevamenti);
		
		List<Rilevamento> parziale = new ArrayList<>();
//		bestSoluzione = new ArrayList<>(rilevamenti);
		cerca(parziale,0,rilevamenti);
		
		
		return bestSoluzione;
	}
	
	
	private void cerca(List<Rilevamento> parziale, int livello, List<Rilevamento> disponibili) {
		
		//controllo il costo con il bestCosto
//		bestCosto = this.calcolaCosto(parziale);
//		if(this.calcolaCosto(parziale) > bestCosto) {
//			return;
//		}
		
		int costo = this.calcolaCosto(parziale);
		if(costo > bestCosto) {
			return;
		}
		
		
		
		
		
		//arrivo alla fine dell'albero ricorsivo
		if(disponibili.size() == 0) {
			if(costo <= bestCosto) 
				
				
			
			bestCosto = this.calcolaCosto(parziale);
			
			bestSoluzione = new ArrayList<>(parziale);
			return;
			
		}
		
		for(Rilevamento r : disponibili) {
			parziale.add(r);
			List<Rilevamento> temp = new ArrayList<>(disponibili);
			temp.remove(r);
			cerca(parziale,livello+1,temp);
			
		}
		
//		parziale.add(rilevamenti.get(livello));
//		cerca(parziale, livello+1);
//		parziale.remove(rilevamenti.get(livello));
//		
//		cerca(parziale,livello+1);
		
		
		
		
	}



	private int calcolaCosto(List<Rilevamento> parziale) {
		
		int costo =0;
		
		for(Rilevamento r : parziale) {
			costo += r.getUmidita(); 
		}

		for(int i =0; i<parziale.size(); i++) {
			for(int j=i+1; j<parziale.size(); j++) {
				if(!parziale.get(i).equals(parziale.get(j))) {
					costo += 100;
					break;
				}
			}
		}
		
		return costo;
	}

	public int bestCosto() {
		
		return bestCosto;
	}

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
