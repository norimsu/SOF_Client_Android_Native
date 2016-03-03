package com.example.cte1.stack_overflow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OnClickListenerSignIn implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.member_signin_form, null, false);

        final EditText formTextId = (EditText) formElementsView.findViewById(R.id.formTextId);
        final EditText formTextPwd1 = (EditText) formElementsView.findViewById(R.id.formTextPwd1);
        final EditText formTextPwd2 = (EditText) formElementsView.findViewById(R.id.formTextPwd2);
        final EditText formTextAddr = (EditText) formElementsView.findViewById(R.id.formTextAddr);
        final EditText formTextEmail1 = (EditText) formElementsView.findViewById(R.id.formTextEmail1);
        final EditText formTextEmail2 = (EditText) formElementsView.findViewById(R.id.formTextEmail2);

        /*
        중복 아이디 여부 (통신 필요)
        패스워드 확인
        */
        Log.e("test", formTextPwd1.toString());
        Log.e("test", formTextPwd2.toString());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("회원가입")
                .setPositiveButton("완료",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        // 가입
                            if (formTextPwd1.toString().equals(formTextPwd2.toString())) {
                                String formTextEmail = formTextEmail1.getText().toString() + "@" + formTextEmail2.getText().toString();

                                Log.e("test", formTextEmail);

                                try {
                                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                                    nameValuePairs.add(new BasicNameValuePair("mname", formTextId.getText().toString()));
                                    nameValuePairs.add(new BasicNameValuePair("mpwd", formTextPwd1.getText().toString()));
                                    nameValuePairs.add(new BasicNameValuePair("mloc", formTextAddr.getText().toString()));

                                    HttpClient client = new DefaultHttpClient(); // 보낼 객체 생성
                                    HttpParams params = client.getParams();
                                    HttpConnectionParams.setConnectionTimeout(params, 5000);
                                    HttpConnectionParams.setSoTimeout(params, 5000);

                                    //EditText memberUrl= (EditText) formElementsView.findViewById(R.string.member_url);
                                    String memberUrl = "http://10.131.158.30:8080/androidserver/join.do";
                                    Log.e("test", memberUrl);

                                    HttpPost httpPost = new HttpPost(memberUrl);
                                    UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                                    httpPost.setEntity(entityRequest);

                                    HttpResponse responsePost = client.execute(httpPost);

                                    /////////////////////////////////////////////////////////////////////////////////////////////////

                                    //case :join
                                    // status // success // disaccord

                                /*

                                URL url = new URL(memberUrl);
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

                                    //String result = (String)responseJSON.get("status");

                                    //Log.e("test1", result);


                                    // 중요 !! Activity 이동하는 부분!!
                                    //if(result.toString().equals("success")){

                                    // }

                                }

                            */
                                    dialog.cancel();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    dialog.cancel();
                                }//확인

                            } else {
                                Toast toastPwd = Toast.makeText(context, "비밀번호 오류!", Toast.LENGTH_SHORT);
                                toastPwd.setGravity(Gravity.CENTER, 0, 0);
                                toastPwd.show();
                            }
                        }
             }).show();
        }
    }
