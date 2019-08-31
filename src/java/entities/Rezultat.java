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
package entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

/**
 *
 * @author crumbl3d
 */
@Entity
@Table(name = "rezultat")
public class Rezultat implements Serializable {

    @Id
    @Column(name = "datum")
    Date datum;
    
    @Id
    @Column(name = "korisnickoIme")
    String korisnickoIme;
    
    @Column(name = "poeniAnagram")
    int poeniAnagram;
    
    @Column(name = "poeniMojBroj")
    int poeniMojBroj;
    
    @Column(name = "poeniPetXPet")
    int poeniPetXPet;
    
    @Column(name = "poeniZanGeo")
    int poeniZanGeo;
    
    @Column(name = "poeniPehar")
    int poeniPehar;

    @Column(name = "wip", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean wip;
    
    @Formula("poeniAnagram + poeniMojBroj + poeniPetXPet + poeniZanGeo + poeniPehar")
    int poeniUkupno;
    
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

    public boolean isWip() {
        return wip;
    }

    public void setWip(boolean wip) {
        this.wip = wip;
    }

    public int getPoeniUkupno() {
        return poeniUkupno;
    }

    public void setPoeniUkupno(int poeniUkupno) {
        this.poeniUkupno = poeniUkupno;
    }
}