package com.loftschool.moneytracker2;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.text.Html.*;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = new ArrayList<>();

    public ItemsAdapter() {
        items.add(new Item("Молоко", 300 ));
        items.add(new Item("Зубная щётка", 250));
        items.add(new Item("Сковородка с антипригарным покрытием", 55));
        items.add(new Item("Паста", 300));
        items.add(new Item("Яйца", 60));
        items.add(new Item("Масло", 150));
        items.add(new Item("Мезим", 235));
        items.add(new Item("Сидр", 140));
        items.add(new Item("Открывашка", 50));
        items.add(new Item("Бензин", 1500));
        items.add(new Item("Чипсы", 120));
        items.add(new Item("Анальгин", 60));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
        //String string = parent.getContext().getString(R.string.ruble);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;


        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);

        }

        void bind(Item item) {
            name.setText(String.valueOf(item.getName()));
            price.setText(String.valueOf(item.getPrice()));


        }
    }

}
