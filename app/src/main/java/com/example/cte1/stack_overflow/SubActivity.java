package com.example.cte1.stack_overflow;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class SubActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        ListView listView=(ListView)findViewById(R.id.listview); // 리스트뷰 객체 생성

        ArrayList<ListViewItem> data = new ArrayList<>();

        Log.e("test", "사진 넣기 전");

        ListViewItem Han = new ListViewItem(R.drawable.han, "한민지 입니다");
        ListViewItem Lee = new ListViewItem(R.drawable.lee, "이상현 입니다");
        ListViewItem An = new ListViewItem(R.drawable.an, "안병래 입니다");
        ListViewItem Ko = new ListViewItem(R.drawable.ko, "고민수 입니다");

        Log.e("test", "사진 넣은 후");

        data.add(Han);
        data.add(Lee);
        data.add(An);
        data.add(Ko);

        ListviewAdapter adapter=new ListviewAdapter(this, R.layout.activity_sub, data);
        listView.setAdapter(adapter);

        Log.e("text", "adapter set");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class ListViewItem {

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
