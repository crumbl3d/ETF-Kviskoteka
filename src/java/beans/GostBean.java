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
import org.hibernate.transform.Transformers;
import util.HibernateUtil;

/**
 * Bean for gost.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "gostBean")
public class GostBean implements Serializable {

    ArrayList<RezultatIspis> rez20dana;
    ArrayList<RezultatIspis> rezMesec;

    public ArrayList<RezultatIspis> getRez20dana() {
        return rez20dana;
    }

    public ArrayList<RezultatIspis> getRezMesec() {
        return rezMesec;
    }
    
    public GostBean() {
        LocalDate ld = LocalDate.now();
        Date danas = Date.valueOf(ld);
        Date pocetakMeseca = Date.valueOf(ld.minusDays(ld.getDayOfMonth() - 1));

        rez20dana = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rez20dana.add(new RezultatIspis(i + 1, Date.valueOf(ld.minusDays(i))));
        }
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();

        rez20dana.forEach((ispis) -> {
            Criteria cr = dbs.createCriteria(Rezultat.class);
            cr.add(Restrictions.eq("datum", ispis.getDatum()));
            cr.add(Restrictions.eq("wip", false));
            cr.addOrder(Order.desc("poeniUkupno"));
            cr.setMaxResults(1);
            Rezultat rez = (Rezultat) cr.uniqueResult();
            if (rez != null) {
                cr = dbs.createCriteria(Korisnik.class);
                cr.add(Restrictions.eq("korisnickoIme", rez.getKorisnickoIme()));
                Korisnik k = (Korisnik) cr.uniqueResult();
                if (k == null) {
                    System.out.println("Greska prilikom dohvatanja korisnika: " + rez.getKorisnickoIme());
                } else {
                    ispis.setTakmicar(k);
                    ispis.setPoeniAnagram(rez.getPoeniAnagram());
                    ispis.setPoeniMojBroj(rez.getPoeniMojBroj());
                    ispis.setPoeniPetXPet(rez.getPoeniPetXPet());
                    ispis.setPoeniZanGeo(rez.getPoeniZanGeo());
                    ispis.setPoeniPehar(rez.getPoeniPehar());
                    ispis.setPoeniUkupno(rez.getPoeniUkupno());
                }
            }
        });
        
        Criteria cr = dbs.createCriteria(Rezultat.class);
        cr.add(Restrictions.le("datum", danas));
        cr.add(Restrictions.ge("datum", pocetakMeseca));
        cr.add(Restrictions.eq("wip", false));
        cr.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("korisnickoIme").as("korisnickoIme"))
                .add(Projections.property("datum").as("datum"))
                .add(Projections.avg("poeniAnagram").as("poeniAnagram"))
                .add(Projections.avg("poeniMojBroj").as("poeniMojBroj"))
                .add(Projections.avg("poeniPetXPet").as("poeniPetXPet"))
                .add(Projections.avg("poeniZanGeo").as("poeniZanGeo"))
                .add(Projections.avg("poeniPehar").as("poeniPehar"))
                .add(Projections.avg("poeniUkupno").as("poeniUkupno")));
        cr.addOrder(Order.desc("poeniUkupno"));
        cr.setResultTransformer(Transformers.aliasToBean(RezultatIspis.class));
        List<RezultatIspis> rezultati2 = cr.list();

        rezMesec = new ArrayList<>();
        
        for (int index = 0; index < rezultati2.size(); index++) {
            RezultatIspis rezultat = rezultati2.get(index);
            RezultatIspis ispis = new RezultatIspis(index + 1, pocetakMeseca);
            Criteria cr2 = dbs.createCriteria(Korisnik.class);
            cr2.add(Restrictions.eq("korisnickoIme", rezultat.getKorisnickoIme()));
            Korisnik k = (Korisnik) cr2.uniqueResult();
            if (k == null) {
                System.out.println("Greska prilikom dohvatanja korisnika: " + rezultat.getKorisnickoIme());
            } else {
                ispis.setTakmicar(k);
                ispis.setPoeniAnagram(rezultat.getPoeniAnagram());
                ispis.setPoeniMojBroj(rezultat.getPoeniMojBroj());
                ispis.setPoeniPetXPet(rezultat.getPoeniPetXPet());
                ispis.setPoeniZanGeo(rezultat.getPoeniZanGeo());
                ispis.setPoeniPehar(rezultat.getPoeniPehar());
                ispis.setPoeniUkupno(rezultat.getPoeniUkupno());
                rezMesec.add(ispis);
            }
        }
        
        dbs.close();
    }    
}