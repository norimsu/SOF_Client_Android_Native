package com.example.cte1.stack_overflow;
import android.content.Intent;
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

        ListViewItem Han = new ListViewItem(R.drawable.han, "Hybrid 앱 보안관련 이슈 공유");
        ListViewItem Lee = new ListViewItem(R.drawable.lee, "NetBeans에서 Cordova연동");
        ListViewItem An = new ListViewItem(R.drawable.an, "Spring MVC패턴 개념정리");
        ListViewItem Ko = new ListViewItem(R.drawable.ko, "Hybrid앱에서 자동로그인 구현하기");

        Log.e("test", "사진 넣은 후");

        data.add(Han);
        data.add(Lee);
        data.add(An);
        data.add(Ko);

        ListviewAdapter adapter=new ListviewAdapter(this, R.layout.item, data);
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
        if (id == R.id.action_logout){
            changeActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
