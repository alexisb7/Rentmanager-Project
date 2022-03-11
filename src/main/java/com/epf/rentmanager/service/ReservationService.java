package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

    public int create(Reservation reservation) throws ServiceException{
        try{
            this.reservationDao.create(reservation);
        }
        catch(DaoException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Reservation reservation) throws ServiceException{
        try {
            this.reservationDao.update(reservation);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public List<Reservation> findResaByClientId(int clientId) throws ServiceException{
        try {
            return this.reservationDao.findResaByClientId(clientId);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int findNbResaByClientId(int clientId) throws ServiceException{
        try {
            return this.reservationDao.findNbResaByClientId(clientId);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Reservation> findResaByVehicleId(int vehcileId) throws ServiceException{
        try {
            return this.reservationDao.findResaByVehicleId(vehcileId);
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Reservation findResaById(int id) throws DaoException{
        return this.reservationDao.findResaById(id);
    }

    public List<Reservation> findAll() throws ServiceException{
        try {
            return this.reservationDao.findAll();
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public int count(){
        return this.reservationDao.count();
    }
}
