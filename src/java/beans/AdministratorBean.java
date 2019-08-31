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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;

import util.Helper;
import util.HibernateUtil;

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

    public List<IgraDanaIspis> getIgreDana() {
        return igreDana;
    }

    public void setIgreDana(List<IgraDanaIspis> igreDana) {
        this.igreDana = igreDana;
    }

    public List<Integer> getAnagrami() {
        return anagrami;
    }

    public List<Integer> getPetxpet() {
        return petxpet;
    }

    public List<Integer> getPehari() {
        return pehari;
    }

    public java.util.Date getDanas() {
        return danas;
    }

    public java.util.Date getDatum() {
        return datum;
    }

    public void setDatum(java.util.Date datum) {
        this.datum = datum;
    }
    
    private void ucitajZahteve(Session dbs) {
        Criteria cr = dbs.createCriteria(Korisnik.class);
        cr.add(Restrictions.and(Restrictions.ne("vrsta", "administrator"),
                Restrictions.ne("vrsta", "takmicar"),
                Restrictions.ne("vrsta", "supervizor")));
        zahtevi = cr.list();
    }
    
    private void ucitajIgreDana(Session dbs) {
        Criteria cr = dbs.createCriteria(IgraDana.class);
        List<IgraDana> igre = cr.list();
        
        igreDana = new ArrayList<>();
        
        igre.forEach((igra) -> {
            Criteria cr2 = dbs.createCriteria(Rezultat.class);
            cr2.add(Restrictions.eq("datum", igra.getDatum()));
            cr2.setProjection(Projections.rowCount());
            int brojOdigranih = ((Number) cr2.uniqueResult()).intValue();
            IgraDanaIspis ispis = new IgraDanaIspis(igra);
            ispis.setIzmenljiva(brojOdigranih == 0 && igra.getDatum().compareTo(java.sql.Date.valueOf(LocalDate.now())) >= 0);
            igreDana.add(ispis);
        });
        igreDana.add(new IgraDanaIspis()); // blanko red za dodavanje nove igre dana
    }
    
    private void ucitajIndekse(Session dbs) {
        Criteria cr = dbs.createCriteria(Anagram.class);
        cr.setProjection(Projections.property("idAnagram"));
        anagrami = cr.list();

        cr = dbs.createCriteria(PetXPet.class);
        cr.setProjection(Projections.property("idPetXPet"));
        petxpet = cr.list();

        cr = dbs.createCriteria(Pehar.class);
        cr.setProjection(Projections.property("idPehar"));
        pehari = cr.list();
    }
    
    public AdministratorBean() {
        this.danas = java.util.Calendar.getInstance().getTime();
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        ucitajZahteve(dbs);
        ucitajIgreDana(dbs);
        ucitajIndekse(dbs);
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
        java.sql.Date datum = ispis.getDatum();
        if (datum == null) {
            if (this.datum == null) {
                Helper.showError("Greška!", "Niste postavili datum!");
                igreDana.set(igreDana.size() - 1, new IgraDanaIspis());
                return;
            }
            datum = new java.sql.Date(this.datum.getTime());
            ispis.setDatum(datum);
        }
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", datum));
        IgraDana igra = (IgraDana) cr.uniqueResult();

        if (ispis == igreDana.get(igreDana.size() - 1)) {
            // menja se poslednji red (dodajemo novu igru)
            if (igra != null) {
                Helper.showError("Greška!", "Baza već sadrži igru dana za datum: " + datum);
                igreDana.set(igreDana.size() - 1, new IgraDanaIspis());
                dbs.close();
                return;
            }
            igra = new IgraDana();
            igra.setDatum(datum);
            igra.setIdAnagram(ispis.getIdAnagram());
            igra.setIdPetXPet(ispis.getIdPetXPet());
            igra.setIdPehar(ispis.getIdPehar());
            dbs.beginTransaction();
            dbs.save(igra);
            dbs.getTransaction().commit();
            dbs.close();
            igreDana.sort(Comparator.comparing(c -> c.getDatum()));
            igreDana.add(new IgraDanaIspis());
            Helper.showInfo("Igra dana dodata!", "Datum: " + datum);
        } else {
            if (igra == null) {
                Helper.showFatal("Interna greška!", "Baza ne sadrži igru dana za datum: " + datum);
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
            Helper.showInfo("Igra dana izmenjena!", "Datum: " + datum);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Helper.showInfo("Izmena prekinuta!", "Datum: " + ((IgraDanaIspis) event.getObject()).getDatum());
    }
}