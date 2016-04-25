package px500.pipoask.com;

import android.app.AlertDialog;
import android.content.Context;

public class DialogManager {
    public static AlertDialog.Builder create(Context context) {
        return new AlertDialog.Builder(context)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // continue with delete
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }
}
