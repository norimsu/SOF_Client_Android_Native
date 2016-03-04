package com.example.cte1.stack_overflow;

// 안드로이드 lib
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    // View Update용 Handler 객체 선언
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 데이터, View 초기화
        super.onCreate(savedInstanceState);
        // 액션바 타이틀 변경
        getSupportActionBar().setTitle("로그인");

        setContentView(R.layout.activity_main);
        // 로그인 버튼 동작
        Button requestBtn1 = (Button)findViewById(R.id.requestBtn1);
        requestBtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast mToast;
                // 로딩중입니다.
                mToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.setText(R.string.toast_loading);
                mToast.show();
                // txtMsg에 Loading set
                TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
                txtMsg.setText("Loding");
                // URL 읽어오기
                EditText inputUrl = (EditText) findViewById(R.id.input_url);
                String urlStr = inputUrl.getText().toString();
                // 통신 thread start
                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();
            }
        });

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
            String response_result = "Fail";
            // 서버의 성공 실패 여부를 response_result 에 저장
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                TextView input_id = (TextView)findViewById(R.id.input_id);
                TextView input_pwd = (TextView)findViewById(R.id.input_pwd);

                nameValuePairs.add(new BasicNameValuePair("mname", input_id.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("mpwd", input_pwd.getText().toString()));
                // 보낼 객체 생성
                HttpClient client = new DefaultHttpClient();
                HttpParams params = client.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);

                HttpPost httpPost = new HttpPost(urlStr);
                UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                httpPost.setEntity(entityRequest);
                // 전송 & 수신
                HttpResponse response = client.execute(httpPost);
                Log.i("test 보내는내용 : ", "["+input_id.getText() + "], [" + input_pwd.getText()+"]" );

                // 응답 코드 확인
                Log.i("test1", "로그인 HTTPCODE : " + response.getStatusLine().getStatusCode());

                if(response.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent()),"utf-8"));

                    String line = null;
                    String result = "";

                    while((line=bufferedReader.readLine())!=null){
                        result+=line;
                    }

                    Log.e("test2","로그인 받아온내용 : "+result);

                    JSONObject responseJSON = new JSONObject(result);

                    String resultCaseby = (String)responseJSON.get("caseby");
                    String resultStatus = (String)responseJSON.get("status");
                    // caseby :login
                    // status : success OR disaccord
                    if(resultCaseby.equals("login"))
                    {
                        if(resultStatus.equals("success")){
                            // 로그인
                            Log.i("test","로그인 성공");
//                            Toast toastLogin = Toast.makeText(getApplicationContext(), R.string.toast_login, Toast.LENGTH_SHORT);
//                            toastLogin.setGravity(Gravity.CENTER,0,0);
//                            toastLogin.show();
                            response_result = "Success";
                            // 중요 !! Activity 이동하는 부분!!
                            changeActivity();
                        } else if(resultStatus.equals("disaccord")) {
                            // 아이디와 비밀번호를 확인해주세요.
                            Log.i("test","로그인 실패");
//                            Toast toast1 = Toast.makeText(getApplicationContext(), R.string.toast_disaccord, Toast.LENGTH_SHORT);
//                            toast1.setGravity(Gravity.CENTER, 0, 0);
//                            toast1.show();
                            response_result = "Check your ID/PWD";
                        } else {
                            // 알수없는 오류 1
                            Log.e("test","로그인 알수없는 오류1");
//                            Toast toast2 = Toast.makeText(getApplicationContext(), R.string.toast_xxxxx, Toast.LENGTH_SHORT);
//                            toast2.setGravity(Gravity.CENTER,0,0);
//                            toast2.show();
                        }
                    } else {
                        // 알수없는 오류 2
                        Log.e("test","로그인 알수없는 오류2");
//                        Toast toast3 = Toast.makeText(getApplicationContext(), R.string.toast_xxxxx, Toast.LENGTH_SHORT);
//                        toast3.setGravity(Gravity.CENTER,0,0);
//                        toast3.show();
                    }
                } else {
                    Log.e("test","로그인 응답코드 확인");
//                    Toast toast4 = Toast.makeText(getApplicationContext(),"받지 못함", Toast.LENGTH_SHORT);
//                    toast4.setGravity(Gravity.CENTER,0,0);
//                    toast4.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("test", "로그인 예외 처리");
                response_result = "Time Out";
            }
            return response_result;
        }
    }

    public void changeActivity(){
        Intent intent = new Intent(this, SubActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
