package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.ClientDao;

@Service
public class ClientService {

	private ClientDao clientDao;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public int create(Client client) throws ServiceException {
		try {
			this.clientDao.create(client);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}

	public int update(Client client) throws ServiceException {
		try {
			this.clientDao.update(client);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Client findById(int id) throws ServiceException {
		try {
			return this.clientDao.findById(id).get();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	public Client findByIdClient(int id) throws ServiceException {
		try {
			return this.clientDao.findByIdClient(id);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	public List<Client> findClientByName(String nom) throws ServiceException {
		return this.clientDao.findClientByName(nom);
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int count(){
		return this.clientDao.count();
	}	
}
