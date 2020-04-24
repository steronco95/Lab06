package it.polito.tdp.meteo.model;

import java.util.Date;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		
		
		System.out.println(m.getUmiditaMedia(12));
		
		
		
		try {
			for(Rilevamento r : m.trovaSequenza(5))
			System.out.println(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(m.bestCosto());
		
//		try {
//			StringBuffer result = new StringBuffer();
//		for(Rilevamento r : m.trovaSequenza1(5)) {
//			result.append(r+ "\n");
//			System.out.println(result.toString());
//		}
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("\n////////////////////////////////////\n");
		
		StringBuffer result = new StringBuffer();
		for(Rilevamento r : m.getRilevamenti()) {
			result.append(r+ "\n");
			
		}
		System.out.println(result.toString());
	}

}
