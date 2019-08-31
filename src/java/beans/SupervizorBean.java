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

import beans.data.PojamProveraIspis;
import entities.Anagram;
import entities.Korisnik;
import entities.Pehar;
import entities.PetXPet;
import entities.PojamProvera;
import entities.Rezultat;
import entities.ZanGeo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;
import util.Helper;
import util.HibernateUtil;

/**
 * Bean for supervizor.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value = "supervizorBean")
public class SupervizorBean implements Serializable {

    List<Anagram> anagrami;
    List<PetXPet> petxpet;
    List<PojamProveraIspis> zangeo;
    
    Pehar pehar = new Pehar();

    public List<Anagram> getAnagrami() {
        return anagrami;
    }

    public void setAnagrami(List<Anagram> anagrami) {
        this.anagrami = anagrami;
    }

    public List<PetXPet> getPetxpet() {
        return petxpet;
    }

    public void setPetxpet(List<PetXPet> petxpet) {
        this.petxpet = petxpet;
    }

    public List<PojamProveraIspis> getZangeo() {
        return zangeo;
    }

    public void setZangeo(List<PojamProveraIspis> zangeo) {
        this.zangeo = zangeo;
    }

    public Pehar getPehar() {
        return pehar;
    }

    public void setPehar(Pehar pehar) {
        this.pehar = pehar;
    }
    
    private void ucitajAnagrame(Session dbs) {
        Criteria cr = dbs.createCriteria(Anagram.class);
        anagrami = cr.list();
        anagrami.add(new Anagram()); // blanko red za dodavanje novog anagrama
    }
    
    private void ucitajPetXPet(Session dbs) {
        Criteria cr = dbs.createCriteria(PetXPet.class);
        petxpet = cr.list();
        petxpet.add(new PetXPet()); // blanko red za dodavanje nove igre 5x5
    }
    
    private void ucitajPojamProvera(Session dbs) {
        Criteria cr = dbs.createCriteria(PojamProvera.class);
        List<PojamProvera> lista = cr.list();
        zangeo = new ArrayList<>();
        lista.forEach(elem -> {
            PojamProveraIspis ppi = new PojamProveraIspis(elem);
            Criteria cr2 = dbs.createCriteria(Korisnik.class);
            cr2.add(Restrictions.eq("korisnickoIme", elem.getKorisnickoIme()));
            Korisnik k = (Korisnik) cr2.uniqueResult();
            if (k == null) {
                System.out.println("Greska prilikom dohvatanja korisnika: " + elem.getKorisnickoIme());
            } else {
                ppi.setTakmicar(k);
            }
            zangeo.add(ppi);
        });
    }
    
    public SupervizorBean() {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        ucitajAnagrame(dbs);
        ucitajPetXPet(dbs);
        ucitajPojamProvera(dbs);
        dbs.close();
    }

    public void izmenaAnagrama(RowEditEvent event) {
        Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
        Anagram anagram = (Anagram) event.getObject();
        
        if (!Helper.checkValid(anagram.getZagonetka())) {
            Helper.showError("Greška!", "Niste postavili zagonetku!");
            return;
        }
        
        if (!Helper.checkValid(anagram.getResenje())) {
            Helper.showError("Greška!", "Niste postavili rešenje!");
            return;
        }

        anagram.setIdAnagram(anagrami.size() > 1 ? anagrami.get(anagrami.size() - 2).getIdAnagram() + 1 : 1);
        anagram.setResenje(anagram.getResenje().toLowerCase(locale));

        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        dbs.save(anagram);
        dbs.getTransaction().commit();
        dbs.close();
        anagrami.add(new Anagram());
        Helper.showInfo("Uspeh!", "Dodali ste novi anagram!");
    }
        
    public void izmenaPetXPet(RowEditEvent event) {
        PetXPet igra = (PetXPet) event.getObject();
        
        if (!Helper.checkValid(igra.getRec1())) {
            Helper.showError("Greška!", "Niste postavili prvu reč!");
            return;
        }        
        if (!Helper.checkValid(igra.getRec2())) {
            Helper.showError("Greška!", "Niste postavili drugu reč!");
            return;
        }        
        if (!Helper.checkValid(igra.getRec3())) {
            Helper.showError("Greška!", "Niste postavili treću reč!");
            return;
        }        
        if (!Helper.checkValid(igra.getRec4())) {
            Helper.showError("Greška!", "Niste postavili četvrtu reč!");
            return;
        }        
        if (!Helper.checkValid(igra.getRec5())) {
            Helper.showError("Greška!", "Niste postavili petu reč!");
            return;
        }
        
        igra.setIdPetXPet(petxpet.size() > 1 ? petxpet.get(petxpet.size() - 2).getIdPetXPet() + 1 : 1);
        
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        dbs.save(igra);
        dbs.getTransaction().commit();
        dbs.close();
        petxpet.add(new PetXPet());
        Helper.showInfo("Uspeh!", "Dodali ste novu igru 5x5!");
    }

    public void odustani(RowEditEvent event) {
        Helper.showInfo("Info:", "Akcija prekinuta.");
    }
     
    public void prihvati(PojamProveraIspis ispis) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(Rezultat.class);
        cr.add(Restrictions.eq("datum", ispis.getDatum()));
        cr.add(Restrictions.eq("korisnickoIme", ispis.getKorisnickoIme()));
        Rezultat rez = (Rezultat) cr.uniqueResult();
        if (rez == null) {
            System.out.println("Greska prilikom dohvatanja rezultata: " + ispis.getKorisnickoIme() + " za datum: " + ispis.getDatum());
            return;
        }
        rez.setPoeniZanGeo(rez.getPoeniZanGeo() + 4);
        cr = dbs.createCriteria(PojamProvera.class);
        cr.add(Restrictions.eq("datum", ispis.getDatum()));
        cr.add(Restrictions.eq("korisnickoIme", ispis.getKorisnickoIme()));
        cr.add(Restrictions.eq("kategorija", ispis.getKategorija()));
        PojamProvera pp = (PojamProvera) cr.uniqueResult();
        if (pp == null) {
            System.out.println("Greska prilikom dohvatanja pojma: " + ispis.getKorisnickoIme() + " za datum: " + ispis.getDatum() + " i kategoriju: " + ispis.getKategorija());
            return;
        }
        ZanGeo pojam = new ZanGeo();
        pojam.setKategorija(ispis.getKategorija());
        pojam.setPojam(ispis.getPojam());
        dbs.beginTransaction();
        dbs.update(rez);
        dbs.save(pojam);
        dbs.delete(pp);
        dbs.getTransaction().commit();
        dbs.close();
        zangeo.remove(ispis);
    }
        
    public void odbij(PojamProveraIspis ispis) {
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(PojamProvera.class);
        cr.add(Restrictions.eq("datum", ispis.getDatum()));
        cr.add(Restrictions.eq("korisnickoIme", ispis.getKorisnickoIme()));
        cr.add(Restrictions.eq("kategorija", ispis.getKategorija()));
        PojamProvera pp = (PojamProvera) cr.uniqueResult();
        if (pp == null) {
            System.out.println("Greska prilikom dohvatanja pojma: " + ispis.getKorisnickoIme() + " za datum: " + ispis.getDatum() + " i kategoriju: " + ispis.getKategorija());
            return;
        }
        dbs.beginTransaction();
        dbs.delete(pp);
        dbs.getTransaction().commit();
        dbs.close();
        zangeo.remove(ispis);
    }
    
    public void dodajPehar() {
        if (!Helper.checkValid(pehar.getPitanje1()) || !Helper.checkValid(pehar.getOdgovor1())
                || !Helper.checkValid(pehar.getPitanje2()) || !Helper.checkValid(pehar.getOdgovor2())
                || !Helper.checkValid(pehar.getPitanje3()) || !Helper.checkValid(pehar.getOdgovor3())
                || !Helper.checkValid(pehar.getPitanje4()) || !Helper.checkValid(pehar.getOdgovor4())
                || !Helper.checkValid(pehar.getPitanje5()) || !Helper.checkValid(pehar.getOdgovor5())
                || !Helper.checkValid(pehar.getPitanje6()) || !Helper.checkValid(pehar.getOdgovor6())
                || !Helper.checkValid(pehar.getPitanje7()) || !Helper.checkValid(pehar.getOdgovor7())
                || !Helper.checkValid(pehar.getPitanje8()) || !Helper.checkValid(pehar.getOdgovor8())
                || !Helper.checkValid(pehar.getPitanje9()) || !Helper.checkValid(pehar.getOdgovor9())
                || !Helper.checkValid(pehar.getPitanje10()) || !Helper.checkValid(pehar.getOdgovor10())
                || !Helper.checkValid(pehar.getPitanje11()) || !Helper.checkValid(pehar.getOdgovor11())
                || !Helper.checkValid(pehar.getPitanje12()) || !Helper.checkValid(pehar.getOdgovor12())
                || !Helper.checkValid(pehar.getPitanje13()) || !Helper.checkValid(pehar.getOdgovor13())) {
            Helper.showError("Greška!", "Sva polja su obavezna!");
            return;
        }

        Session dbs = HibernateUtil.getSessionFactory().openSession();
        dbs.beginTransaction();
        dbs.save(pehar);
        dbs.getTransaction().commit();
        dbs.close();
        pehar = new Pehar();

        Helper.showInfo("Poruka zadovoljstva!", "Uspešno ste dodali pehar!");
    }
}