package com.mybus.requirements;

public class AddressValidator {

    private static final int MAX_NUMBER = 20000;

    private AddressValidator() {
    }

    /**
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
     * @param text
     * @return
     */
    private static boolean isValidCharacters(String text) {
        if (text == null) {
            return false;
        }
        //regular expresion
        String pattern = "[a-zA-Z0-9 ,-]+";
        return text.matches(pattern);
    }

    /**
     * @param address
     * @return
     */
    public static boolean isValidAddress(String address) {
        if (!isValidCharacters(address)) {
            return false;
        }
        Long number = getStreetNumber(address);
        return number != null && number < MAX_NUMBER;
    }
}
