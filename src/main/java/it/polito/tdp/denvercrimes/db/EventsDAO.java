package it.polito.tdp.denvercrimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.denvercrimes.model.Distretto;
import it.polito.tdp.denvercrimes.model.Event;


public class EventsDAO {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> prendiAnno(){
		String sql = "SELECT DISTINCT year(e.reported_date) anno " + 
				"FROM `events` e " + 
				"ORDER BY YEAR(e.reported_date) " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getInt("anno"));
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Distretto> prendiDistrettiAnno(int anno){
		String sql = "SELECT e.district_id cod, e.geo_lon lon, e.geo_lat lat " + 
				"FROM `events` e " + 
				"WHERE YEAR(e.reported_date)= ? " + 
				"GROUP BY e.district_id " + 
				"HAVING AVG(e.geo_lon) AND AVG(e.geo_lat) " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			List<Distretto> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Integer id = res.getInt("cod");
				LatLng centro = new LatLng(res.getDouble("lat"), res.getDouble("lon"));
				
				Distretto d = new Distretto(id, centro);
				
				list.add(d);
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> prendiMeseAnno(int anno){
		String sql = "SELECT distinct(MONTH(e.reported_date)) mese " + 
				"FROM `events` e " + 
				"WHERE YEAR(e.reported_date)= ? " + 
				"ORDER BY MONTH(e.reported_date) " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Integer id = res.getInt("mese");
				
				list.add(id);
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> prendiGiornoMeseAnno(int anno, int mese){
		String sql = "SELECT distinct(DAY(e.reported_date)) gg " + 
				"FROM `events` e " + 
				"WHERE YEAR(e.reported_date)= ? AND MONTH(e.reported_date)= ? " + 
				"ORDER BY DAY(e.reported_date) " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			st.setInt(2, mese);
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Integer id = res.getInt("gg");
				
				list.add(id);
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	//---PUNTO 2---
	public Distretto trovaCentrale(int anno, Map<Integer, Distretto> mappa){
		String sql = "SELECT e.district_id cod, COUNT(e.incident_id) " + 
				"FROM `events` e " + 
				"WHERE YEAR(e.reported_date)= ? " + 
				"GROUP BY e.district_id " + 
				"ORDER BY COUNT(e.incident_id) " + 
				"LIMIT 1 " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			Distretto centrale = null;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(mappa.containsKey(res.getInt("cod"))) {
					centrale = mappa.get(res.getInt("cod"));
				}
			}
			
			conn.close();
			return centrale;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Event> prendiEventi(int anno, int mese, int gg){
		String sql = "SELECT * " + 
				"FROM `events` e " + 
				"WHERE YEAR(e.reported_date)= ? AND MONTH(e.reported_date)= ? AND DAY(e.reported_date)= ? " + 
				"ORDER BY e.reported_date " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			st.setInt(2, mese);
			
			st.setInt(3, gg);
			
			List<Event> lista = new ArrayList<>();
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				lista.add(new Event(res.getLong("incident_id"),
						res.getInt("offense_code"),
						res.getInt("offense_code_extension"), 
						res.getString("offense_type_id"), 
						res.getString("offense_category_id"),
						res.getTimestamp("reported_date").toLocalDateTime(),
						res.getString("incident_address"),
						res.getDouble("geo_lon"),
						res.getDouble("geo_lat"),
						res.getInt("district_id"),
						res.getInt("precinct_id"), 
						res.getString("neighborhood_id"),
						res.getInt("is_crime"),
						res.getInt("is_traffic")));
			}
			
			conn.close();
			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
}

