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

/**
 *
 * @author crumbl3d
 */
@Entity
@Table(name = "igradana")
public class IgraDana implements Serializable {

    @Id
    @Column(name = "datum")
    Date datum;
    
    @Column(name = "idAnagram")
    int idAnagram;
    
    @Column(name =  "idPetXPet")
    int idPetXPet;

    @Column(name =  "idPehar")
    int idPehar;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getIdPetXPet() {
        return idPetXPet;
    }

    public void setIdPetXPet(int idPetXPet) {
        this.idPetXPet = idPetXPet;
    }

    public int getIdAnagram() {
        return idAnagram;
    }

    public void setIdAnagram(int idAnagram) {
        this.idAnagram = idAnagram;
    }

    public int getIdPehar() {
        return idPehar;
    }

    public void setIdPehar(int idPehar) {
        this.idPehar = idPehar;
    }
}