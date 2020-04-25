package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	
	
	
	

	public List<Rilevamento> getAllRilevamenti() {

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		

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
	
	public Double getUmiditaMedia(int mese, Citta c) {
		
		final String sql = "SELECT AVG(Umidita) AS U FROM situazione  WHERE localita=? AND MONTH(DATA)=? ";

		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, c.getNome());
			st.setInt(2, mese);
			
			ResultSet rs = st.executeQuery();

			rs.next();
			double ris = rs.getDouble("U");

			return ris;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}
	
	public List<Citta> getAllCitta() {
		
		List<Citta> citta = new ArrayList<>();
		
		final String sql = "SELECT distinct Localita FROM situazione ";

		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta c = new Citta(rs.getString("Localita"));
				citta.add(c);
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, Citta c) {

		List<Rilevamento> ril = new ArrayList<>();
		
		final String sql = "SELECT localita, DATA, umidita FROM situazione WHERE localita =? AND MONTH(DATA) =?";

		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, c.getNome());
			st.setInt(2, mese);
			
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				ril.add(r);
				
			}

			conn.close();
			
			c.setRilevamenti(ril);
			
			return ril;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	
		
	}
	
	


	


}
