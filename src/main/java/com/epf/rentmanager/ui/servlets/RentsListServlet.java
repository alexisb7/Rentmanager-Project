package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents")
public class RentsListServlet extends HttpServlet {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    VehicleDao vehicleDao;

    @Autowired
    ClientDao clientDao;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {           
            request.setAttribute("listUsers", this.clientDao.findAll());
            request.setAttribute("listVehicles", this.vehicleDao.findAll());
            request.setAttribute("listRents", this.reservationDao.findAll());               
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            reservationDao.delete(reservationDao.findResaById(Integer.parseInt(request.getParameter("id"))));
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        doGet(request,response);
    }
    
}
