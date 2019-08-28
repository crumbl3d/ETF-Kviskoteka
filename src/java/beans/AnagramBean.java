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
import entities.Anagram;
import entities.IgraDana;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="anagramBean")
public class AnagramBean implements Serializable {

    Anagram anagram;
    int status, preostaloVreme, brojPoena;
    List<Integer> indeksi;
    List<Boolean> blokirani;
    List<Character> slova;
    Stack<Integer> redosledKliktanja;
    String pokusaj, poruka;
    boolean tajmerZaustavljen;
    
    public Anagram getAnagram() {
        return anagram;
    }

    public void setAnagram(Anagram anagram) {
        this.anagram = anagram;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Integer> getIndeksi() {
        return indeksi;
    }

    public void setIndeksi(List<Integer> indeksi) {
        this.indeksi = indeksi;
    }

    public int getPreostaloVreme() {
        return preostaloVreme;
    }

    public void setPreostaloVreme(int preostaloVreme) {
        this.preostaloVreme = preostaloVreme;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(int brojPoena) {
        this.brojPoena = brojPoena;
    }

    public List<Boolean> getBlokirani() {
        return blokirani;
    }

    public void setBlokirani(List<Boolean> blokirani) {
        this.blokirani = blokirani;
    }

    public List<Character> getSlova() {
        return slova;
    }

    public void setSlova(List<Character> slova) {
        this.slova = slova;
    }

    public String getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String pokusaj) {
        this.pokusaj = pokusaj;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
    }

    public void setTajmerZaustavljen(boolean tajmerZaustavljen) {
        this.tajmerZaustavljen = tajmerZaustavljen;
    }

    public void start() {
        status = 1;
        preostaloVreme = 60;
    }
    
    public void tajmerTick() {
        if (!tajmerZaustavljen) {
            preostaloVreme--;
            if (preostaloVreme == 0) {
                proveri();
            }
        }
    }
    
    public void klik(int i) {
        if (status != 1) {
            return;
        }
        char c = slova.get(i);
        if (c != '_') {
            blokirani.set(i, true);
        } else {
            c = ' ';
        }
        pokusaj += c;
        redosledKliktanja.push(i);
    }
    
    public void obrisi() {
        if (status != 1) {
            return;
        }
        if (redosledKliktanja.empty()) {
            return;
        }
        int i = redosledKliktanja.pop();
        char c = slova.get(i);
        if (c != '_') {
            blokirani.set(i, false);
        }
        pokusaj = pokusaj.substring(0, pokusaj.length() - 1);
    }
    
    public void proveri() {
        tajmerZaustavljen = true;
        String s1 = pokusaj.trim().replaceAll("\\s+", "_"), s2 = anagram.getResenje().trim().replaceAll("\\s+", "_");
        System.out.println("s1: " + s1 + " s2: " + s2);
        if (s1.equalsIgnoreCase(s2)) {
            poruka = "Čestitamo! Uspeli ste da rešite anagram! Dobili ste 10 poena!";
            status = 2;
            brojPoena = 10;
        } else {
            poruka = "Nažalost niste uspeli da rešite anagram!";
            status = 3;
            brojPoena = 0;
        }
    }
    
    public AnagramBean() {
        GameController gctl = GameController.getCurrentInstance();
        IgraDana igra = gctl.getIgra();
        if (igra == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Interna greška!", "Igra nije učitana!"));
            return;
        }
        Session dbs = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = dbs.createCriteria(Anagram.class);
        cr.add(Restrictions.eq("idAnagram", igra.getIdAnagram()));
        anagram = (Anagram) cr.uniqueResult();
        if (anagram == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Interna greška!", "Nevalidan id anagrama: "
                                    + igra.getIdAnagram() + "!"));
            return;
        }

        slova = new ArrayList<>();
        
        String zagonetka = anagram.getZagonetka().toUpperCase();

        for (int i = 0; i < zagonetka.length(); i++) {
            char c = zagonetka.charAt(i);
            if (c >= 'A' && c <= 'Z' || c == 'Č' || c == 'Ć' || c == 'Š' || c == 'Ž' || c == 'Đ') {
                slova.add(c);
            }
        }
        
        slova.add('_'); // razmak

        indeksi = new ArrayList<>();
        blokirani = new ArrayList<>();
        for (int i = 0; i < slova.size(); i++) {
            indeksi.add(i);
            blokirani.add(false);
        }

        pokusaj = "";
        redosledKliktanja = new Stack<>();
        
        dbs.close();
    }
}