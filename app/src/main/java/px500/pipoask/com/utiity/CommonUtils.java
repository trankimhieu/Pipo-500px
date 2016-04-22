package px500.pipoask.com.utiity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class CommonUtils {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
