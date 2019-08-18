/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Korisnik;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.BCrypt;
import util.HibernateUtil;
import util.SessionUtil;

/**
 *
 * @author crumbl3d
 */
@ManagedBean
@SessionScoped
@Named(value="PrijavaBean")
public class PrijavaBean implements Serializable {

    // rezimi
    String rezim = "Prijava";
    boolean rezimPrijavljen = false;
    boolean rezimPrijava = true;
    boolean rezimRegistracija = false;
    boolean rezimPromenaLozinke = false;
    boolean rezimZaboravljenaLozinka = false;
    boolean rezimZaboravljenaLozinka1 = true;
    boolean rezimZaboravljenaLozinka2 = false;
    boolean rezimZaboravljenaLozinka3 = false;
    
    // rezim prijava
    String korisnickoIme;
    String lozinka;

    // rezim registracija
    String ime;
    String prezime;
    String email;
    String zanimanje;
    String pol;
    
    // rezim promena lozinke
    String novaLozinka;
    String novaLozinka2;
    
    // rezim zaboravljena lozinka
    String jmbg;
    String pitanje;
    String odgovor;
    Korisnik trazeni;
    
    // ulogovani korisnik
    Korisnik prijavljen;

    public String getRezim() {
        return rezim;
    }

    public void setRezim(String rezim) {
        this.rezim = rezim;
    }

    public boolean isRezimPrijavljen() {
        return rezimPrijavljen;
    }

    public void setRezimPrijavljen(boolean rezimPrijavljen) {
        this.rezimPrijavljen = rezimPrijavljen;
    }

    public boolean isRezimPrijava() {
        return rezimPrijava;
    }

    public void setRezimPrijava(boolean rezimPrijava) {
        this.rezimPrijava = rezimPrijava;
    }

    public boolean isRezimRegistracija() {
        return rezimRegistracija;
    }

    public void setRezimRegistracija(boolean rezimRegistracija) {
        this.rezimRegistracija = rezimRegistracija;
    }

    public boolean isRezimPromenaLozinke() {
        return rezimPromenaLozinke;
    }

    public void isRezimPromenaLozinke(boolean rezimPromenaLozinke) {
        this.rezimPromenaLozinke = rezimPromenaLozinke;
    }

    public boolean isRezimZaboravljenaLozinka() {
        return rezimZaboravljenaLozinka;
    }

    public void isRezimZaboravljenaLozinka(boolean rezimZaboravljenaLozinka) {
        this.rezimZaboravljenaLozinka = rezimZaboravljenaLozinka;
    }

    public boolean isRezimZaboravljenaLozinka1() {
        return rezimZaboravljenaLozinka1;
    }

    public void isRezimZaboravljenaLozinka1(boolean rezimZaboravljenaLozinka1) {
        this.rezimZaboravljenaLozinka1 = rezimZaboravljenaLozinka1;
    }

    public boolean isRezimZaboravljenaLozinka2() {
        return rezimZaboravljenaLozinka2;
    }

    public void isRezimZaboravljenaLozinka2(boolean rezimZaboravljenaLozinka2) {
        this.rezimZaboravljenaLozinka2 = rezimZaboravljenaLozinka2;
    }

    public boolean isRezimZaboravljenaLozinka3() {
        return rezimZaboravljenaLozinka3;
    }

    public void isRezimZaboravljenaLozinka3(boolean rezimZaboravljenaLozinka3) {
        this.rezimZaboravljenaLozinka3 = rezimZaboravljenaLozinka3;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

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

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }

    public String getNovaLozinka2() {
        return novaLozinka2;
    }

    public void setNovaLozinka2(String novaLozinka2) {
        this.novaLozinka2 = novaLozinka2;
    }

    public Korisnik getPrijavljen() {
        return prijavljen;
    }

    public void setPrijavljen(Korisnik prijavljen) {
        this.prijavljen = prijavljen;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public String naPrijavu() {
        reset();
        rezim = "Prijava";
        rezimPrijavljen = false;
        rezimPrijava = true;
        rezimRegistracija = false;
        rezimPromenaLozinke = false;
        rezimZaboravljenaLozinka = false;
        return "index?faces-redirect=true";
    }
    
    public String naRegistraciju() {
        reset();
        rezim = "Registracija";
        rezimPrijavljen = false;
        rezimPrijava = false;
        rezimRegistracija = true;
        rezimPromenaLozinke = false;
        rezimZaboravljenaLozinka = false;
        return "index?faces-redirect=true";
    }
    
    public String naPromenuLozinke() {
        reset();
        rezim = "Promena lozinke";
        rezimPrijavljen = false;
        rezimPrijava = false;
        rezimRegistracija = false;
        rezimPromenaLozinke = true;
        rezimZaboravljenaLozinka = false;
        return "index?faces-redirect=true";
    }
    
    public String naZaboravljenaLozinka() {
        reset();
        rezim = "Zaboravljena lozinka";
        rezimPrijavljen = false;
        rezimPrijava = false;
        rezimRegistracija = false;
        rezimPromenaLozinke = false;
        rezimZaboravljenaLozinka = true;
        return "index?faces-redirect=true";
    }

    public String povratak() {
        reset();
        if (prijavljen == null) {
            return "";
        }
        return "users/" + prijavljen.getVrsta().toLowerCase() + "?faces-redirect=true";
    }

    public void reset() {
        korisnickoIme = "";
        lozinka = "";
        novaLozinka = "";
        novaLozinka2 = "";
        jmbg = "";
        pitanje = "";
        odgovor = "";
    }
    
    public String prijava() {
        if (korisnickoIme == null || korisnickoIme.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Korisničko ime je obavezno!"));
            return "";
        }
        if (lozinka == null || lozinka.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Lozinka je obavezna!"));
            return "";
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria cr = session.createCriteria(Korisnik.class);
        cr.add(Restrictions.eq("korisnickoIme", korisnickoIme));
        Korisnik k = (Korisnik) cr.uniqueResult();

        session.close();
        
        if (k == null) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Trazeni korisnik ne postoji u bazi podataka!"));
            return "index";
        }

        if (!BCrypt.checkpw(lozinka, k.getLozinka())) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Pogrešna lozinka!"));
            return "index";
        }
        
        if (k.getVrsta().equalsIgnoreCase("takmicar") || 
            k.getVrsta().equalsIgnoreCase("administrator") ||
            k.getVrsta().equalsIgnoreCase("supervizor")) {
            reset();
            prijavljen = k;
            SessionUtil.setCurrentUser(k);
            if (k.getVrsta().equalsIgnoreCase("takmicar")) {
                rezim = "Takmičar";
            } else if (k.getVrsta().equalsIgnoreCase("administrator")) {
                rezim = "Administrator";
            } else {
                rezim = "Supervizor";
            }
            rezimPrijavljen = true;
            rezimPrijava = false;
            rezimRegistracija = false;
            rezimPromenaLozinke = false;
            return "users/" + k.getVrsta().toLowerCase() + "?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage("info",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                        "Korisnik postoji ali još uvek nije prihvaćen!"));
        return "index";
    }

    public String prijavaGost() {
        reset();
        prijavljen = null;
        SessionUtil.setCurrentUser(null);
        rezim = "Prijava";
        rezimPrijavljen = false;
        rezimPrijava = true;
        rezimRegistracija = false;
        rezimPromenaLozinke = false;
        return "public/gost?faces-redirect=true";
    }

    public String odjava() {
        reset();
        prijavljen = null;
        SessionUtil.setCurrentUser(null);
        rezim = "Prijava";
        rezimPrijavljen = false;
        rezimPrijava = true;
        rezimRegistracija = false;
        rezimPromenaLozinke = false;
        return "index?faces-redirect=true";
    }
    
    public String registracija() {
        
        return "index";
    }
    
    public String promenaLozinke() {        
        if (korisnickoIme == null || korisnickoIme.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Korisničko ime je obavezno!"));
            return "index";
        }
        if (lozinka == null || lozinka.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Stara lozinka je obavezna!"));
            return "index";
        }
        if (novaLozinka == null || novaLozinka.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Nova lozinka je obavezna!"));
            return "index";
        }
        if (novaLozinka2 == null || novaLozinka2.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Nova lozinka se mora dva puta uneti!"));
            return "index";
        }
        if (!novaLozinka.equals(novaLozinka2)) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Nove lozinke nisu iste!"));
            return "index";
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria cr = session.createCriteria(Korisnik.class);
        cr.add(Restrictions.eq("korisnickoIme", korisnickoIme));
        Korisnik k = (Korisnik) cr.uniqueResult();

        if (k == null) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Trazeni korisnik ne postoji u bazi podataka!"));
            session.close();
            return "index";
        }

        if (!BCrypt.checkpw(lozinka, k.getLozinka())) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Pogrešna lozinka!"));
            session.close();
            return "index";
        }

        if (!k.getVrsta().equalsIgnoreCase("takmicar") && 
            !k.getVrsta().equalsIgnoreCase("administrator") &&
            !k.getVrsta().equalsIgnoreCase("supervizor")) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                            "Korisnik postoji ali još uvek nije prihvaćen!"));
            session.close();
            return "index";
        }

        if (lozinka.equals(novaLozinka)) {
            FacesContext.getCurrentInstance().addMessage("info",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                            "Lozinke su identične!"));
            session.close();
            return "index";
        }
        
        session.beginTransaction();
        k.setLozinka(BCrypt.hashpw(novaLozinka, BCrypt.gensalt(12)));
        session.getTransaction().commit();
        session.close();
        return naPrijavu();
    }
    
    public String zaboravljenaLozinka(int n) {
        if (n == 1) {
            if (korisnickoIme == null || korisnickoIme.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Korisničko ime je obavezno!"));
                return "index";
            }
            if (jmbg == null || jmbg.isEmpty() || jmbg.length() != 13) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "JMBG je obavezan i mora imati tačno 13 cifara!"));
                return "index";
            }

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria cr = session.createCriteria(Korisnik.class);
            cr.add(Restrictions.eq("korisnickoIme", korisnickoIme));
            cr.add(Restrictions.eq("jmbg", jmbg));
            trazeni = (Korisnik) cr.uniqueResult();

            if (trazeni == null) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Trazeni korisnik ne postoji u bazi podataka!"));
                session.close();
                return "index";
            }

            pitanje = trazeni.getTajnoPitanje();
            rezimZaboravljenaLozinka1 = false;
            rezimZaboravljenaLozinka2 = true;
            rezimZaboravljenaLozinka3 = false;

            session.close();
            return "index?faces-redirect=true";
        } else if (n == 2) {
            if (odgovor == null || odgovor.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Odgovor je obavezan!"));
                return "index";
            }
            
            if (odgovor.equalsIgnoreCase(trazeni.getOdgovor())) {
                rezimZaboravljenaLozinka1 = false;
                rezimZaboravljenaLozinka2 = false;
                rezimZaboravljenaLozinka3 = true;
                return "index?faces-redirect=true";
            }
        } else {
            if (novaLozinka == null || novaLozinka.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Nova lozinka je obavezna!"));
                return "index";
            }
            if (novaLozinka2 == null || novaLozinka2.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Nova lozinka se mora dva puta uneti!"));
                return "index";
            }
            if (!novaLozinka.equals(novaLozinka2)) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Nove lozinke nisu iste!"));
                return "index";
            }
            if (trazeni == null) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
                                "Interna greška! Nije upamćen korisnik za kog se menja šifra!"));
                return "index";
            }

            if (BCrypt.checkpw(novaLozinka, trazeni.getLozinka())) {
                FacesContext.getCurrentInstance().addMessage("info",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                                "Lozinke su identične!"));
                return "index";
            } else {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                trazeni.setLozinka(BCrypt.hashpw(novaLozinka, BCrypt.gensalt(12)));
                session.update(trazeni);
                session.getTransaction().commit();
                session.close();
            }
        }

        trazeni = null;
        rezimZaboravljenaLozinka1 = true;
        rezimZaboravljenaLozinka2 = false;
        rezimZaboravljenaLozinka3 = false;
        return naPrijavu();
    }
}