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
 * Bean for promenaLozinke.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "promenaLozinkeBean")
public class PromenaLozinkeBean implements Serializable {

    String korisnickoIme;
    String staraLozinka;
    String novaLozinka;
    String potvrdaLozinke;

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
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
    
    public String promenaLozinke() {
        if (!Helper.checkValid(korisnickoIme) || !Helper.checkValid(staraLozinka)
            || !Helper.checkValid(novaLozinka) || !Helper.checkValid(potvrdaLozinke)) {
            return "";
        }
        if (!novaLozinka.equals(potvrdaLozinke)) {
            Helper.showError("Greška!", "Potvrda lozinke nije ispravna! Proverite ispravnost unosa!");
            return "";
        }
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        
        Criteria cr = dbs.createCriteria(Korisnik.class);
        cr.add(Restrictions.eq("korisnickoIme", korisnickoIme));
        Korisnik k = (Korisnik) cr.uniqueResult();
        
        if (!Helper.checkValid(k, "Traženi korisnik ne postoji u bazi podataka!")) {
            dbs.close();
            return "";
        }
        
        if (!BCrypt.checkpw(staraLozinka, k.getLozinka())) {
            Helper.showError("Greška!", "Pogrešna lozinka!");
            dbs.close();
            return "";
        }
        
        if (!k.getVrsta().equalsIgnoreCase("takmicar") && 
            !k.getVrsta().equalsIgnoreCase("administrator") &&
            !k.getVrsta().equalsIgnoreCase("supervizor")) {
            Helper.showWarning(null, "Korisnik postoji ali još uvek nije prihvaćen!");
            dbs.close();
            return "";
        }

        if (staraLozinka.equals(novaLozinka)) {
            Helper.showWarning(null, "Nova lozinka je identična staroj!");
            dbs.close();
            return "";
        }
        
        dbs.beginTransaction();
        k.setLozinka(BCrypt.hashpw(novaLozinka, BCrypt.gensalt(12)));
        dbs.getTransaction().commit();

        dbs.close();
        return "/public/prijava";
    }
}