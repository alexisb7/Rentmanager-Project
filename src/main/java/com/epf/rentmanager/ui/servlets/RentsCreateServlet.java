package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")
public class RentsCreateServlet extends HttpServlet{

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ClientService clientService;

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    boolean verify=false;
    boolean test=false;
    boolean pause=false;
    String begin;
    String end;
    int clientId;
    int vehicleId;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("listVehicles", this.vehicleService.findAll());
            request.setAttribute("listUsers", this.clientService.findAll());
            request.setAttribute("listRents", this.reservationService.findAll());
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(verify==true || pause==true || test==true){
            request.setAttribute("begin", begin);
            request.setAttribute("end", end);
            request.setAttribute("verify", verify);
            request.setAttribute("test", test);    
            request.setAttribute("pause", pause);          
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }        
        else {
            request.setAttribute("verify", verify);
            request.setAttribute("test", test);   
            request.setAttribute("pause", pause);       
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clientId = Integer.parseInt(request.getParameter("client"));
        vehicleId = Integer.parseInt(request.getParameter("car"));
        begin=request.getParameter("begin");
        end = request.getParameter("end");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate debut = LocalDate.parse(begin, formatter);
        LocalDate fin = LocalDate.parse(end, formatter);  
        long dif=ChronoUnit.DAYS.between(debut, fin);
        try {                
            List<Reservation> liste_res = this.reservationService.findAll();
            for(int i=0;i<liste_res.size();i++){
                if(liste_res.get(i).getDebut_res().equals(begin) && liste_res.get(i).getVehicleId()==vehicleId){                        
                            verify=true;
                            doGet(request, response);
                            return;
                }
                long time = ChronoUnit.DAYS.between(LocalDate.parse(liste_res.get(i).getDebut_res(), formatter), debut);               
                if(time<30 && time>0 && liste_res.get(i).getVehicleId()==vehicleId){
                    dif=dif+ChronoUnit.DAYS.between(LocalDate.parse(liste_res.get(i).getDebut_res(), formatter), LocalDate.parse(liste_res.get(i).getFin_res(), formatter));
                }
                else{
                    verify=false;
                }
            }
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(dif>=30){
            pause=true;
            doGet(request, response);
            return;
        }
        if(ChronoUnit.DAYS.between(debut, fin)>7){
            test=true;
            doGet(request, response);
            return;
        }
        else{
            test=false;
            pause=false;
        }
        Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
        try {
            reservationService.create(reservation);          
            response.sendRedirect("/rentmanager/rents");
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
