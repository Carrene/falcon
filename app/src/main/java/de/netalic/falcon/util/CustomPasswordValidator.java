package de.netalic.falcon.util;

public class CustomPasswordValidator {

    public static boolean hasMinimumLength(String password) {

        return password.length() > 7;
    }

    public static boolean checkSpace(String password) {

        if (password.contains(" ")) {

            return false;
        } else {

            return true;
        }
    }

    public static boolean hasCapitalLetter(String password) {

        return !password.equals(password.toLowerCase());
    }

    public static boolean hasDigit(String password) {

        return password.matches(".*\\d+.*");
    }
}
