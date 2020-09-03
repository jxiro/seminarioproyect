package com.example.easybuy2.Collection;


import android.widget.Toast;

import com.example.easybuy2.Utils;

public class Item {
    public int id;
    public String numDormitorios;
    public String numBanos;
    public String supTerreno;
    public String oferta;
    public String precio;
    public String yearCOns;
    public String description;
    public String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumDormitorios() {
        return numDormitorios;
    }

    public void setNumDormitorios(String numDormitorios) {
        this.numDormitorios = numDormitorios;
    }

    public String getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(String numBanos) {
        this.numBanos = numBanos;
    }

    public String getSupTerreno() {
        return supTerreno;
    }

    public void setSupTerreno(String supTerreno) {
        this.supTerreno = supTerreno;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getYearCOns() {
        return yearCOns;
    }

    public void setYearCOns(String yearCOns) {
        this.yearCOns = yearCOns;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}