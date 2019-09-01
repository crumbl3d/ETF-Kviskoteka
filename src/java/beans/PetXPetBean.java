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
import entities.Korisnik;
import entities.PetXPet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.PrimeFaces;

import util.Helper;
import util.HibernateUtil;

/**
 * Bean for petxpet.xhtml.
 * @author crumbl3d
 */
@ManagedBean
@ViewScoped
@Named(value="petXPetBean")
public class PetXPetBean implements Serializable {

    int status, preostaloVreme, brojPoena, bonusPoeni, zivoti;
    String[][] tabla, celaTabla;
    List<String> tacnaSlova, netacnaSlova;
    String[] slova;
    boolean[] blokirani;
    String poruka;
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

    public int getZivoti() {
        return zivoti;
    }

    public String[][] getTabla() {
        return tabla;
    }

    public String[][] getCelaTabla() {
        return celaTabla;
    }

    public List<String> getTacnaSlova() {
        return tacnaSlova;
    }

    public List<String> getNetacnaSlova() {
        return netacnaSlova;
    }

    public String[] getSlova() {
        return slova;
    }

    public boolean[] getBlokirani() {
        return blokirani;
    }

    public String getPoruka() {
        return poruka;
    }

    public boolean isTajmerZaustavljen() {
        return tajmerZaustavljen;
    }

    public void start() {
        status = 1;
        preostaloVreme = 90;
        zivoti = 4;
    }
    
    public void tajmerTick() {
        if (!tajmerZaustavljen) {
            preostaloVreme--;
            if (preostaloVreme == 0) {
                zavrsi();
            }
        }
    }
    
    public void klik(int id) {
        if (status != 1) {
            return;
        }
        String slovo = slova[id];
        boolean sadrzi = false;
        for (int i = 0; i < celaTabla.length; i++) {
            for (int j = 0; j < celaTabla[i].length; j++) {
                if (celaTabla[i][j].equalsIgnoreCase(slovo)) {
                    sadrzi = true;
                    tabla[i][j] = slovo;
                    brojPoena++;
                    int brojSlova = 0;
                    for (String[] recV : tabla) { // vertikalno
                        if (!recV[j].isEmpty()) {
                            brojSlova++;
                        }
                    }
                    if (brojSlova == tabla.length) {
                        brojPoena += 2;
                        bonusPoeni += 2;
                        Helper.showInfo("Čestitamo!", "Otvorili ste celu reč vertikalno i dobili 2 bonus poena!");
                        if (PrimeFaces.current().isAjaxRequest()) {
                            PrimeFaces.current().ajax().update("form:tabla", "form:msgs");
                        }
                    }
                    brojSlova = 0;
                    for (String slovoH : tabla[i]) { // horizontalno
                        if (!slovoH.isEmpty()) {
                            brojSlova++;
                        }
                    }
                    if (brojSlova == tabla.length) {
                        brojPoena += 2;
                        bonusPoeni += 2;
                        Helper.showInfo("Čestitamo!", "Otvorili ste celu reč horizontalno i dobili 2 bonus poena!");
                        if (PrimeFaces.current().isAjaxRequest()) {
                            PrimeFaces.current().ajax().update("form:tabla", "form:msgs");
                        }
                    }
                }
            }
        }

        if (sadrzi) {
            tacnaSlova.add(slovo);
            if (brojPoena - bonusPoeni == tabla.length * tabla[0].length) {
                zavrsi();
            }
        } else {
            netacnaSlova.add(slovo);
            zivoti--;
            if (zivoti == 0) {
                zavrsi();
            }
        }
        
        blokirani[id] = true;
    }

    public void zavrsi() {
        status = 2;
        tajmerZaustavljen = true;
        if (brojPoena == 0) {
            poruka = "Nažalost u ovoj igri niste osvojili poene.";
        } else {
            int brojSlova = brojPoena - bonusPoeni, brojCelihReci = bonusPoeni / 2;
            poruka = "Čestitamo! Otvorili ste " + brojSlova + " slov" + (brojSlova == 1 ? "o" : "a") + " i "
                    + (brojCelihReci < 1 ? "" : brojCelihReci + (brojCelihReci == 1 ? " celu reč" : (brojCelihReci < 5 ? " cele reči" : " celih reči")))
                    + " osvojili ste " + brojPoena + " poena!";
        }
        if (PrimeFaces.current().isAjaxRequest()) {
            PrimeFaces.current().ajax().update("form:tabla", "form:kraj");
        }
    }

    public PetXPetBean() {
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
        Criteria cr = dbs.createCriteria(PetXPet.class);
        cr.add(Restrictions.eq("idPetXPet", igra.getIdPetXPet()));
        PetXPet petxpet = (PetXPet) cr.uniqueResult();
        if (petxpet == null) {
            Helper.showFatal("Interna greška!", "Nevalidan id igre 5x5: " + igra.getIdPetXPet() + "!");
            return;
        }
        
        dbs.close();

        celaTabla = new String[][] {
            {petxpet.getS11(), petxpet.getS12(), petxpet.getS13(), petxpet.getS14(), petxpet.getS15()},
            {petxpet.getS21(), petxpet.getS22(), petxpet.getS23(), petxpet.getS24(), petxpet.getS25()},
            {petxpet.getS31(), petxpet.getS32(), petxpet.getS33(), petxpet.getS34(), petxpet.getS35()},
            {petxpet.getS41(), petxpet.getS42(), petxpet.getS43(), petxpet.getS44(), petxpet.getS45()},
            {petxpet.getS51(), petxpet.getS52(), petxpet.getS53(), petxpet.getS54(), petxpet.getS55()}
        };
        
        tabla = new String[][] {
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""}
        };

        tacnaSlova = new ArrayList<>();
        netacnaSlova = new ArrayList<>();

        slova = new String[] {
            "A", "B", "C", "Č", "Ć",
            "D", "Dž", "Đ", "E", "F",
            "G", "H", "I", "J", "K",
            "L", "Lj", "M", "N", "Nj",
            "O", "P", "R", "S", "Š",
            "T", "U", "V", "Z", "Ž"
        };

        blokirani = new boolean[] {
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false
        };
        
        for (int i = 0; i < celaTabla.length; i++) {
            for (int j = 0; j < celaTabla[i].length; j++) {
                celaTabla[i][j] = celaTabla[i][j].toUpperCase();
                if (celaTabla[i][j].equalsIgnoreCase("Dž")
                        || celaTabla[i][j].equalsIgnoreCase("Lj")
                        || celaTabla[i][j].equalsIgnoreCase("Nj")) {
                    celaTabla[i][j] = String.valueOf(celaTabla[i][j].charAt(0))
                            + String.valueOf(celaTabla[i][j].charAt(1)).toLowerCase();
                }
            }
        }
    }
}