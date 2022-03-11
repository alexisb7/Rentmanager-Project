package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	// private static ReservationDao instance=null;
	// public static ReservationDao getInstance() {
	// 	if(instance == null) {
	// 		instance = new ReservationDao();
	// 	}
	// 	return instance;
	// }
	private ReservationDao() {
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY= "SELECT COUNT(id) FROM Reservation;";
	private static final String UPDATE_RESERVATIONS_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	
	public int create(Reservation reservation) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(CREATE_RESERVATION_QUERY);			
			pstat.setInt(1,reservation.getClientId());			
			pstat.setInt(2,reservation.getVehicleId());
			pstat.setDate(3,Date.valueOf(reservation.getDebut()));
			pstat.setDate(4,Date.valueOf(reservation.getFin()));			
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;	
	}
	
	public int delete(Reservation reservation) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(DELETE_RESERVATION_QUERY);
			pstat.setInt(1,reservation.getId());
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}

	public int update(Reservation reservation) throws DaoException{
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(UPDATE_RESERVATIONS_QUERY);	
			pstat.setInt(1, reservation.getClientId());
			pstat.setInt(2, reservation.getVehicleId());
			pstat.setDate(3, Date.valueOf(reservation.getDebut()));
			pstat.setDate(4, Date.valueOf(reservation.getFin()));
			pstat.setInt(5, reservation.getId());
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	
	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		List<Reservation> liste_res = new ArrayList<Reservation>();
		
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			pstat.setInt(1, clientId);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()==true){
				int id = rs.getInt("id");
				int vehicule_id=rs.getInt("vehicle_id");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String debut_res = rs.getDate("debut").toLocalDate().format(formatter);
				String fin_res = rs.getDate("fin").toLocalDate().format(formatter);
				Reservation res = new Reservation(id, clientId, vehicule_id, debut_res, fin_res);	
				liste_res.add(res);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste_res;
	}

	public int findNbResaByClientId(int clientId) throws DaoException {
		int nb_resa=0;
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			pstat.setInt(1, clientId);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()==true){
				nb_resa++;
			}		
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nb_resa;
	}
	
	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {
		List<Reservation> liste_res = new ArrayList<Reservation>();
		
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			pstat.setInt(1, vehicleId);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			int id = rs.getInt("id");
			int client_id=rs.getInt("client_id");
			LocalDate debut_res = rs.getDate("debut").toLocalDate();
			LocalDate fin_res = rs.getDate("fin").toLocalDate();
			Reservation res = new Reservation(id, client_id, vehicleId, debut_res, fin_res);	
			liste_res.add(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste_res;		 
	}

	public Reservation findResaById(int id){
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_RESERVATION_QUERY);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			int client_id=rs.getInt("client_id");
			int vehicleId = rs.getInt("vehicle_id");
			LocalDate debut_res = rs.getDate("debut").toLocalDate();
			LocalDate fin_res = rs.getDate("fin").toLocalDate();
			Reservation res = new Reservation(id, client_id, vehicleId, debut_res, fin_res);	
			con.close();
			return res;
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return null;
	}
	
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> liste_res = new ArrayList<Reservation>();
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			do {
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicule_id=rs.getInt("vehicle_id");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String debut_res = rs.getDate("debut").toLocalDate().format(formatter);
				String fin_res = rs.getDate("fin").toLocalDate().format(formatter);
				Reservation res = new Reservation(id, client_id, vehicule_id, debut_res, fin_res);	
				liste_res.add(res); 
			}
			while(rs.next());
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return liste_res;		 
	}

	public int count(){
		int nb_rents=0;
		try {	
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			nb_rents=rs.getInt(1);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		return nb_rents;

	}
}
