package com.mybus.helper;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class WalkDistanceHelper {

    private static String[] strArr = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",};
    private static int BLOCK_LENGHT = 100;

    /**
     * Returns the distance in blocks from meters
     *
     * @param distance
     * @return
     */
    public static String getDistanceInBlocks(double distance) {
        String blocks = "";
        int cantidad = (int) (distance / BLOCK_LENGHT);
        if (cantidad < 1) {
            blocks += "menos de una cuadra";
        } else if (cantidad > 1 && cantidad < 2) {
            blocks += "una cuadra";
        } else {
            if (cantidad > 1 && cantidad < 10) {
                blocks += strArr[cantidad];
            } else {
                blocks += "mas de diez";
            }
            blocks += " cuadras";
        }
        return blocks;
    }
}
