package com.mybus.requirements;

import java.text.Normalizer;

public final class AddressValidator {

    private static final String AVENUE_STR = "Avenida";
    private static final String SHORT_AVENUE_STR = "Av";

    private AddressValidator() {
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
        text = normalizeAddress(text);
        String pattern = "[a-zA-Z0-9 .]+";
        return text.matches(pattern);
    }

    /**
     * @param address
     * @return
     */
    public static boolean isValidAddress(String address) {
        //disable number valitarion for now to enable street intersections
        return isValidCharacters(address);
    }

    /**
     * @param address
     * @return
     */
    public static String normalizeAddress(String address) {
        //Remove accents:
        address = Normalizer.normalize(address, Normalizer.Form.NFD);
        address = address.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        address = address.replaceAll("&", "y");
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
