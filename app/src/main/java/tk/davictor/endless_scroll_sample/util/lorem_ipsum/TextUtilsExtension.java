package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import android.text.TextUtils;

/**
 * 07.06.16
 * Created by Victor Ponomarenko
 */
public class TextUtilsExtension {

    private static final String WHITESPACE_PATTERN = "\\s+";
    private static final String EMPTY_STRING = "";


    /**
     * Returns true if a and b are equal in lower case, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equalsIgnoreCase(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.toString().equalsIgnoreCase(b.toString());
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all whitespaces from {@link String} parameter
     * @param a string for remove from it whitespaces
     * @return return {@link String} without whitespaces
     */
    public static String removeWhitespaces(String a) {
        if (TextUtils.isEmpty(a)) {
            return a;
        }

        return a.replaceAll(WHITESPACE_PATTERN, EMPTY_STRING);
    }
}
