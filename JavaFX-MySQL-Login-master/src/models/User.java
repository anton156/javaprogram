/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


public class User {

    private String id;

    private String opis;

    private String cijena;

    private String količina;

    private String kategorija;

    private String naziv;

    private String detalji;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOpis ()
    {
        return opis;
    }

    public void setOpis (String email)
    {
        this.opis = opis;
    }

    public String getCijena ()
    {
        return cijena;
    }

    public void setCijena (String cijena)
    {
        this.cijena = cijena;
    }

    public String getKoličina ()
    {
        return količina;
    }

    public void setKoličina (String količina)
    {
        this.količina = količina;
    }

    public String getKategorija ()
    {
        return kategorija;
    }

    public void setKategorija (String gender)
    {
        this.kategorija = kategorija;
    }

    public String getDetalji ()
    {
        return detalji;
    }

    public void setDetalji (String detalji)
    {
        this.detalji = detalji;
    }

    public String getNaziv ()
    {
        return naziv;
    }

    public void setNaziv (String naziv)
    {
        this.naziv = naziv;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", opis = "+opis+", cijena = "+cijena+", količina = "+količina+", kategorija = "+kategorija+", naziv = "+naziv+", detalji = "+detalji+"]";
    }
}