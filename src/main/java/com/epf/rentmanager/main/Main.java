package com.epf.rentmanager.main;

import java.sql.Date;

import javax.naming.spi.ResolveResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import com.epf.rentmanager.Configuration.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.ui.servlets.VehicleCreateServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    
    public static void main(String[]args){
        // ReservationDao reservationDao=ReservationDao.getInstance();
        // try {
        //     System.out.println(reservationDao.findAll());
        // } catch (DaoException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        }
    

}
