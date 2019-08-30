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
package controllers;

import entities.IgraDana;
import entities.Korisnik;
import entities.Rezultat;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.Helper;
import util.HibernateUtil;
import util.SessionUtil;

/**
 * Manages login and logout...
 * @author crumbl3d
 */
@ManagedBean
@SessionScoped
@Named(value = "gameController")
public class GameController implements Serializable {

    IgraDana igra;
    Korisnik takmicar;
    Rezultat rezultat;

    public static GameController getCurrentInstance() {
        return (GameController) Helper.getObject("gameController");
    }
    
    public IgraDana getIgra() {
        return igra;
    }
    
    public Korisnik getTakmicar() {
        return takmicar;
    }
    
    public Rezultat getRezultat() {
        return rezultat;
    }
    
    public String zapocniIgruDana() {
        reset();
        LoginController lctl = LoginController.getCurrentInstance();
        if (!lctl.ulogovan()) {
            return "";
        }
        if (!lctl.getKorisnik().getVrsta().equals("takmicar")) {
            return "";
        }
        takmicar = lctl.getKorisnik();
        Date danas = Date.valueOf(LocalDate.now());
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(IgraDana.class);
        cr.add(Restrictions.eq("datum", danas));
        igra = (IgraDana) cr.uniqueResult();
        if (igra == null) {
            dbs.close();
            return "";
        }
        SessionUtil.setIgraUToku(true);
        rezultat = new Rezultat();
        rezultat.setDatum(danas);
        rezultat.setKorisnickoIme(SessionUtil.getCurrentUser().getKorisnickoIme());
        dbs.beginTransaction();
        dbs.saveOrUpdate(rezultat);
        dbs.getTransaction().commit();
        dbs.close();
        return "/games/anagram.xhtml?faces-redirect=true";
    }
    
    public String krajAnagram(int brojPoena) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        rezultat.setPoeniAnagram(brojPoena);
        dbs.update(rezultat);
        dbs.getTransaction().commit();
        dbs.close();
        return "/games/mojbroj.xhtml?faces-redirect=true";
    }
    
    public String krajMojBroj(int brojPoena) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        rezultat.setPoeniMojBroj(brojPoena);
        dbs.update(rezultat);
        dbs.getTransaction().commit();
        dbs.close();
        return "/games/zangeo.xhtml?faces-redirect=true";
    }
    
    public String krajZanGeo(int brojPoena) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        rezultat.setPoeniMojBroj(brojPoena);
        dbs.update(rezultat);
        dbs.getTransaction().commit();
        dbs.close();
        return "/games/pehar.xhtml?faces-redirect=true";
    }
    
    public String odustani() {
        return "/users/takmicar.xhtml?faces-redirect=true";
    }
    
    private void reset() {
        takmicar = null;
        igra = null;
        rezultat = null;
        SessionUtil.setIgraUToku(null);
    }
    
    // temporary, remove!!!
    public GameController() {
        zapocniIgruDana();
    }
}