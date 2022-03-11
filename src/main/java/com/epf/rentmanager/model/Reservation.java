package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    
    private int id;
    private int clientId;
    private int vehicleId;
    private LocalDate debut;
    private LocalDate fin; 
    private String debut_res;
    private String fin_res;

    public Reservation(int clientId, int vehicleId, LocalDate debut, LocalDate fin){
        this.clientId=clientId;
        this.vehicleId=vehicleId;
        this.debut=debut;
        this.fin=fin;
    }

    public Reservation(int id, int clientId, int vehicleId, LocalDate debut, LocalDate fin){
        this.id=id;
        this.clientId=clientId;
        this.vehicleId=vehicleId;
        this.debut=debut;
        this.fin=fin;
    }

    public Reservation(int id, int clientId, int vehicleId, String debut_res, String fin_res){
        this.id=id;
        this.clientId=clientId;
        this.vehicleId=vehicleId;
        this.debut_res=debut_res;
        this.fin_res=fin_res;
    }
    
    public String getDebut_res() {
        return debut_res;
    }

    public void setDebut_res(String debut_res) {
        this.debut_res = debut_res;
    }

    public String getFin_res() {
        return fin_res;
    }

    public void setFin_res(String fin_res) {
        this.fin_res = fin_res;
    }

    public int getId(){
        return this.id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public void setId(int id){
        this.id=id;
    }

    public String toString(){
        return "Id : "+this.id+" Id client : "+ this.clientId+" Id véhicule : "+ this.vehicleId+" Date de début : "+this.debut+ this.debut_res+" Date de fin : "+this.fin +this.fin_res;
    }
}