package com.example.cte1.stack_overflow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

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


        // 로직
        // 중복 아이디 체크 (버튼 추가?? 형식으로)
        // 비밀번호 일치 여부
        // 이메일 체크?? (중복여부 - 상관없나??, @들어가는 이메일인가

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("회원가입")
                .setPositiveButton("완료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // 가입 ---

                                //Toast toastPwd = Toast.makeText(MainActivity.this,"오류! 비밀번호!",Toast.LENGTH_SHORT);

                                //if(formTextPwd1 != formTextPwd2) //확인
                                //toastPwd.show();
                                String formTextEmail = formTextEmail1.getText().toString() + "@" + formTextEmail2.getText().toString();

                                try {
                                    JSONObject memberJSON = new JSONObject();
                                    memberJSON.put("id", formTextId.getText().toString());
                                    memberJSON.put("pwd", formTextPwd1.getText().toString());
                                    memberJSON.put("pwd", formTextAddr.getText().toString());
                                    memberJSON.put("pwd", formTextEmail);

                                }
                                catch (Exception e){

                                }




                                dialog.cancel();
                            }

                        }).show();






    }
}
