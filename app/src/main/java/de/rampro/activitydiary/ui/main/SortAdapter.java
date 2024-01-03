package de.rampro.activitydiary.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.rampro.activitydiary.R;
import de.rampro.activitydiary.helpers.GraphicsHelper;
import de.rampro.activitydiary.model.DiaryActivity;


public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private List<DiaryActivity> mFruitList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        TextView activity_name;
        FrameLayout fl_delete;
        RelativeLayout activity_background;

        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            activity_name = view.findViewById(R.id.activity_name);
            activity_background = view.findViewById(R.id.activity_background);
            fl_delete = view.findViewById(R.id.fl_delete);
        }
    }

    public SortAdapter(Context context, List<DiaryActivity> fruitList) {
        mFruitList = fruitList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DiaryActivity bean = mFruitList.get(position);
        holder.activity_name.setText(bean.getName());
        holder.activity_name.setTextColor(GraphicsHelper.textColorOnBackground(bean.getColor()));
        holder.activity_background.setBackgroundColor(bean.getColor());
        holder.fl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFruitList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void setData(List<DiaryActivity> fruitList) {
        mFruitList = fruitList;
        notifyDataSetChanged();
    }

    public List<DiaryActivity> getData() {
        return mFruitList;
    }

    public void onItemMove(int fromPosition, int toPosition) {
        DiaryActivity movedItem = mFruitList.get(fromPosition);
        mFruitList.remove(fromPosition);
        mFruitList.add(toPosition, movedItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void sortHighList() {
        Collections.sort(mFruitList, new Comparator<DiaryActivity>() {
            @Override
            public int compare(DiaryActivity data1, DiaryActivity data2) {
                String name1 = data1.getName();
                String name2 = data2.getName();
                return name1.compareToIgnoreCase(name2);
            }
        });
        notifyDataSetChanged();
    }

    public void sortLowList() {
        Collections.sort(mFruitList, new Comparator<DiaryActivity>() {
            @Override
            public int compare(DiaryActivity data1, DiaryActivity data2) {
                String name1 = data1.getName();
                String name2 = data2.getName();
                return name2.compareToIgnoreCase(name1);
            }
        });
        notifyDataSetChanged();
    }
}
