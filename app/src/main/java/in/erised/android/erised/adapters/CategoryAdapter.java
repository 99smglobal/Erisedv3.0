package in.erised.android.erised.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import in.erised.android.erised.R;
import in.erised.android.erised.pojo.ListItemPOJO;

/**
 * Created by tamilselvankalimuthu on 02/08/15.
 */
public class CategoryAdapter extends RecyclerView
        .Adapter<CategoryAdapterViewHolder> {
    private static String LOG_TAG = "CategoryAdapter";
    private List<ListItemPOJO> mDataset;
    private Context context;

    public CategoryAdapter(Context context, List<ListItemPOJO> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cateogory_item, parent, false);

        return new CategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, int position) {
        ListItemPOJO pojo = mDataset.get(position);
        holder.itemName.setText(pojo.getName());
        holder.itemSelector.setChecked(pojo.isSelected());

        if (!pojo.isAnimaitonShown()) {
            pojo.setIsAnimaitonShown(true);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            animation.setDuration(1000);
            holder.parent.startAnimation(animation);
            animation = null;
        }
    }

    public void addItem(ListItemPOJO dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}