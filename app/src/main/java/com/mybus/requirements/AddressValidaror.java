package com.mybus.requirements;

public class AddressValidaror {

    private static final long MAX_NUMBER = 9999;

    /**
     *
     * @param address
     * @return
     */
    private static Long getStreetNumber(String address) {
        String[] texts = address.split(" ");
        if (texts.length < 2) {
            return null;
        }
        String possibleNumber = texts[texts.length - 1];
        try {
            return Long.parseLong(possibleNumber);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     *
     * @param text
     * @return
     */
    private static boolean isValisCharacters(String text) {
        //regular expresion
        String pattern = "[a-zA-Z0-9 ,-]+";
        return text.matches(pattern);
    }

    /**
     *
     * @param address
     * @return
     */
    public static boolean isValidAddress(String address) {
        if (!isValisCharacters(address)) {
            return false;
        }
        Long number = getStreetNumber(address);
        if (number == null) {
            return true;
        }
        return number < MAX_NUMBER;
    }
}
