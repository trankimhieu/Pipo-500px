package px500.pipoask.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NavigationManager<T> {
    public void openActivity(Context ctx, Class<T> activityClass) {
        Intent intent = new Intent(ctx, activityClass);
        ctx.startActivity(intent);
    }

    public void openActivity(Context ctx, Class<T> activityClass, String bundleName, Bundle bundle) {
        Intent intent = new Intent(ctx, activityClass);
        intent.putExtra(bundleName, bundle);
        ctx.startActivity(intent);
    }
}