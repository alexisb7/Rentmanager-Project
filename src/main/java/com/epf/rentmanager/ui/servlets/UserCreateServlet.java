package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/create")
public class UserCreateServlet extends HttpServlet{

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    List<Client> liste_client = new ArrayList<Client>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("listUsers", this.clientService.findAll());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendar.add(Calendar.YEAR, -18);
            String date_max = sdf.format(calendar.getTime());
            request.setAttribute("date_max", date_max);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(request.getParameter("verify")!=null){
            if(request.getParameter("verify").equals("false")){
                String mail_used = "Cette adresse mail est déjà utilisée.";
                request.setAttribute("nom", liste_client.get(0).getNom());
                request.setAttribute("prenom", liste_client.get(0).getPrenom());
                request.setAttribute("email", mail_used);
                request.setAttribute("naissance", liste_client.get(0).getBirth_date());
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
            }
        }
        else {
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        String birth_date = request.getParameter("birth_date");
        try {
            List<Client> liste = this.clientService.findAll();
            for(int i=0;i<liste.size();i++){
                if(liste.get(i).getEmail().equals(email)){
                    Client c = new Client(nom, prenom, birth_date);
                    liste_client.add(c);
                    request.setAttribute("verify", "false");
                    doGet(request, response);
                    return;
                }
            }
        } catch (ServiceException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }   
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            SimpleDateFormat form1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat form2 = new SimpleDateFormat("dd/MM/yyyy");
            String date = form2.format(form1.parse(birth_date));
            LocalDate naissance = LocalDate.parse(date, formatter);
            Client client = new Client(nom, prenom, email, naissance);
        try {
            clientService.create(client);
            response.sendRedirect("/rentmanager/users");
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }       
    }  
}
