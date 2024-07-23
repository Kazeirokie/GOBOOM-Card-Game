public class Key {

    public static String encryptSuit(String encodeString) {
        String string = null;
        if (encodeString.equals("s")) {
            string = "1";
        } else if (encodeString.equals("d")) {
            string = "2";
        } else if (encodeString.equals("h")) {
            string = "3";
        } else if (encodeString.equals("c")) {
            string = "4";
        }
        return string;
    }

    public static String encryptRank(String encodeString) {

        String enString = encodeString.toUpperCase();
        String string = null;

        if (enString.equals("J")) {
            string = "11";
        } else if (enString.equals("Q")) {
            string = "12";
        } else if (enString.equals("K")) {
            string = "13";
        } else if (enString.equals("A")) {
            string = "14";
        } else {
            string = enString;
        }
        return string;
    }

    public static String decryptSuit(String decodeString) {
        String string = null;
        if (decodeString.equals("1")) {
            string = "s";
        } else if (decodeString.equals("2")) {
            string = "d";
        } else if (decodeString.equals("3")) {
            string = "h";
        } else if (decodeString.equals("4")) {
            string = "c";
        }
        return string;
    }

    public static String decodeRank(String decodeString) {
        String string = null;
        if (decodeString.equals("11")) {
            string = "J";
        } else if (decodeString.equals("12")) {
            string = "Q";
        } else if (decodeString.equals("13")) {
            string = "K";
        } else if (decodeString.equals("14")) {
            string = "A";
        } else {
            string = decodeString;
        }
        return string;
    }
}
