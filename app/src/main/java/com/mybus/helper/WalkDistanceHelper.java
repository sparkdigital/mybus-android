package com.mybus.helper;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public final class WalkDistanceHelper {

    private static final int BLOCK_LENGHT = 100;
    private static String[] strArr = {"cero", "uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez"};

    private WalkDistanceHelper() {
    }

    /**
     * Returns the distance in blocks from meters
     *
     * @param distance
     * @return
     */
    public static String getDistanceInBlocks(double distance) {
        StringBuilder blocks = new StringBuilder("");
        int cantidad = (int) (distance / BLOCK_LENGHT);
        if (cantidad < 1) {
            blocks.append("Menos de una cuadra");
        } else if (cantidad == 1) {
            blocks.append("Una cuadra");
        } else {
            if (cantidad > 1 && cantidad < 10) {
                blocks.append(strArr[cantidad]);
            } else {
                blocks.append("Mas de diez");
            }
            blocks.append(" cuadras");
        }
        return blocks.toString();
    }
}
