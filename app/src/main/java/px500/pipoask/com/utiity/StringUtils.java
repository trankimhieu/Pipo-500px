package px500.pipoask.com.utiity;

public class StringUtils {
    public static boolean isEmpty(String str) {
        boolean isEmpty = false;
        if (str == null || str.trim().length() == 0 || ("null").equalsIgnoreCase(str)) {
            isEmpty = true;
        }
        return isEmpty;
    }
}
