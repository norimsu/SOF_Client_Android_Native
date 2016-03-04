package com.example.cte1.stack_overflow;

/**
 * Created by LDCC on 2016-03-03.
 */
public class ListViewItem {

    private String mid;
    private String btitle;
    private String recommend;
    private String bhits;
    private String bdate;

    public String getmid(){
        return mid;
    }
    public String getbtitle(){
        return btitle;
    }
    public String getrecommend(){
        return recommend;
    }
    public String getbhits(){
        return bhits;
    }
    public String getbdate(){
        return bdate;
    }

    public ListViewItem(String mid, String btitle, String recommend, String bhits, String bdate){
        this.mid = mid;
        this.btitle = btitle;
        this.recommend = recommend;
        this.bhits = bhits;
        this.bdate = bdate;
    }

}
