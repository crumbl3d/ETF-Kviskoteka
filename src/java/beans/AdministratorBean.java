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
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

    Korisnik korisnik;
    List<Korisnik> zahtevi;

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    public List<Korisnik> getZahtevi() {
        return zahtevi;
    }
    
    public void setZahtevi(List<Korisnik> zahtevi) {
        this.zahtevi = zahtevi;
    }
    
    public AdministratorBean() {
        korisnik = SessionUtil.getCurrentUser();
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(Korisnik.class);
        cr.add(Restrictions.and(Restrictions.ne("vrsta", "administrator"),
                Restrictions.ne("vrsta", "takmicar"),
                Restrictions.ne("vrsta", "supervizor")));
        zahtevi = cr.list();
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
}