package com.example.cte1.stack_overflow;

// 안드로이드 lib
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// json lib
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
// 통신 lib
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    // View Update용 Handler 객체 선언
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 데이터, View 초기화
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestBtn1 = (Button)findViewById(R.id.requestBtn1);

        requestBtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                Toast toastLogin = Toast.makeText(getApplicationContext(), R.string.toast_loading, Toast.LENGTH_LONG);
                toastLogin.setGravity(Gravity.CENTER,0,0);
                toastLogin.show();

                // txtMsg에 Loading set
                TextView txtMsg = (TextView)findViewById(R.id.txtMsg);
                txtMsg.setText("Loding");

                // URL 읽어오기
                EditText inputUrl = (EditText)findViewById(R.id.input_url);
                String urlStr = inputUrl.getText().toString();

                // 통신 thread start
                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();

                //toastLogin.cancel();

                //Toast toastFail = Toast.makeText(getApplicationContext(), R.string.toast_fail, Toast.LENGTH_LONG);
                //toastFail.setGravity(Gravity.CENTER, 0, 0);
                //toastFail.show();

            }
        });
        //Button requestBtn2 = (Button)findViewById(R.id.requestBtn2);
        //final Context context = view.getContext();

        Button buttonCreateLocation = (Button) findViewById(R.id.requestBtn2);
        buttonCreateLocation.setOnClickListener(new OnClickListenerSignIn());


    }



    class ConnectThread extends Thread {

        String urlStr;

        public ConnectThread(String inStr) {
            urlStr = inStr;
        }

        public void run() {
            try {

                // output 변수에 request(urlSTR) 결과값 반환
                final String output = request(urlStr);
                // handler에 넘겨주는 부분
                handler.post(new Runnable() {
                    public void run() {
                        TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
                        txtMsg.setText(output);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String request(String urlStr) {
            // 서버의 성공 실패 여부를 response_result 에 저장

            try {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                TextView input_id = (TextView)findViewById(R.id.input_id);
                TextView input_pwd = (TextView)findViewById(R.id.input_pwd);

                JSONObject json = new JSONObject();
                json.put("id", input_id.getText().toString());
                json.put("pwd", input_pwd.getText().toString());

                nameValuePairs.add(new BasicNameValuePair("responsePost", json.toString()));

                HttpClient client = new DefaultHttpClient(); // 보낼 객체 생성
                HttpParams params = client.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);

                HttpPost httpPost = new HttpPost(urlStr);
                UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                httpPost.setEntity(entityRequest);

                HttpResponse responsePost = client.execute(httpPost);

                /////////////////////////////////////////////////////////////////////////////////////////////////

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                int responseCode = conn.getResponseCode(); // Server에 연결
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream in = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;

                    int nLength = 0;
                    while((nLength = in.read(byteBuffer, 0, byteBuffer.length)) != -1){
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();

                    String response = new String(byteData);
                    JSONObject responseJSON = new JSONObject(response);

                    String result = (String)responseJSON.get("status");

                    Log.e("test1", result);

                    // 중요 !! Activity 이동하는 부분!!
                    if(result.toString().equals("success")){
                        changeActivity();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            String response_result = "Check your ID/PWD";

            return response_result;

        }
    }

    public void changeActivity(){
        Intent intent = new Intent(this, SubActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
