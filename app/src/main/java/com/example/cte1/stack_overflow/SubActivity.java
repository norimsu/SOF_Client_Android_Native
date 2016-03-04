package com.example.cte1.stack_overflow;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SubActivity extends ActionBarActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        String url = "http://10.131.158.30:8080/androidserver/board/list.do";
        ConnectThread thread = new ConnectThread(url);
        thread.start();
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
    // Connection Thread
    class ConnectThread extends Thread{

        String urlStr;
        String documentdata_original;
        String documentdata;
        public ConnectThread(String inStr){
            urlStr = inStr;
        }

        public void run(){
            documentdata_original = getdocument(urlStr);
            documentdata = documentdata_original.substring(8, documentdata_original.length()-1);
            Log.i("자른후", "data = "+documentdata);
            handler.post(new Runnable(){
                    public void run(){
                        // UI업데이트 부분!!
                        try {
                            JSONArray responseJSON = new JSONArray(documentdata);
                            ArrayList<ListViewItem> data = new ArrayList<>();
                            Log.i("json_test", "hello");
                            for(int i=0; i<responseJSON.length(); i++){
                                JSONObject temp = responseJSON.getJSONObject(i);
                                Log.i("json_test", "받은 데이터" + temp);
                                ListViewItem doc_info = new ListViewItem(temp.getString("bidx") + " ", temp.getString("mid") + " ",
                                        temp.getString("bdate") + " ", temp.getString("btitle") + " ", temp.getString("bhits") + " ");
                                data.add(doc_info);
                            }
                            makelist(data);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

        }

    }

    public String getdocument(String inStr) {

        String result = "";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            HttpPost httpPost = new HttpPost(inStr);
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));

                String line = null;


                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                Log.e("test_doc", "DOC 받아온내용 : " + result);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void makelist(ArrayList<ListViewItem> itemarray){
        ListView listView=(ListView)findViewById(R.id.listview); // 리스트뷰 객체 생성
        ListviewAdapter adapter=new ListviewAdapter(this, R.layout.item, itemarray);
        listView.setAdapter(adapter);
    }
}

