package in.erised.android.erised.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.erised.android.erised.R;

/**
 * Created by tamilselvankalimuthu on 02/08/15.
 */
public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder {
    protected TextView itemName;
    protected CheckBox itemSelector;
    protected RelativeLayout parent;

    public CategoryAdapterViewHolder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.item_name);
        itemSelector = (CheckBox) itemView.findViewById(R.id.listItemSelector);
        parent = (RelativeLayout) itemView.findViewById(R.id.parent);
    }
}