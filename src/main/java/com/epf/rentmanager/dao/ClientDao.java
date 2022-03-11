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
import java.util.Optional;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	// private static ClientDao instance=null;
	
	// public static ClientDao getInstance() {
	// 	if(instance == null) {
	// 		instance = new ClientDao();
	// 	}
	// 	return instance;
	// }

	private ClientDao() {}

	@Autowired
	ClientDao clientDao;

	@Autowired
	ReservationService reservationService;

	@Autowired
	ReservationDao reservationDao;
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY= "SELECT COUNT(id) FROM Client;";
	private static final String UPDATE_CLIENTS_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	public int create(Client client) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(CREATE_CLIENT_QUERY);
			
			pstat.setString(1,client.getNom());
			
			pstat.setString(2,client.getPrenom());
			
			pstat.setString(3,client.getEmail());
			
			pstat.setDate(4,Date.valueOf(client.getNaissance()));
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int delete(Client client) throws DaoException {
		
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(DELETE_CLIENT_QUERY);
			pstat.setInt(1,client.getId());			
			try {
				List<Reservation> liste_res_suppr = reservationService.findResaByClientId(client.getId());
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

	public int update(Client client) throws DaoException{
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(UPDATE_CLIENTS_QUERY);
			pstat.setString(1, client.getNom());
			pstat.setString(2, client.getPrenom());
			pstat.setString(3, client.getEmail());
			pstat.setDate(4, Date.valueOf(client.getNaissance()));
			pstat.setInt(5, client.getId());
			pstat.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Optional<Client> findById(int id) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(FIND_CLIENT_QUERY);
			pstat.setInt(1, id);
			ResultSet rs =pstat.executeQuery();
			rs.next();
			String clientNom = rs.getString("nom");
			String clientPrenom = rs.getString("prenom");
			String clientEmail = rs.getString("email");
			LocalDate clientDateNaissance = rs.getDate("naissance").toLocalDate();
			Client client = new Client(id, clientNom, clientPrenom, clientEmail, clientDateNaissance);
			con.close();
			return Optional.of(client);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
		
	}

	public Client findByIdClient(int id) throws DaoException {
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(FIND_CLIENT_QUERY);
			pstat.setInt(1, id);
			ResultSet rs =pstat.executeQuery();
			rs.next();
			String clientNom = rs.getString("nom");
			String clientPrenom = rs.getString("prenom");
			String clientEmail = rs.getString("email");
			LocalDate clientDateNaissance = rs.getDate("naissance").toLocalDate();
			Client client = new Client(id, clientNom, clientPrenom, clientEmail, clientDateNaissance);
			con.close();
			return client;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public List<Client> findClientByName(String nom){
		List<Client> liste_client = new ArrayList<Client>();
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat=con.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			do{
				String name = rs.getString("nom");
				if(name.contains(nom)){
					int id = rs.getInt("id");
					String prenom = rs.getString("prenom");
					String email = rs.getString("email");
					LocalDate naissance = rs.getDate("naissance").toLocalDate();
					Client client = new Client(id, name, prenom, email, naissance);
					liste_client.add(client);
				}
			}
			while(rs.next());
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste_client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> liste_client = new ArrayList<Client>();
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			do {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom=rs.getString("prenom");
				String email = rs.getString("email");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String naissance = rs.getDate("naissance").toLocalDate().format(formatter);				
				Client c = new Client(id, nom, prenom, email, naissance);
				liste_client.add(c); 
			}
			while(rs.next());
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return liste_client;
		 
	}

	public int count(){
		int nb_users=0;
		try {	
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pstat = con.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = pstat.executeQuery();
			rs.next();
			nb_users=rs.getInt(1);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nb_users;
	}

}
