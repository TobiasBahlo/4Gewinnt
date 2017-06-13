package com.bahlot.a4gewinnt.net;

import com.bahlot.a4gewinnt.backend.eColor;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ihatenames on 6/13/17.
 */
public class eColStringTest {
    private eColor colRed = eColor.red;
    private eColor colYellow = eColor.yellow;
    private eColor colBlue = eColor.blue;
    private eColor colNone = eColor.none;

    private String strRed = "red";
    private String strYellow = "yellow";
    private String strBlue = "blue";
    private String strNone = "none";

    @Test
    public void testValidFrom(){
        String s = eColString.convertFromECol(this.colRed);
        Assert.assertEquals("Red", this.strRed, s);

        s = eColString.convertFromECol(this.colYellow);
        Assert.assertEquals("Yellow", this.strYellow, s);

        s = eColString.convertFromECol(this.colBlue);
        Assert.assertEquals("Blue", this.strBlue, s);

        s = eColString.convertFromECol(this.colNone);
        Assert.assertEquals("None", this.strNone, s);
    }

    @Test
    public void validToRed(){
        eColor col = eColString.convertToECol(this.strRed);
        Assert.assertEquals("Red", this.colRed, col);

        col = eColString.convertToECol("RED");
        Assert.assertEquals("Red", this.colRed, col);
    }

    @Test
    public void validToYellow(){
        eColor col = eColString.convertToECol(this.strYellow);
        Assert.assertEquals("Yellow", this.colYellow, col);

        col = eColString.convertToECol("YELLOW");
        Assert.assertEquals("Yellow", this.colYellow, col);
    }

    @Test
    public void validToBlue(){
        eColor col = eColString.convertToECol(this.strBlue);
        Assert.assertEquals("Blue", this.colBlue, col);

        col = eColString.convertToECol("BLUE");
        Assert.assertEquals("Blue", this.colBlue, col);
    }

    @Test
    public void validToNone(){
        eColor col = eColString.convertToECol(this.strNone);
        Assert.assertEquals("None", this.colNone, col);

        col = eColString.convertToECol("NONE");
        Assert.assertEquals("None", this.colNone, col);
    }

    @Test
    public void nullToNone(){
        eColor col = eColString.convertToECol(null);
        Assert.assertEquals("None", eColor.none, col);
    }

    @Test
    public void emptyToNone(){
        eColor col = eColString.convertToECol("");
        Assert.assertEquals("None", eColor.none, col);
    }

    @Test
    public void noneFromNull(){
        String s = eColString.convertFromECol(null);
        Assert.assertEquals("none", this.strNone, s);
    }
}