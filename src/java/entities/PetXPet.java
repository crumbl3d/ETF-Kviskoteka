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
@Table(name = "petxpet")
public class PetXPet implements Serializable {
    
    @Id
    @Column(name = "idPetXPet")
    int idPetXPet;
    
    @Column(name = "s11")
    String s11;
    @Column(name = "s12")
    String s12;
    @Column(name = "s13")
    String s13;
    @Column(name = "s14")
    String s14;
    @Column(name = "s15")
    String s15;
    
    @Column(name = "s21")
    String s21;
    @Column(name = "s22")
    String s22;
    @Column(name = "s23")
    String s23;
    @Column(name = "s24")
    String s24;
    @Column(name = "s25")
    String s25;
    
    @Column(name = "s31")
    String s31;
    @Column(name = "s32")
    String s32;
    @Column(name = "s33")
    String s33;
    @Column(name = "s34")
    String s34;
    @Column(name = "s35")
    String s35;
    
    @Column(name = "s41")
    String s41;
    @Column(name = "s42")
    String s42;
    @Column(name = "s43")
    String s43;
    @Column(name = "s44")
    String s44;
    @Column(name = "s45")
    String s45;
    
    @Column(name = "s51")
    String s51;
    @Column(name = "s52")
    String s52;
    @Column(name = "s53")
    String s53;
    @Column(name = "s54")
    String s54;
    @Column(name = "s55")
    String s55;

    public int getIdPetXPet() {
        return idPetXPet;
    }

    public void setIdPetXPet(int idPetXPet) {
        this.idPetXPet = idPetXPet;
    }

    public String getS11() {
        return s11;
    }

    public void setS11(String s11) {
        this.s11 = s11;
    }

    public String getS12() {
        return s12;
    }

    public void setS12(String s12) {
        this.s12 = s12;
    }

    public String getS13() {
        return s13;
    }

    public void setS13(String s13) {
        this.s13 = s13;
    }

    public String getS14() {
        return s14;
    }

    public void setS14(String s14) {
        this.s14 = s14;
    }

    public String getS15() {
        return s15;
    }

    public void setS15(String s15) {
        this.s15 = s15;
    }

    public String getS21() {
        return s21;
    }

    public void setS21(String s21) {
        this.s21 = s21;
    }

    public String getS22() {
        return s22;
    }

    public void setS22(String s22) {
        this.s22 = s22;
    }

    public String getS23() {
        return s23;
    }

    public void setS23(String s23) {
        this.s23 = s23;
    }

    public String getS24() {
        return s24;
    }

    public void setS24(String s24) {
        this.s24 = s24;
    }

    public String getS25() {
        return s25;
    }

    public void setS25(String s25) {
        this.s25 = s25;
    }

    public String getS31() {
        return s31;
    }

    public void setS31(String s31) {
        this.s31 = s31;
    }

    public String getS32() {
        return s32;
    }

    public void setS32(String s32) {
        this.s32 = s32;
    }

    public String getS33() {
        return s33;
    }

    public void setS33(String s33) {
        this.s33 = s33;
    }

    public String getS34() {
        return s34;
    }

    public void setS34(String s34) {
        this.s34 = s34;
    }

    public String getS35() {
        return s35;
    }

    public void setS35(String s35) {
        this.s35 = s35;
    }

    public String getS41() {
        return s41;
    }

    public void setS41(String s41) {
        this.s41 = s41;
    }

    public String getS42() {
        return s42;
    }

    public void setS42(String s42) {
        this.s42 = s42;
    }

    public String getS43() {
        return s43;
    }

    public void setS43(String s43) {
        this.s43 = s43;
    }

    public String getS44() {
        return s44;
    }

    public void setS44(String s44) {
        this.s44 = s44;
    }

    public String getS45() {
        return s45;
    }

    public void setS45(String s45) {
        this.s45 = s45;
    }

    public String getS51() {
        return s51;
    }

    public void setS51(String s51) {
        this.s51 = s51;
    }

    public String getS52() {
        return s52;
    }

    public void setS52(String s52) {
        this.s52 = s52;
    }

    public String getS53() {
        return s53;
    }

    public void setS53(String s53) {
        this.s53 = s53;
    }

    public String getS54() {
        return s54;
    }

    public void setS54(String s54) {
        this.s54 = s54;
    }

    public String getS55() {
        return s55;
    }

    public void setS55(String s55) {
        this.s55 = s55;
    }

    public String getRec1() {
        if (s11 == null || s12 == null || s13 == null || s14 == null || s15 == null) {
            return null;
        }
        return s11 + s12 + s13 + s14 + s15;
    }

    public boolean setRec1(String rec1) {
        return setRec(0, rec1);
    }

    public String getRec2() {
        if (s21 == null || s22 == null || s23 == null || s24 == null || s25 == null) {
            return null;
        }
        return s21 + s22 + s23 + s24 + s25;
    }

    public boolean setRec2(String rec2) {
        return setRec(1, rec2);
    }
    
    public String getRec3() {
        if (s31 == null || s32 == null || s33 == null || s34 == null || s35 == null) {
            return null;
        }
        return s31 + s32 + s33 + s34 + s35;
    }

    public boolean setRec3(String rec3) {
        return setRec(2, rec3);
    }
    
    public String getRec4() {
        if (s41 == null || s42 == null || s43 == null || s44 == null || s45 == null) {
            return null;
        }
        return s41 + s42 + s43 + s44 + s45;
    }

    public boolean setRec4(String rec4) {
        return setRec(3, rec4);
    }

    public String getRec5() {
        if (s51 == null || s52 == null || s53 == null || s54 == null || s55 == null) {
            return null;
        }
        return s51 + s52 + s53 + s54 + s55;
    }

    public boolean setRec5(String rec5) {
        return setRec(4, rec5);
    }
    
    public PetXPet() {
        for (int i = 0; i < 5; i++) {
            setRec(i, "");
        }
    }
    
    private boolean setRec(int indexRec, String rec) {
        if (rec == null || rec.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                setSlovo(indexRec, i, "");
            }
            return true;
        }
        rec = rec.toLowerCase();
        String slovo;
        int indexSlovo = 0;
        for (int i = 0; i < rec.length(); i++) {
            char c = rec.charAt(i), c2 = i + 1 < rec.length() ? rec.charAt(i + 1) : '_';
            if (c == 'd' && c2 == 'ž' || (c == 'l' || c == 'n') && c2 == 'j') {
                i++;
                slovo = String.valueOf(c) + String.valueOf(c2);
            } else {
                slovo = String.valueOf(c);
            }
            if (!setSlovo(indexRec, indexSlovo, slovo)) {
                setRec(indexRec, null);
                return false;
            }
            indexSlovo++;
            if (indexSlovo > 5) {
                setRec(indexRec, null);
                return false;
            }
        }
        if (indexSlovo != 5) {
            setRec(indexRec, null);
            return false;
        }
        return true;
    }
    
    private boolean setSlovo(int indexRec, int indexSlovo, String slovo) {
        if (indexRec < 0 || indexRec > 4 || indexSlovo < 0 || indexRec > 4) {
            return false;
        }
        if (indexRec == 0) {
            if (indexSlovo == 0) {
                s11 = slovo;
            } else if (indexSlovo == 1) {
                s12 = slovo;
            } else if (indexSlovo == 2) {
                s13 = slovo;
            } else if (indexSlovo == 3) {
                s14 = slovo;
            } else {
                s15 = slovo;
            }
        } else if (indexRec == 1) {
            if (indexSlovo == 0) {
                s21 = slovo;
            } else if (indexSlovo == 1) {
                s22 = slovo;
            } else if (indexSlovo == 2) {
                s23 = slovo;
            } else if (indexSlovo == 3) {
                s24 = slovo;
            } else {
                s25 = slovo;
            }
        } else if (indexRec == 2) {
            if (indexSlovo == 0) {
                s31 = slovo;
            } else if (indexSlovo == 1) {
                s32 = slovo;
            } else if (indexSlovo == 2) {
                s33 = slovo;
            } else if (indexSlovo == 3) {
                s34 = slovo;
            } else {
                s35 = slovo;
            }
        } else if (indexRec == 3) {
            if (indexSlovo == 0) {
                s41 = slovo;
            } else if (indexSlovo == 1) {
                s42 = slovo;
            } else if (indexSlovo == 2) {
                s43 = slovo;
            } else if (indexSlovo == 3) {
                s44 = slovo;
            } else {
                s45 = slovo;
            }
        } else if (indexRec == 4) {
            if (indexSlovo == 0) {
                s51 = slovo;
            } else if (indexSlovo == 1) {
                s52 = slovo;
            } else if (indexSlovo == 2) {
                s53 = slovo;
            } else if (indexSlovo == 3) {
                s54 = slovo;
            } else {
                s55 = slovo;
            }
        }
        return true;
    }
}