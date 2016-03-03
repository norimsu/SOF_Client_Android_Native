package com.example.cte1.stack_overflow;

/**
 * Created by LDCC on 2016-03-03.
 */
public class ListViewItem {

    private int icon;
    private String name;

    public int getIcon(){
        return icon;
    }

    public String getName(){
        return name;
    }

    public ListViewItem(int icon, String name){
        this.icon = icon;
        this.name = name;
    }

}
