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
import java.util.Random;
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
 * Bean for zangeo.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="zanGeoBean")
public class ZanGeoBean implements Serializable {

    int status, preostaloVreme, brojPoena;
    String poruka, slovo, drzava, grad, jezero, reka, planina, zivotinja, biljka, grupa;
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

    public String getPoruka() {
        return poruka;
    }

    public String getSlovo() {
        return slovo;
    }
    
    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getJezero() {
        return jezero;
    }

    public void setJezero(String jezero) {
        this.jezero = jezero;
    }

    public String getReka() {
        return reka;
    }

    public void setReka(String reka) {
        this.reka = reka;
    }

    public String getPlanina() {
        return planina;
    }

    public void setPlanina(String planina) {
        this.planina = planina;
    }

    public String getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(String zivotinja) {
        this.zivotinja = zivotinja;
    }

    public String getBiljka() {
        return biljka;
    }

    public void setBiljka(String biljka) {
        this.biljka = biljka;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
    }

    public void start() {
        status = 1;
        preostaloVreme = 120;
    }
    
    public void tajmerTick() {
        if (!tajmerZaustavljen) {
            preostaloVreme--;
            if (preostaloVreme == 0) {
                proveri();
            }
        }
    }

    public void proveri() {
        tajmerZaustavljen = true;
        status = 2;
        poruka = "temp";
    }
    
    public ZanGeoBean() {
        GameController gctl = GameController.getCurrentInstance();
        IgraDana igra = gctl.getIgra();
        if (igra == null) {
            Helper.showFatal("Interna greška!", "Igra nije učitana!");
            return;
        }
        
        String[] latinica = new String[] {
            "A", "B", "C", "Č", "Ć",
            "D", "Dž", "Đ", "E", "F",
            "G", "H", "I", "J", "K",
            "L", "Lj", "M", "N", "Nj",
            "O", "P", "R", "S", "Š",
            "T", "U", "V", "Z", "Ž" };
        Random generator = new Random(igra.getDatum().getTime());
        slovo = latinica[generator.nextInt(30)];
    }
}