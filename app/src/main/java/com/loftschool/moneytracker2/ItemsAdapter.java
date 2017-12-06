package com.loftschool.moneytracker2;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = Collections.emptyList();
    private ItemsAdapterListener listener = null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, position, selectedItems.get(position, false), listener);

    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    Item remove(int pos) {
        final Item item = items.remove(pos);
        return item;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);

        }

        void bind(final Item item, final int position, boolean selected, final ItemsAdapterListener listener) {
            name.setText(item.name);
            Resources resources = price.getContext().getResources();
            String priceInStr = resources.getString(R.string.price, item.price);
            Spannable text = new SpannableString(priceInStr);
            text.setSpan(new ForegroundColorSpan(Color.WHITE), priceInStr.length() - 1, priceInStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            price.setText(text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, position);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(item, position);
                    return true;
                }
            });
            itemView.setActivated(selected);
        }
    }

}
