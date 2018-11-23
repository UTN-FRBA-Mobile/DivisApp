package com.gradientepolimorfico.monedapp.Entities;

public class DetalleDivisa {
    private String codigo;
    private String detalle;
    private Ubicacion ubicacion;

    public String getCodigo(){
        return this.codigo;
    }

    public String getDetalle(){
        return this.detalle;
    }

    public Ubicacion getUbicacion() {  return this.ubicacion; }
}
