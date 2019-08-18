/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author crumbl3d
 */
@Entity
@Table(name="korisnik")
public class Korisnik implements Serializable {

    @Id
    @Column(name="korisnickoIme")
    String korisnickoIme;

    @Column(name="lozinka")
    String lozinka;
    
    @Column(name="vrsta")
    String vrsta;
    
    @Column(name="ime")
    String ime;
    
    @Column(name="prezime")
    String prezime;
    
    @Column(name="mejl")
    String mejl;
    
    @Column(name="zanimanje")
    String zanimanje;
    
    @Column(name="pol")
    String pol;
    
    @Column(name="jmbg")
    String jmbg;
    
    @Column(name="tajnoPitanje")
    String tajnoPitanje;
    
    @Column(name="odgovor")
    String odgovor;

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getTajnoPitanje() {
        return tajnoPitanje;
    }

    public void setTajnoPitanje(String tajnoPitanje) {
        this.tajnoPitanje = tajnoPitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }
}