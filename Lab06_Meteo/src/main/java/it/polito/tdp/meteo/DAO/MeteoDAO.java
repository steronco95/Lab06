package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	
	
	private Map<String, Citta> citta;


	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
//		Date inizio = new Date(2013,mese,01);
//		Date fine = new Date(2013,mese,31);
		String inizio = "2013-" + mese + "-01";
		String fine = "2013-" + mese + "-31";
		Citta c = citta.get(localita);
		
		final String sql = "SELECT Localita, DATA, Umidita FROM situazione WHERE Localita = ? AND DATA >=? AND DATA <=?";
		
		List<Rilevamento> rilevamenti = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, localita);
			st.setString(2, inizio);
			st.setString(3, fine);
//			st.setDate(2, inizio);
//			st.setDate(3, fine);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
				
				
			}
			
			c.setRilevamenti(rilevamenti);
			
			conn.close();
		
		}catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return rilevamenti;
	}


	public void setCitta(Map<String, Citta> citta) {
		this.citta = citta;
		
	}


}
