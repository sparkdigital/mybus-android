package com.mybus.requirements;

import java.text.Normalizer;

public final class AddressValidator {

    private static final int MAX_NUMBER = 20000;
    private static final String AVENUE_STR = "Avenida";
    private static final String SHORT_AVENUE_STR = "Av";

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
        String pattern = "[a-zA-Z0-9 ]+";
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

    /**
     * @param address
     * @return
     */
    public static String normalizeAddress(String address) {
        //Remove accents:
        address = Normalizer.normalize(address, Normalizer.Form.NFD);
        address = address.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        //Avenue string abbreviation:
        return address.replaceAll(AVENUE_STR, SHORT_AVENUE_STR);
    }

    /**
     * @param addressNumber
     * @return
     */
    public static String removeDash(String addressNumber) {
        if (addressNumber.contains("-")) {
            return addressNumber.split("-")[0];
        } else {
            return addressNumber;
        }
    }
}
