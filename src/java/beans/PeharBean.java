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
import entities.Pehar;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.PrimeFaces;
import util.Helper;
import util.HibernateUtil;

/**
 * Bean for pehar.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="peharBean")
public class PeharBean implements Serializable {

    UIComponent pgPehar;
    
    Pehar pehar;
    int status, preostaloVreme, brojPoena, indeks;
    int[] poeni, poeniProtivnik;
    String poruka, trenutnoPitanje, trenutniOdgovor;
    String[] pitanja, tacniOdgovori, odgovori;
    boolean tajmerZaustavljen;

    public UIComponent getPgPehar() {
        return pgPehar;
    }

    public void setPgPehar(UIComponent pgPehar) {
        this.pgPehar = pgPehar;
    }

    public int getStatus() {
        return status;
    }

    public int getPreostaloVreme() {
        return preostaloVreme;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public int getIndeks() {
        return indeks;
    }

    public int[] getPoeni() {
        return poeni;
    }
    
    public int[] getPoeniProtivnik() {
        return poeniProtivnik;
    }
    
    public String getPoruka() {
        return poruka;
    }

    public String getTrenutnoPitanje() {
        return trenutnoPitanje;
    }

    public String getTrenutniOdgovor() {
        return trenutniOdgovor;
    }

    public void setTrenutniOdgovor(String trenutniOdgovor) {
        this.trenutniOdgovor = trenutniOdgovor;
    }

    public String[] getPitanja() {
        return pitanja;
    }

    public String[] getTacniOdgovori() {
        return tacniOdgovori;
    }

    public String[] getOdgovori() {
        return odgovori;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
    }

    public void start() {
        status = 1;
        preostaloVreme = 30;
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
        if (indeks > 12) {
            return;
        }
        String s1 = trenutniOdgovor.trim().replaceAll("\\s+", "_"), s2 = tacniOdgovori[indeks].trim().replaceAll("\\s+", "_");
        if (s1.equalsIgnoreCase(s2)) {
            poeni[indeks] = s2.length();
            brojPoena += poeni[indeks];
            odgovori[indeks] = trenutniOdgovor.toUpperCase();
        } else {
            poeniProtivnik[indeks] = s2.length();
        }
        indeks++;
        if (indeks == 13) {
            tajmerZaustavljen = true;
            status = 3;
            poruka = "temp";
            if (PrimeFaces.current().isAjaxRequest()) {
                PrimeFaces.current().ajax().update("form:kraj");
            }
        } else {
            trenutnoPitanje = pitanja[indeks];
            trenutniOdgovor = "";
            preostaloVreme = 30;
        }
        if (PrimeFaces.current().isAjaxRequest()) {
            PrimeFaces.current().ajax().update("form:pehar");
        }
    }

    public PeharBean() {
        GameController gctl = GameController.getCurrentInstance();
        IgraDana igra = gctl.getIgra();
        if (igra == null) {
            Helper.showFatal("Interna greška!", "Igra nije učitana!");
            return;
        }
        Korisnik takmicar = gctl.getTakmicar();
        if (takmicar == null) {
            Helper.showFatal("Interna greška!", "Takmičar nije ulogovan!");
            return;
        }
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(Pehar.class);
        cr.add(Restrictions.eq("idPehar", igra.getIdPehar()));
        pehar = (Pehar) cr.uniqueResult();
        if (pehar == null) {
            Helper.showFatal("Interna greška!", "Nevalidan id pehara: " + igra.getIdPehar() + "!");
            return;
        }

        poeni = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        poeniProtivnik = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        pitanja = new String[] {
            pehar.getPitanje1(), pehar.getPitanje2(), pehar.getPitanje3(),
            pehar.getPitanje4(), pehar.getPitanje5(), pehar.getPitanje6(),
            pehar.getPitanje7(), pehar.getPitanje8(), pehar.getPitanje9(),
            pehar.getPitanje10(), pehar.getPitanje11(), pehar.getPitanje12(),
            pehar.getPitanje13() };
        tacniOdgovori = new String[] {
            pehar.getOdgovor1().toUpperCase(), pehar.getOdgovor2().toUpperCase(), pehar.getOdgovor3().toUpperCase(),
            pehar.getOdgovor4().toUpperCase(), pehar.getOdgovor5().toUpperCase(), pehar.getOdgovor6().toUpperCase(),
            pehar.getOdgovor7().toUpperCase(), pehar.getOdgovor8().toUpperCase(), pehar.getOdgovor9().toUpperCase(),
            pehar.getOdgovor10().toUpperCase(), pehar.getOdgovor11().toUpperCase(), pehar.getOdgovor12().toUpperCase(),
            pehar.getOdgovor13().toUpperCase() };
        odgovori = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "" };

        trenutnoPitanje = pehar.getPitanje1();
        trenutniOdgovor = "";
    }
}