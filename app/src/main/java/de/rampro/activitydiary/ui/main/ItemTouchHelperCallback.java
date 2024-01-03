package de.rampro.activitydiary.ui.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃  神兽保佑
 * 　　　　┃　　　┃  代码无bug
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author hao
 * @date 2024/1/3
 * @description
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{
   private final ItemTouchHelperAdapter mAdapter;

   public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
      mAdapter = adapter;
   }

   @Override
   public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
      int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // 允许上下拖动
      int swipeFlags = 0; // 不允许左右滑动
      return makeMovementFlags(dragFlags, swipeFlags);
   }

   @Override
   public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
      mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
      return true;
   }

   @Override
   public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
      // 不做任何操作
   }

   @Override
   public boolean isLongPressDragEnabled() {
      return true; // 允许长按拖动
   }
}
