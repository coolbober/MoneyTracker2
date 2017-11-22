package com.loftschool.moneytracker2;

public class Item {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";

    public int id;
    public String name;
    public int price;
    public String type;

    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

}
