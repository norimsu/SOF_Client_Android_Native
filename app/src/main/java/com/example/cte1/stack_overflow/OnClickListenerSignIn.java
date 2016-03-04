package com.example.cte1.stack_overflow;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OnClickListenerSignIn implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.member_signin_form, null, false);

        final EditText editTextId = (EditText) formElementsView.findViewById(R.id.formTextId);
        final EditText editTextPwd1 = (EditText) formElementsView.findViewById(R.id.formTextPwd1);
        final EditText editTextPwd2 = (EditText) formElementsView.findViewById(R.id.formTextPwd2);
        final EditText editTextAddr = (EditText) formElementsView.findViewById(R.id.formTextAddr);
        //final EditText editTextEmail1 = (EditText) formElementsView.findViewById(R.id.formTextEmail1);
        //final EditText editTextEmail2 = (EditText) formElementsView.findViewById(R.id.formTextEmail2);
        final Button addOk = (Button) formElementsView.findViewById(R.id.addOk);
        final Button addCancel = (Button) formElementsView.findViewById(R.id.addCancel);

        final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(formElementsView).setTitle("회원가입").show();
        // 확인 버튼
        addOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formTextId = editTextId.getText().toString();
                String formTextPwd1 = editTextPwd1.getText().toString();
                String formTextPwd2 = editTextPwd2.getText().toString();
                String formTextAddr = editTextAddr.getText().toString();
                //String formTextEmail1 = editTextEmail1.getText().toString();
                //String formTextEmail2 = editTextEmail2.getText().toString();
                Log.i("test1", formTextPwd1);
                Log.i("test2", formTextPwd2);
                if (formTextPwd1.equals(formTextPwd2)){
                    ConnectionTask joinThread = null;
                    try {
                        Log.i("test","aa");
                        joinThread = new ConnectionTask(formTextId, formTextPwd1, formTextAddr);
                        Log.i("test","bb");
                        joinThread.execute(formTextId, formTextPwd1, formTextAddr);
                        Log.i("test","cc");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                } else {
                    Toast toastPwd = Toast.makeText(context, "비밀번호가 다릅니다!", Toast.LENGTH_SHORT);
                    toastPwd.setGravity(Gravity.CENTER, 0, 0);
                    toastPwd.show();
                }
            }
        });
        // 취소 버튼
        addCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
    // 비동기테스크 백그라운드 작업
    class ConnectionTask extends AsyncTask<String, String, String> {
        String formTextId, formTextPwd1, formTextAddr;

        private Exception exception;

        public ConnectionTask(String id, String pwd, String addr) throws IOException, JSONException {
            Log.i("test89090", "감싸기 전 아이디 " + id);
            formTextId = id;
            formTextPwd1 = pwd;
            formTextAddr = addr;
        }

        protected String doInBackground(String... urls) {
            myMethod(formTextId, formTextPwd1, formTextAddr); // 통신 메소드
            return "";
        }

        public void myMethod(String formTextId, String formTextPwd1, String formTextAddr) {
            try {
                String urlStr = "http://10.131.158.30:8080/androidserver/member/join.do";
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.i("test", "보내기 전 아이디 : "+ this.formTextId);
                nameValuePairs.add(new BasicNameValuePair("mname", this.formTextId));
                nameValuePairs.add(new BasicNameValuePair("mpwd", this.formTextPwd1));
                nameValuePairs.add(new BasicNameValuePair("mloc", this.formTextAddr));

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
                Log.i("test 보내는내용 : ", "[" + formTextId + formTextPwd1 + formTextAddr + "]");
                // 응답 코드 확인
                Log.i("test1", "회원가입 HTTPCODE : " + response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
                    String line = null;
                    String result = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    Log.e("test2", "회원가입 받아온내용 : " + result);

                    JSONObject responseJSON = new JSONObject(result);

                    String resultCaseby = (String) responseJSON.get("caseby");
                    String resultStatus = (String) responseJSON.get("status");
                    // caseby :login
                    // status : success OR disaccord
                    if (resultCaseby.equals("join")) {
                        if (resultStatus.equals("success")) {
                            // 로그인
                            Log.i("test", "회원가입 성공");
//                            Toast toastLogin = Toast.makeText(getApplicationContext(), R.string.toast_login, Toast.LENGTH_SHORT);
//                            toastLogin.setGravity(Gravity.CENTER,0,0);
//                            toastLogin.show();

                        } else if (resultStatus.equals("disaccord")) {
                            // 아이디와 비밀번호를 확인해주세요.
                            Log.i("test", "회원가입 실패");
//                            Toast toast1 = Toast.makeText(getApplicationContext(), R.string.toast_disaccord, Toast.LENGTH_SHORT);
//                            toast1.setGravity(Gravity.CENTER, 0, 0);
//                            toast1.show();

                        } else {
                            // 알수없는 오류 1
                            Log.e("test", "회원가입 알수없는 오류1");
//                            Toast toast2 = Toast.makeText(getApplicationContext(), R.string.toast_xxxxx, Toast.LENGTH_SHORT);
//                            toast2.setGravity(Gravity.CENTER,0,0);
//                            toast2.show();
                        }
                    } else {
                        // 알수없는 오류 2
                        Log.e("test", "회원가입 알수없는 오류2");
//                        Toast toast3 = Toast.makeText(getApplicationContext(), R.string.toast_xxxxx, Toast.LENGTH_SHORT);
//                        toast3.setGravity(Gravity.CENTER,0,0);
//                        toast3.show();
                    }
                } else {
                    Log.e("test", "회원가입 응답코드 확인");
//                    Toast toast4 = Toast.makeText(getApplicationContext(),"받지 못함", Toast.LENGTH_SHORT);
//                    toast4.setGravity(Gravity.CENTER,0,0);
//                    toast4.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("test", "회원가입 예외 처리");
            }
        }
    }
}






