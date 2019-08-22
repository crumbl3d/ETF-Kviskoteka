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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author crumbl3d
 */
@Entity
@Table(name = "pehar")
public class Pehar implements Serializable {

    @Id
    @Column(name = "idPehar")
    int idPehar;
    
    @Column(name = "pitanje1")
    String pitanje1;
    
    @Column(name = "pitanje2")
    String pitanje2;
    
    @Column(name = "pitanje3")
    String pitanje3;
    
    @Column(name = "pitanje4")
    String pitanje4;
    
    @Column(name = "pitanje5")
    String pitanje5;
    
    @Column(name = "pitanje6")
    String pitanje6;
    
    @Column(name = "pitanje7")
    String pitanje7;
    
    @Column(name = "pitanje8")
    String pitanje8;
    
    @Column(name = "pitanje9")
    String pitanje9;
    
    @Column(name = "pitanje10")
    String pitanje10;
    
    @Column(name = "pitanje11")
    String pitanje11;
    
    @Column(name = "pitanje12")
    String pitanje12;
    
    @Column(name = "pitanje13")
    String pitanje13;

    @Column(name = "odgovor1")
    String odgovor1;
    
    @Column(name = "odgovor2")
    String odgovor2;
    
    @Column(name = "odgovor3")
    String odgovor3;
    
    @Column(name = "odgovor4")
    String odgovor4;
    
    @Column(name = "odgovor5")
    String odgovor5;
    
    @Column(name = "odgovor6")
    String odgovor6;
    
    @Column(name = "odgovor7")
    String odgovor7;
    
    @Column(name = "odgovor8")
    String odgovor8;
    
    @Column(name = "odgovor9")
    String odgovor9;
    
    @Column(name = "odgovor10")
    String odgovor10;
    
    @Column(name = "odgovor11")
    String odgovor11;

    @Column(name = "odgovor12")
    String odgovor12;
    
    @Column(name = "odgovor13")
    String odgovor13;

    public int getIdPehar() {
        return idPehar;
    }

    public void setIdPehar(int idPehar) {
        this.idPehar = idPehar;
    }

    public String getPitanje1() {
        return pitanje1;
    }

    public void setPitanje1(String pitanje1) {
        this.pitanje1 = pitanje1;
    }

    public String getPitanje2() {
        return pitanje2;
    }

    public void setPitanje2(String pitanje2) {
        this.pitanje2 = pitanje2;
    }

    public String getPitanje3() {
        return pitanje3;
    }

    public void setPitanje3(String pitanje3) {
        this.pitanje3 = pitanje3;
    }

    public String getPitanje4() {
        return pitanje4;
    }

    public void setPitanje4(String pitanje4) {
        this.pitanje4 = pitanje4;
    }

    public String getPitanje5() {
        return pitanje5;
    }

    public void setPitanje5(String pitanje5) {
        this.pitanje5 = pitanje5;
    }

    public String getPitanje6() {
        return pitanje6;
    }

    public void setPitanje6(String pitanje6) {
        this.pitanje6 = pitanje6;
    }

    public String getPitanje7() {
        return pitanje7;
    }

    public void setPitanje7(String pitanje7) {
        this.pitanje7 = pitanje7;
    }

    public String getPitanje8() {
        return pitanje8;
    }

    public void setPitanje8(String pitanje8) {
        this.pitanje8 = pitanje8;
    }

    public String getPitanje9() {
        return pitanje9;
    }

    public void setPitanje9(String pitanje9) {
        this.pitanje9 = pitanje9;
    }

    public String getPitanje10() {
        return pitanje10;
    }

    public void setPitanje10(String pitanje10) {
        this.pitanje10 = pitanje10;
    }

    public String getPitanje11() {
        return pitanje11;
    }

    public void setPitanje11(String pitanje11) {
        this.pitanje11 = pitanje11;
    }

    public String getPitanje12() {
        return pitanje12;
    }

    public void setPitanje12(String pitanje12) {
        this.pitanje12 = pitanje12;
    }

    public String getPitanje13() {
        return pitanje13;
    }

    public void setPitanje13(String pitanje13) {
        this.pitanje13 = pitanje13;
    }

    public String getOdgovor1() {
        return odgovor1;
    }

    public void setOdgovor1(String odgovor1) {
        this.odgovor1 = odgovor1;
    }

    public String getOdgovor2() {
        return odgovor2;
    }

    public void setOdgovor2(String odgovor2) {
        this.odgovor2 = odgovor2;
    }

    public String getOdgovor3() {
        return odgovor3;
    }

    public void setOdgovor3(String odgovor3) {
        this.odgovor3 = odgovor3;
    }

    public String getOdgovor4() {
        return odgovor4;
    }

    public void setOdgovor4(String odgovor4) {
        this.odgovor4 = odgovor4;
    }

    public String getOdgovor5() {
        return odgovor5;
    }

    public void setOdgovor5(String odgovor5) {
        this.odgovor5 = odgovor5;
    }

    public String getOdgovor6() {
        return odgovor6;
    }

    public void setOdgovor6(String odgovor6) {
        this.odgovor6 = odgovor6;
    }

    public String getOdgovor7() {
        return odgovor7;
    }

    public void setOdgovor7(String odgovor7) {
        this.odgovor7 = odgovor7;
    }

    public String getOdgovor8() {
        return odgovor8;
    }

    public void setOdgovor8(String odgovor8) {
        this.odgovor8 = odgovor8;
    }

    public String getOdgovor9() {
        return odgovor9;
    }

    public void setOdgovor9(String odgovor9) {
        this.odgovor9 = odgovor9;
    }

    public String getOdgovor10() {
        return odgovor10;
    }

    public void setOdgovor10(String odgovor10) {
        this.odgovor10 = odgovor10;
    }

    public String getOdgovor11() {
        return odgovor11;
    }

    public void setOdgovor11(String odgovor11) {
        this.odgovor11 = odgovor11;
    }

    public String getOdgovor12() {
        return odgovor12;
    }

    public void setOdgovor12(String odgovor12) {
        this.odgovor12 = odgovor12;
    }

    public String getOdgovor13() {
        return odgovor13;
    }

    public void setOdgovor13(String odgovor13) {
        this.odgovor13 = odgovor13;
    }
}