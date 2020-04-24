package it.polito.tdp.meteo.model;

import java.util.Date;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		
		
		System.out.println(m.getUmiditaMedia(12));
		
		
		
		try {
			System.out.println(m.trovaSequenza(6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
