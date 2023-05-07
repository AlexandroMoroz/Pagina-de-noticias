package com.EGG.Noticias.Entidades;

import com.EGG.Noticias.enumeraciones.Rol;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Periodista extends Usuario{

    private ArrayList<Noticia> noticia;

    private Integer sueldoMensual;

    @Temporal(TemporalType.DATE)
    private Date baja;

    public Periodista() {
    }
 
    public Periodista(ArrayList<Noticia> noticia, Integer sueldoMensual, Date baja, String id, String nombre, String email, String pass, Rol rol) {
        super(id, nombre, email, pass, rol);
        this.noticia = noticia;
        this.sueldoMensual = sueldoMensual;
        this.baja = baja;
    }

    public ArrayList<Noticia> getNoticia() {
        return noticia;
    }

    public void setNoticia(ArrayList<Noticia> noticia) {
        this.noticia = noticia;
    }

    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
    
}