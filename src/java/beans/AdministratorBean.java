/* 
 * The MIT License
 *
 * Copyright 2019 crumbl3d.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package beans;

import beans.data.IgraDanaIspis;
import entities.Anagram;
import entities.IgraDana;
import entities.Korisnik;
import entities.Pehar;
import entities.PetXPet;
import entities.Rezultat;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;
import util.HibernateUtil;
import util.SessionUtil;

/**
 * Bean for administrator.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "administratorBean")
public class AdministratorBean implements Serializable {

    List<Korisnik> zahtevi;
    List<IgraDanaIspis> igreDana;
    List<Integer> anagrami, petxpet, pehari;

    java.util.Date danas, datum;
    
    public List<Korisnik> getZahtevi() {
        return zahtevi;
    }
    
    public void setZahtevi(List<Korisnik> zahtevi) {
        this.zahtevi = zahtevi;
    }

    public List<IgraDanaIspis> getIgreDana() {
        return igreDana;
    }

    public void setIgreDana(List<IgraDanaIspis> igreDana) {
        this.igreDana = igreDana;
    }

    public List<Integer> getAnagrami() {
        return anagrami;
    }

    public void setAnagrami(List<Integer> anagrami) {
        this.anagrami = anagrami;
    }

    public List<Integer> getPetxpet() {
        return petxpet;
    }

    public void setPetxpet(List<Integer> petxpet) {
        this.petxpet = petxpet;
    }

    public List<Integer> getPehari() {
        return pehari;
    }

    public void setPehari(List<Integer> pehari) {
        this.pehari = pehari;
    }

    public java.util.Date getDanas() {
        return danas;
    }

    public void setDanas(java.util.Date danas) {
        this.danas = danas;
    }

    public java.util.Date getDatum() {
        return datum;
    }

    public void setDatum(java.util.Date datum) {
        this.datum = datum;
    }
    
    public AdministratorBean() {
        this.danas = java.util.Calendar.getInstance().getTime();

        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(Korisnik.class);
        cr.add(Restrictions.and(Restrictions.ne("vrsta", "administrator"),
                Restrictions.ne("vrsta", "takmicar"),
                Restrictions.ne("vrsta", "supervizor")));
        zahtevi = cr.list();
        cr = dbs.createCriteria(IgraDana.class);
        List<IgraDana> igre = cr.list();
        Date danas = Date.valueOf(LocalDate.now());
        igreDana = new ArrayList<>();

        igre.forEach((igra) -> {
            IgraDanaIspis ispis = new IgraDanaIspis();
            Criteria cr2 = dbs.createCriteria(Rezultat.class);
            cr2.add(Restrictions.eq("datum", igra.getDatum()));
            cr2.setProjection(Projections.rowCount());
            int brojOdigranih = ((Number) cr2.uniqueResult()).intValue();
            ispis.setDatum(igra.getDatum());
            ispis.setIdAnagram(igra.getIdAnagram());
            ispis.setIdPetXPet(igra.getIdPetXPet());
            ispis.setIdPehar(igra.getIdPehar());
            ispis.setIzmenljiva(brojOdigranih == 0 && igra.getDatum().compareTo(danas) >= 0);
            igreDana.add(ispis);
        });
        
        igreDana.add(new IgraDanaIspis());

        cr = dbs.createCriteria(Anagram.class);
        cr.setProjection(Projections.property("idAnagram"));
        anagrami = cr.list();

        cr = dbs.createCriteria(PetXPet.class);
        cr.setProjection(Projections.property("idPetXPet"));
        petxpet = cr.list();

        cr = dbs.createCriteria(Pehar.class);
        cr.setProjection(Projections.property("idPehar"));
        pehari = cr.list();
        
        dbs.close();
    }
    
    public void prihvati(Korisnik zahtev) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        zahtev.setVrsta("takmicar");
        dbs.update(zahtev);
        dbs.getTransaction().commit();
        dbs.close();
        zahtevi.remove(zahtev);
    }
        
    public void odbij(Korisnik zahtev) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        dbs.delete(zahtev);
        dbs.getTransaction().commit();
        dbs.close();
        zahtevi.remove(zahtev);
    }
    
    public void onRowEdit(RowEditEvent event) {
        IgraDanaIspis ispis = (IgraDanaIspis) event.getObject();

        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", ispis.getDatum()));
        IgraDana igra = (IgraDana) cr.uniqueResult();

        if (ispis == igreDana.get(igreDana.size() - 1)) {
            // menja se poslednji red (dodajemo novu igru)
            if (igra != null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", 
                        "Baza već sadrži igru dana za datum: " + ispis.getDatum()));
                igreDana.set(igreDana.size() - 1, new IgraDanaIspis());
                dbs.close();
                return;
            }
            ispis.setDatum(new java.sql.Date(datum.getTime()));
            igra = new IgraDana();
            igra.setDatum(ispis.getDatum());
            igra.setIdAnagram(ispis.getIdAnagram());
            igra.setIdPetXPet(ispis.getIdPetXPet());
            igra.setIdPehar(ispis.getIdPehar());
            dbs.beginTransaction();
            dbs.save(igra);
            dbs.getTransaction().commit();
            dbs.close();
            igreDana.add(new IgraDanaIspis());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Igra dana dodata!", 
                    "Datum: " + ispis.getDatum()));
        } else {
            if (igra == null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Interna greška!", 
                        "Baza ne sadrži igru dana za datum: " + ispis.getDatum()));
                dbs.close();
                return;
            }
            igra.setIdAnagram(ispis.getIdAnagram());
            igra.setIdPetXPet(ispis.getIdPetXPet());
            igra.setIdPehar(ispis.getIdPehar());
            dbs.beginTransaction();
            dbs.update(igra);
            dbs.getTransaction().commit();
            dbs.close();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Igra dana izmenjena!", 
                    "Datum: " + ispis.getDatum()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Izmena prekinuta!", 
                "Datum: " + ((IgraDanaIspis) event.getObject()).getDatum()));
    }
}