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

import entities.Korisnik;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.BCrypt;
import util.Helper;
import util.HibernateUtil;

/**
 * Bean for resetLozinke.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "resetLozinkeBean")
public class ResetLozinkeBean implements Serializable {

    int korak = 1;
    String korisnickoIme;
    String jmbg;
    String odgovor;
    String novaLozinka;
    String potvrdaLozinke;
    Korisnik trazeni;

    public int getKorak() {
        return korak;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }

    public String getPotvrdaLozinke() {
        return potvrdaLozinke;
    }

    public void setPotvrdaLozinke(String potvrdaLozinke) {
        this.potvrdaLozinke = potvrdaLozinke;
    }

    public Korisnik getTrazeni() {
        return trazeni;
    }

    public void prviKorak() {
        if (korak != 1 || !Helper.checkValid(korisnickoIme) || !Helper.checkValid(jmbg)) {
            return;
        }

        Session dbs = HibernateUtil.getSessionFactory().openSession();

        Criteria cr = dbs.createCriteria(Korisnik.class);
        cr.add(Restrictions.eq("korisnickoIme", korisnickoIme));
        cr.add(Restrictions.eq("jmbg", jmbg));
        trazeni = (Korisnik) cr.uniqueResult();

        dbs.close();

        if (!Helper.checkValid(trazeni, "Traženi korisnik ne postoji u bazi podataka!")) {
            return;
        }

        korak++;
    }
    
    public void drugiKorak() {
        if (korak != 2 || !Helper.checkValid(odgovor)
            || !Helper.checkValid(trazeni, "Interna greška! Nije upamćen korisnik za kog se menja šifra!")) {
            return;
        }

        if (!BCrypt.checkpw(odgovor, trazeni.getOdgovor())) {
            Helper.showError("Greška!", "Pogrešan odgovor!");
            korak = 0;
            return;
        }

        korak++;
    }
    
    public String treciKorak() {
        if (korak != 3 || !Helper.checkValid(novaLozinka) || !Helper.checkValid(potvrdaLozinke)
            || !Helper.checkValid(trazeni, "Interna greška! Nije upamćen korisnik za kog se menja šifra!")) {
            return "";
        }
        if (!novaLozinka.equals(potvrdaLozinke)) {
            Helper.showError("Greška!", "Potvrda lozinke nije ispravna! Proverite ispravnost unosa!");
            return "";
        }
        if (BCrypt.checkpw(novaLozinka, trazeni.getLozinka())) {
            Helper.showWarning(null, "Nova lozinka je identična staroj!");
            return "";
        }

        Session dbs = HibernateUtil.getSessionFactory().openSession();

        dbs.beginTransaction();
        trazeni.setLozinka(BCrypt.hashpw(novaLozinka, BCrypt.gensalt(12)));
        dbs.update(trazeni);
        dbs.getTransaction().commit();

        dbs.close();
        return "/public/prijava?faces-redirect=true";
    }
}