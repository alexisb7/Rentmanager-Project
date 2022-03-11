package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;

    int id;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("id", id);
        try {
            request.setAttribute("constructeur", vehicleService.findById(id).getConstructeur());
            request.setAttribute("modele", vehicleService.findById(id).getModele());
            request.setAttribute("seats", vehicleService.findById(id).getNbPlaces());
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String modele = request.getParameter("modele");
        int nbPlaces = Integer.parseInt(request.getParameter("seats"));
        Vehicule v = new Vehicule(id, constructeur,modele, nbPlaces);
        try {
            vehicleService.update(v);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/cars");
    }
    
}
