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
import entities.Korisnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.Helper;
import util.HibernateUtil;

/**
 * Bean for anagram.xhtml.
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

    public int getStatus() {
        return status;
    }

    public List<Integer> getIndeksi() {
        return indeksi;
    }

    public int getPreostaloVreme() {
        return preostaloVreme;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public List<Boolean> getBlokirani() {
        return blokirani;
    }

    public List<Character> getSlova() {
        return slova;
    }

    public String getPokusaj() {
        return pokusaj;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
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
        status = 2;
        if (s1.equalsIgnoreCase(s2)) {
            poruka = "Čestitamo! Uspeli ste da rešite anagram! Dobili ste 10 poena!";
            brojPoena = 10;
        } else {
            poruka = "Nažalost u ovoj igri niste osvojili poene.";
            brojPoena = 0;
        }
    }
    
    public AnagramBean() {
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
        Criteria cr = dbs.createCriteria(Anagram.class);
        cr.add(Restrictions.eq("idAnagram", igra.getIdAnagram()));
        anagram = (Anagram) cr.uniqueResult();
        if (anagram == null) {
            Helper.showFatal("Interna greška!", "Nevalidan id anagrama: " + igra.getIdAnagram() + "!");
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