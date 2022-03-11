package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleDao vehicleDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            request.setAttribute("listVehicles", this.vehicleService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        }
        catch(ServiceException e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        
        try{
            vehicleDao.delete(vehicleDao.findByIdVehicule(Integer.parseInt(request.getParameter("id"))));
        }
        catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        doGet(request, response);
    }
    
}
