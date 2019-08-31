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

import beans.data.RezultatIspis;
import entities.IgraDana;
import entities.Korisnik;
import entities.Rezultat;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;
import util.SessionUtil;

/**
 * Bean for takmicar.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "takmicarBean")
public class TakmicarBean implements Serializable {

    int aktivanPogled;
    Rezultat mojRezultat;
    ArrayList<RezultatIspis> rez10naj;

    public int getAktivanPogled() {
        return aktivanPogled;
    }

    public void setAktivanPogled(int aktivanPogled) {
        this.aktivanPogled = aktivanPogled;
    }
    
    public Rezultat getMojRezultat() {
        return mojRezultat;
    }

    public ArrayList<RezultatIspis> getRez10naj() {
        return rez10naj;
    }

    public TakmicarBean() {
        LocalDate ld = LocalDate.now();
        Date danas = Date.valueOf(ld);

        Session dbs = HibernateUtil.getSessionFactory().openSession();
        
        Criteria cr = dbs.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", danas));
        IgraDana igra = (IgraDana) cr.uniqueResult();
        
        if (igra != null) {
            cr = dbs.createCriteria(Rezultat.class);
            cr.add(Restrictions.eq("datum", danas));
            cr.add(Restrictions.eq("korisnickoIme", SessionUtil.getCurrentUser().getKorisnickoIme()));
            mojRezultat = (Rezultat) cr.uniqueResult();
            
            cr = dbs.createCriteria(Rezultat.class);
            cr.add(Restrictions.eq("datum", igra.getDatum()));
            cr.addOrder(Order.desc("poeniUkupno"));
            cr.setMaxResults(10);
            List<Rezultat> rezultati = cr.list();
            
            rez10naj = new ArrayList<>(10);
            
            int index;
            for (index = 0; index < rezultati.size(); index++) {
                RezultatIspis ispis = new RezultatIspis(index + 1, danas);
                Criteria cr2 = dbs.createCriteria(Korisnik.class);
                cr2.add(Restrictions.eq("korisnickoIme", rezultati.get(index).getKorisnickoIme()));
                Korisnik k = (Korisnik) cr2.uniqueResult();
                if (k == null) {
                    System.out.println("Greska prilikom dohvatanja korisnika: " + rezultati.get(index).getKorisnickoIme());
                } else {
                    ispis.setTakmicar(k);
                    ispis.setPoeniAnagram(rezultati.get(index).getPoeniAnagram());
                    ispis.setPoeniMojBroj(rezultati.get(index).getPoeniMojBroj());
                    ispis.setPoeniPetXPet(rezultati.get(index).getPoeniPetXPet());
                    ispis.setPoeniZanGeo(rezultati.get(index).getPoeniZanGeo());
                    ispis.setPoeniPehar(rezultati.get(index).getPoeniPehar());
                    ispis.setPoeniUkupno(rezultati.get(index).getPoeniUkupno());
                    rez10naj.add(ispis);
                }
            }
            
            while (index != 10) {
                rez10naj.add(new RezultatIspis(++index, danas));
            }
            
            if (mojRezultat != null && !rezultati.contains(mojRezultat)) {
                cr = dbs.createCriteria(Rezultat.class);
                cr.add(Restrictions.eq("datum", danas));
                cr.add(Restrictions.ne("korisnickoIme", mojRezultat.getKorisnickoIme()));
                cr.add(Restrictions.ge("poeniUkupno", mojRezultat.getPoeniUkupno()));
                cr.setProjection(Projections.projectionList()
                    .add(Projections.rowCount()));
                index = ((Long) cr.uniqueResult()).intValue();
                RezultatIspis ispis = new RezultatIspis(index + 1, danas);
                cr = dbs.createCriteria(Korisnik.class);
                cr.add(Restrictions.eq("korisnickoIme", mojRezultat.getKorisnickoIme()));
                Korisnik k = (Korisnik) cr.uniqueResult();
                if (k == null) {
                    System.out.println("Greska prilikom dohvatanja korisnika: " + mojRezultat.getKorisnickoIme());
                } else {
                    ispis.setTakmicar(k);
                    ispis.setPoeniAnagram(mojRezultat.getPoeniAnagram());
                    ispis.setPoeniMojBroj(mojRezultat.getPoeniMojBroj());
                    ispis.setPoeniPetXPet(mojRezultat.getPoeniPetXPet());
                    ispis.setPoeniZanGeo(mojRezultat.getPoeniZanGeo());
                    ispis.setPoeniPehar(mojRezultat.getPoeniPehar());
                    ispis.setPoeniUkupno(mojRezultat.getPoeniUkupno());
                    rez10naj.add(ispis);
                }
            }
        }
        
        dbs.close();
    }
}