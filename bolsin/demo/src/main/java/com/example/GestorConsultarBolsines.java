package com.example;
import java.util.List;

public class GestorConsultarBolsines {
    private Sesion actual;
    private Object pantalla; 
    private Empleado empleado;
    private Empleado logueado;
    private Bolsin bolsin;
    private Sesion sesion;
    private Object interfazGMaps; 
    private int nroPrecinto;
    private String localizacionBolsines;
    private Object seleccionCM;
    private List<Bolsin> bolsinesEnviados;
    private ComisionMedica comisionMedica;
    private String emailGCM;

    public List<Bolsin> getBolsinesEnviados() { return bolsinesEnviados; }
    public String getLocalizacionBolsines() { return localizacionBolsines; }
}