package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

@WebServlet("/users")
public class UserListServlet extends HttpServlet{

    @Autowired
    ClientService clientService;

    @Autowired
    ClientDao clientDao;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(request.getParameter("search")==null){         
            try {
                request.setAttribute("listUsers", this.clientService.findAll());
            }
            catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                request.setAttribute("listUsers", this.clientService.findClientByName(request.getParameter("nom")));
            } catch (ServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
    
        if(request.getParameter("id")!=null){
            try {
                clientDao.delete(clientDao.findByIdClient(Integer.parseInt(request.getParameter("id"))));
            } catch (DaoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        doGet(request, response);
    }
}
