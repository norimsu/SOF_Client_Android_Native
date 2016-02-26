package com.example.cte1.stack_overflow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class OnClickListenerSignIn implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.member_signin_form, null, false);

        final EditText formTextId = (EditText) formElementsView.findViewById(R.id.formTextId);
        final EditText formTextPwd1 = (EditText) formElementsView.findViewById(R.id.formTextPwd1);
        final EditText formTextPwd2 = (EditText) formElementsView.findViewById(R.id.formTextPwd2);
        final EditText formTextAdrr = (EditText) formElementsView.findViewById(R.id.formTextAdrr);
        final EditText formTextEmail = (EditText) formElementsView.findViewById(R.id.formTextEmail);

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

                                dialog.cancel();
                            }

                        }).show();






    }
}
