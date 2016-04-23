package px500.pipoask.com;

import android.content.Context;
import android.content.Intent;

public class NavigationManager<T> {
    public void openActivity(Context ctx, Class<T> activityClass) {
        Intent intent = new Intent(ctx, activityClass);
        ctx.startActivity(intent);
    }
}