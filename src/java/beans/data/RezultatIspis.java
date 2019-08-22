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
import java.sql.Date;

/**
 * Used in dataTable for displaying game results.
 * @author crumbl3d
 */
public class RezultatIspis {

    int indeks;
    Date datum;
    
    Korisnik takmicar;
    int poeniAnagram;
    int poeniMojBroj;
    int poeniPetXPet;
    int poeniZanGeo;
    int poeniPehar;
    int poeniUkupno;

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Korisnik getTakmicar() {
        return takmicar;
    }

    public void setTakmicar(Korisnik takmicar) {
        this.takmicar = takmicar;
    }

    public int getPoeniAnagram() {
        return poeniAnagram;
    }

    public void setPoeniAnagram(int poeniAnagram) {
        this.poeniAnagram = poeniAnagram;
    }

    public int getPoeniMojBroj() {
        return poeniMojBroj;
    }

    public void setPoeniMojBroj(int poeniMojBroj) {
        this.poeniMojBroj = poeniMojBroj;
    }

    public int getPoeniPetXPet() {
        return poeniPetXPet;
    }

    public void setPoeniPetXPet(int poeniPetXPet) {
        this.poeniPetXPet = poeniPetXPet;
    }

    public int getPoeniZanGeo() {
        return poeniZanGeo;
    }

    public void setPoeniZanGeo(int poeniZanGeo) {
        this.poeniZanGeo = poeniZanGeo;
    }

    public int getPoeniPehar() {
        return poeniPehar;
    }

    public void setPoeniPehar(int poeniPehar) {
        this.poeniPehar = poeniPehar;
    }

    public int getPoeniUkupno() {
        return poeniUkupno;
    }

    public void setPoeniUkupno(int poeniUkupno) {
        this.poeniUkupno = poeniUkupno;
    }
    
    public RezultatIspis(int indeks, Date datum) {
        this.indeks = indeks;
        this.datum = datum;
    }
}