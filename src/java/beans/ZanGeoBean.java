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

import controllers.GameController;
import entities.IgraDana;
import entities.Korisnik;
import entities.PojamProvera;
import entities.ZanGeo;
import java.io.Serializable;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import util.Helper;
import util.HibernateUtil;

/**
 * Bean for zangeo.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="zanGeoBean")
public class ZanGeoBean implements Serializable {

    IgraDana igra;
    Korisnik takmicar;
    int status, preostaloVreme, brojPoena;
    String poruka, slovo;
    String[] nazivi, kategorije, pojmovi, poeni;
    boolean tajmerZaustavljen, wip;
    
    public int getStatus() {
        return status;
    }

    public int getPreostaloVreme() {
        return preostaloVreme;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public String getPoruka() {
        return poruka;
    }

    public String getSlovo() {
        return slovo;
    }

    public String[] getNazivi() {
        return nazivi;
    }

    public String[] getPojmovi() {
        return pojmovi;
    }

    public String[] getPoeni() {
        return poeni;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
    }

    public boolean isWip() {
        return wip;
    }

    public void start() {
        status = 1;
        preostaloVreme = 120;
    }
    
    public void tajmerTick() {
        if (!tajmerZaustavljen) {
            preostaloVreme--;
            if (preostaloVreme == 0) {
                proveri();
            }
        }
    }

    public void proveri() {
        tajmerZaustavljen = true;
        status = 2;
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();

        brojPoena = 0;
        wip = false;
        int supervizor = 0;
        for (int i = 0; i < 8; i++) {
            String pojam = pojmovi[i].isEmpty() ? null : slovo + pojmovi[i];
            int p = proveriPojam(kategorije[i], pojam, dbs);
            if (p == -1) {
                poeni[i] = "?";
                wip = true;
                supervizor++;
                dodajProveruPojma(kategorije[i], pojam, dbs);
            } else {
                poeni[i] = String.valueOf(p);
                brojPoena += p;
            }
        }

        dbs.close();
        
        if (supervizor > 0) {
            poruka = "Uneli ste " + supervizor + (supervizor == 1 ? " pojam" : (supervizor < 5 ? " pojma" : " pojmova")) + " koji ne postoje u bazi (označeni sa '?'). Vaš rezultat će se pojaviti na rang listi tek nakon što supervizor proveri date pojmove. Imaćete najmanje " + brojPoena + " poena, a najviše " + (brojPoena + 4 * supervizor) + " poena.";
        } else if (brojPoena > 0) {
            poruka = "Čestitamo! Osvojili ste " + brojPoena + " poena.";
        } else {
            poruka = "Nažalost u ovoj igri niste osvojili poene.";
        }
    }
    
    private int proveriPojam(String kategorija, String pojam, Session dbs) {
        if (pojam == null || pojam.isEmpty()) {
            return 0;
        }
        Criteria cr = dbs.createCriteria(ZanGeo.class);
        cr.add(Restrictions.eq("kategorija", kategorija));
        cr.add(Restrictions.ilike("pojam", pojam, MatchMode.ANYWHERE));
        ZanGeo z = (ZanGeo) cr.uniqueResult();
        return z != null ? 2 : -1;
    }
    
    private void dodajProveruPojma(String kategorija, String pojam, Session dbs) {
        if (pojam == null || pojam.isEmpty()) {
            return;
        }
        PojamProvera provera = new PojamProvera();
        provera.setDatum(igra.getDatum());
        provera.setKorisnickoIme(takmicar.getKorisnickoIme());
        provera.setKategorija(kategorija.toLowerCase());
        provera.setPojam(pojam.toLowerCase());
        dbs.beginTransaction();
        dbs.save(provera);
        dbs.getTransaction().commit();
    }
    
    public ZanGeoBean() {
        GameController gctl = GameController.getCurrentInstance();
        igra = gctl.getIgra();
        if (igra == null) {
            Helper.showFatal("Interna greška!", "Igra nije učitana!");
            return;
        }
        takmicar = gctl.getTakmicar();
        if (takmicar == null) {
            Helper.showFatal("Interna greška!", "Takmičar nije ulogovan!");
            return;
        }
        
        String[] latinica = new String[] {
            "A", "B", "C", "Č", "Ć",
            "D", "Dž", "Đ", "E", "F",
            "G", "H", "I", "J", "K",
            "L", "Lj", "M", "N", "Nj",
            "O", "P", "R", "S", "Š",
            "T", "U", "V", "Z", "Ž" };
        Random generator = new Random(igra.getDatum().getTime());
        slovo = latinica[generator.nextInt(30)];
        
        nazivi = new String[] { "Država", "Grad", "Jezero", "Planina", "Reka", "Životinja", "Biljka", "Muzička grupa" };
        pojmovi = new String[] { "", "", "", "", "", "", "", "" };
        poeni = new String[] { "", "", "", "", "", "", "", "" };
        kategorije = new String[] { "drzava", "grad", "jezero", "planina", "reka", "zivotinja", "biljka", "grupa" };
    }
}