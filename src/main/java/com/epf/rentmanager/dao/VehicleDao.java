package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	@Autowired
	ReservationService reservationService;

	@Autowired
	ReservationDao reservationDao;
	
	//private static VehicleDao instance = null;
	private VehicleDao() {}
	// public static VehicleDao getInstance() {
	// 	if(instance == null) {
	// 		instance = new VehicleDao();
	// 	}
	// 	return instance;
	// }
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY= "SELECT COUNT(id) FROM Vehicle;";
	private static final String UPDATE_VEHICLES_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";

	public int create(Vehicule vehicule) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(CREATE_VEHICLE_QUERY);			
			pstat.setString(1,vehicule.getConstructeur());
			pstat.setString(2, vehicule.getModele());			
			pstat.setInt(3,vehicule.getNbPlaces());			
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}

	public int delete(Vehicule vehicule) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(DELETE_VEHICLE_QUERY);
			pstat.setInt(1,vehicule.getId());			
			try {
				List<Reservation> liste_res_suppr = reservationService.findResaByVehicleId(vehicule.getId());
				for(int i=0;liste_res_suppr.size()!=i;i++){
					reservationDao.delete(liste_res_suppr.get(i));
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}

	public int update(Vehicule vehicule) throws DaoException{
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(UPDATE_VEHICLES_QUERY);
			pstat.setString(1, vehicule.getConstructeur());
			pstat.setString(2, vehicule.getModele());
			pstat.setInt(3, vehicule.getNbPlaces());
			pstat.setInt(4, vehicule.getId());
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Optional<Vehicule> findById(int id) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_VEHICLE_QUERY);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			String constructeur=rs.getString("constructeur");
			String modele=rs.getString("modele");
			int nbPlace = rs.getInt("nb_places");
			Vehicule v = new Vehicule(id, constructeur,modele, nbPlace);
			return Optional.of(v);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Vehicule findByIdVehicule(int id) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_VEHICLE_QUERY);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			String constructeur=rs.getString("constructeur");
			String modele = rs.getString("modele");
			int nbPlace = rs.getInt("nb_places");
			Vehicule v = new Vehicule(id, constructeur,modele, nbPlace);
			con.close();
			return v;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Vehicule> findAll() throws DaoException {
		List<Vehicule> liste_vehicule = new ArrayList<Vehicule>();
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			do {
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("modele");
				int nbPlaces = rs.getInt("nb_places");
				Vehicule v = new Vehicule(id, constructeur, modele, nbPlaces);
				liste_vehicule.add(v);
			}
			while(rs.next());
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return liste_vehicule;		
	}

	public int count(){
		int nb_v=0;
		try {	
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(COUNT_VEHICLES_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			nb_v=rs.getInt(1);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nb_v;
	}
}
