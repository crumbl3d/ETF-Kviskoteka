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
package beans.data;

import entities.Korisnik;
import entities.PojamProvera;
import java.sql.Date;

/**
 * Used in dataTable for displaying games of the day.
 * @author crumbl3d
 */
public class PojamProveraIspis {

    Date datum;
    
    String korisnickoIme;
    Korisnik takmicar;  
    
    String kategorija;
    String pojam;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }
    
    public Korisnik getTakmicar() {
        return takmicar;
    }

    public void setTakmicar(Korisnik takmicar) {
        this.takmicar = takmicar;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String getPojam() {
        return pojam;
    }

    public void setPojam(String pojam) {
        this.pojam = pojam;
    }
    
    public PojamProveraIspis() {}
        
    public PojamProveraIspis(PojamProvera pp) {
        datum = pp.getDatum();
        korisnickoIme = pp.getKorisnickoIme();
        kategorija = pp.getKategorija();
        pojam = pp.getPojam();
    }
}