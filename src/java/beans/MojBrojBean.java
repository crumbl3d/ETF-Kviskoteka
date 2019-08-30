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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.Helper;

/**
 * Bean for mojbroj.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="mojBrojBean")
public class MojBrojBean implements Serializable {

    Random generator;
    int status, preostaloVreme, indeks, trazeniBroj, prijavljenBroj, brojPoena;
    List<Integer> brojevi;
    List<Boolean> blokirani;
    Stack<Integer> redosledKliktanja;
    String izraz, poruka;
    boolean tajmerZaustavljen;

    public int getStatus() {
        return status;
    }

    public int getPreostaloVreme() {
        return preostaloVreme;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public int getPrijavljenBroj() {
        return prijavljenBroj;
    }

    public List<Integer> getBrojevi() {
        return brojevi;
    }

    public List<Boolean> getBlokirani() {
        return blokirani;
    }

    public String getIzraz() {
        return izraz;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
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
        if (status != 1) {
            return;
        }
        if (indeks < 3) {
            int cifra = generator.nextInt(10);
            trazeniBroj += cifra * ((int) Math.pow(10, 2 - indeks));
            brojevi.set(indeks++, cifra);
        } else if (indeks < 7) {
            int broj = generator.nextInt(9) + 1;
            brojevi.set(indeks++, broj);
        } else if (indeks == 7) {
            int broj = 10 + generator.nextInt(3) * 5;
            brojevi.set(indeks++, broj);
        } else {
            int broj = (generator.nextInt(4) + 1) * 25;
            brojevi.set(indeks++, broj);
            status = 2;
            preostaloVreme = 60;
            indeks = 0;
        }
    }
    
    public void klik(int i) {
        if (status != 2 || i < 3 || i > 14) {
            return;
        }
        if (i < 9) { // broj
            izraz += brojevi.get(i);
            blokirani.set(i, true);
        } else if (i == 9) { // otvorena zagrada
            izraz += "(";
        } else if (i == 10) { // zatvorena zagrada
            izraz += ")";
        } else if (i == 11) { // zbir
            izraz += " + ";
        } else if (i == 12) { // razlika
            izraz += " - ";
        } else if (i == 13) { // proizvod
            izraz += " * ";
        } else if (i == 14) { // kolicnik
            izraz += " / ";
        }
        redosledKliktanja.push(i);
    }
    
    public void obrisi() {
        if (status != 2 || redosledKliktanja.empty()) {
            return;
        }
        int i = redosledKliktanja.pop(), r;
        if (i < 9) { // broj
            r = Integer.toString(brojevi.get(i)).length();
            blokirani.set(i, false);
        } else if (i < 11) { // zagrade
            r = 1;
        } else { // ostale operacije
            r = 3;
        }
        izraz = izraz.substring(0, izraz.length() - r);
    }
    
    public void klikPrijava(int i) {
        if (status != 2 || i < 0 || i > 9 || indeks > 2) {
            return;
        }
        int stotina = prijavljenBroj / 10;
        int desetica = prijavljenBroj % 10;
        prijavljenBroj = prijavljenBroj * 10 + i;
        brojevi.set(9, stotina);
        brojevi.set(10, desetica);
        brojevi.set(11, i);
    }

    public void obrisiPrijava() {
        if (status != 2) {
            return;
        }
        prijavljenBroj /= 10;
        int desetica = prijavljenBroj / 10;
        int jedinica = prijavljenBroj % 10;
        brojevi.set(9, 0);
        brojevi.set(10, desetica);
        brojevi.set(11, jedinica);
    }
    
    public void tacanBroj() {
        if (status != 2) {
            return;
        }
        prijavljenBroj = trazeniBroj;
        brojevi.set(9, brojevi.get(0));
        brojevi.set(10, brojevi.get(1));
        brojevi.set(11, brojevi.get(2));
    }
    
    public void proveri() {
        tajmerZaustavljen = true;
        status = 3;
        Stack<Integer> ops = new Stack<>(), vals = new Stack<>();
        boolean ispravanIzraz = true;
        int rank = 0;
        for (int i : redosledKliktanja) {
            if (i < 9) { // broj
                vals.push(brojevi.get(i));
                rank++;
            } else if (i == 9) { // otvorena zagrada
                ops.push(i);
            } else { // zatvorena zagrada i operacije
                while (!ops.empty() && (
                        i == 10 && ops.peek() != 9 || // zatvorena zagrada
                        i != 10 && priority(ops.peek()) >= priority(i))) { // ostali operatori
                    vals.push(eval(ops, vals));
                    rank--;
                    if (rank < 1) {
                        ispravanIzraz = false;
                        break;
                    }
                }
                if (i == 10) { // zatvorena zagrada
                    ops.pop();
                } else {
                    ops.push(i);
                }
            }
        }
        while (!ops.empty()) {
            vals.push(eval(ops, vals));
            rank--;
        }
        if (rank != 1) {
            poruka = "Neispravan izraz! Nažalost u ovoj igri niste osvojili poene.";
            brojPoena = 0;
            return;
        }
        int rezultat = vals.pop();
        if (rezultat == prijavljenBroj) {
            if (prijavljenBroj == trazeniBroj) {
                poruka = "Čestitamo! Uspeli ste da sastavite izraz i dobijete tačan broj! Dobili ste 10 poena!";
                brojPoena = 10;
            } else {
                double dist = perDist(prijavljenBroj, trazeniBroj);
                if (dist > 0.3) {
                    poruka = "Prijavljen broj se previše razlikuje od traženog. Nažalost u ovoj igri niste osvojili poene.";
                    brojPoena = 0;
                } else {
                    if (dist <= 0.005) {
                        brojPoena = 9;
                    } else if (dist <= 0.01) {
                        brojPoena = 8;
                    } else if (dist <= 0.02) {
                        brojPoena = 7;
                    } else if (dist <= 0.05) {
                        brojPoena = 6;
                    } else if (dist <= 0.1) {
                        brojPoena = 5;
                    } else if (dist <= 0.15) {
                        brojPoena = 4;
                    } else if (dist <= 0.2) {
                        brojPoena = 3;
                    } else if (dist <= 0.25) {
                        brojPoena = 2;
                    } else if (dist <= 0.3) {
                        brojPoena = 1;
                    }
                    poruka = "Niste pogodili tačan broj ali ste osvojili " + brojPoena + " poena!";
                }
            }
        } else {
            poruka = "Prijavili ste broj: " + prijavljenBroj + " ali ste dobili broj: " + rezultat + ". Nažalost u ovoj igri niste osvojili poene.";
            brojPoena = 0;
        }
    }

    private int priority(int op) {
        if (op == 9) return 1; // otvorena zagrada
        if (op == 11 || op == 12) return 2; // zbir i razlika
        if (op == 13 || op == 14) return 3; // proizvod i kolicnik
        return 0; // zatvorena zagrada
    }
    
    private int eval(Stack<Integer> ops, Stack<Integer> vals) {
        int v = vals.pop();
        if (!ops.empty()) {
            int op = ops.pop();
            if (op == 11) { // zbir
                v = vals.pop() + v;
            } else if (op == 12) { // razlika
                v = vals.pop() - v;
            } else if (op == 13) { // proizvod
                v = vals.pop() * v;
            } else if (op == 14) { // kolicnik
                v = vals.pop() / v;
            }
        }
        return v;
    }

    private double perDist(double a, double b) {
        double dist = Math.abs(a - b);
        return dist / b;
    }

    public MojBrojBean() {
        GameController gctl = GameController.getCurrentInstance();
        IgraDana igra = gctl.getIgra();
        if (igra == null) {
            Helper.showFatal("Interna greška!", "Igra nije učitana!");
            return;
        }
        generator = new Random(igra.getDatum().getTime());
        brojevi = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        blokirani = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false));
        redosledKliktanja = new Stack<>();
        izraz="";
    }
}