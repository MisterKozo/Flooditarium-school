package xyz.misterkozo.flooditarium.Utilities;

/**
 * Created by adhoms on 10/2/17.
 */

public class Utilities {
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    public static int firstOccurrence(String str, Character chr) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == chr) {
                return i;
            }
        }
        return -1;
    }
}
