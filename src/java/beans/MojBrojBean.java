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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="mojBrojBean")
public class MojBrojBean implements Serializable {

    int status, preostaloVreme, brojPoena, prijavljenBroj, indeks;
    List<Integer> brojevi;
    List<Boolean> blokirani;
    Stack<Integer> redosledKliktanja;
    String izraz, poruka;
    boolean tajmerZaustavljen;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getPrijavljenBroj() {
        return prijavljenBroj;
    }

    public void setPrijavljenBroj(int prijavljenBroj) {
        this.prijavljenBroj = prijavljenBroj;
    }

    public List<Integer> getBrojevi() {
        return brojevi;
    }

    public void setBrojevi(List<Integer> brojevi) {
        this.brojevi = brojevi;
    }

    public List<Boolean> getBlokirani() {
        return blokirani;
    }

    public void setBlokirani(List<Boolean> blokirani) {
        this.blokirani = blokirani;
    }

    public String getIzraz() {
        return izraz;
    }

    public void setIzraz(String izraz) {
        this.izraz = izraz;
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
    }
    
    public void tajmerTick() {
        if (!tajmerZaustavljen) {
            preostaloVreme--;
            if (preostaloVreme == 0) {
                proveri();
            }
        }
    }
    
    public void stop() {
        if (status != 1 || indeks >= 9) {
            return;
        }
        Random r = new Random();
        int broj = r.nextInt(9) + 1;
        brojevi.set(indeks++, broj);
        if (indeks == 9) {
            status = 2;
            preostaloVreme = 60;
        }
    }
    
    public void klik(int i) {
        if (status != 2) {
            return;
        }
        System.out.println("Dugme: " + i);
//        char c = slova.get(i);
//        if (c != '_') {
//            blokirani.set(i, true);
//        } else {
//            c = ' ';
//        }
//        pokusaj += c;
        redosledKliktanja.push(i);
    }
    
    public void obrisi() {
        if (status != 2) {
            return;
        }
        if (redosledKliktanja.empty()) {
            return;
        }
        int i = redosledKliktanja.pop();
        System.out.println("Brisem: " + i);
//        char c = slova.get(i);
//        if (c != '_') {
//            blokirani.set(i, false);
//        }
//        pokusaj = pokusaj.substring(0, pokusaj.length() - 1);
    }
    
    public void proveri() {
        tajmerZaustavljen = true;
//        String s1 = pokusaj.trim().replaceAll("\\s+", "_"), s2 = anagram.getResenje().trim().replaceAll("\\s+", "_");
//        System.out.println("s1: " + s1 + " s2: " + s2);
//        if (s1.equalsIgnoreCase(s2)) {
//            poruka = "Čestitamo! Uspeli ste da rešite anagram! Dobili ste 10 poena!";
//            status = 2;
//            brojPoena = 10;
//        } else {
//            poruka = "Nažalost niste uspeli da rešite anagram!";
//            status = 3;
//            brojPoena = 0;
//        }
    }
    
    public MojBrojBean() {
//        GameController gctl = GameController.getCurrentInstance();
//        IgraDana igra = gctl.getIgra();
//        if (igra == null) {
//            FacesContext.getCurrentInstance().addMessage(null, 
//                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
//                            "Interna greška!", "Igra nije učitana!"));
//            return;
//        }

        indeks = 0;
        brojevi = new ArrayList<>();
        blokirani = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            brojevi.add(0);
            blokirani.add(false);
        }

        izraz = "";
        redosledKliktanja = new Stack<>();
    }
}