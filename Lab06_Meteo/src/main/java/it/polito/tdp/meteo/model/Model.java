package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
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

	public Model() {

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
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	

}
