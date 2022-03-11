package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/details")
public class UserDetailsServlet extends HttpServlet {

    @Autowired
    ClientService clientService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ReservationService reservationService;

    int id;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        id = Integer.parseInt(request.getParameter("id"));
        try {
            int nb_resa = this.reservationService.findNbResaByClientId(id);
            request.setAttribute("nb_resa", nb_resa);
            request.setAttribute("id", id);
            request.setAttribute("listUsers", this.clientService.findAll());
            if(nb_resa!=0){ 
                request.setAttribute("listVehicles", this.vehicleService.findAll());
                request.setAttribute("listRents", this.reservationService.findResaByClientId(id)); 
            }
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        doGet(request, response);
    }
    
}
