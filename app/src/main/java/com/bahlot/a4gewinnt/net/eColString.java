package com.bahlot.a4gewinnt.net;

import com.bahlot.a4gewinnt.backend.eColor;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

/**
 * Converts eColor to and from strings
 */
class eColString {

    /**
     * Convert eColor to lowercase string
     * @param color Color to be converted
     * @return Converted color or empty string if color was null or has a value not accounted for
     */
    static String convertFromECol(eColor color){
        String result;

        if (color == null){
            result = "none";
        } else {
            switch (color){
                case red:
                    result = "red";
                    break;
                case yellow:
                    result = "yellow";
                    break;
                case blue:
                    result = "blue";
                    break;
                case none:
                    result = "none";
                    break;
                default:
                    result = "none";
            }
        }


        return result;
    }

    /**
     * Convert a string to a color, not case sensitive
     * @param col String representing a eColor value e.g. "yellow" or "YELLOW"
     * @return Converted color or null of no string matched or col was null
     */
    static eColor convertToECol(String col){
        eColor result;

        if (col == null || col.isEmpty()){
            result = eColor.none;
        } else {
            String colCase = col.toLowerCase();

            switch (colCase) {
                case "red":
                    result = eColor.red;
                    break;
                case "yellow":
                    result = eColor.yellow;
                    break;
                case "none":
                    result = eColor.none;
                    break;
                case "blue":
                    result = eColor.blue;
                    break;
                default:
                    result = eColor.none;
                    break;
            }
        }
        return result;
    }
}
