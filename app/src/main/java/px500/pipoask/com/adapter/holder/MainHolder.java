package px500.pipoask.com.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    protected ClickListener listener;

    public MainHolder(View view) {
        super(view);
        view.setOnClickListener(this);
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClicked(v, getAdapterPosition());
        }
    }

    public interface ClickListener {
        void onItemClicked(View v, int position);
    }
}
