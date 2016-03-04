package com.example.cte1.stack_overflow;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInDialog extends Dialog  {

    public SignInDialog(Context context) {
        super(context);
    }
//    final EditText editTextId;
//    final EditText editTextPwd1;
//    final EditText editTextPwd2;
//    final EditText editTextAddr;
//    final Button addOk, addCancel;
//    String textId, textPwd1, textPwd2, textAddr;



    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.member_signin_form);


        final EditText editTextId = (EditText) findViewById(R.id.formTextId);
        final EditText editTextPwd1 = (EditText) findViewById(R.id.formTextPwd1);
        final EditText editTextPwd2 = (EditText) findViewById(R.id.formTextPwd2);
        final EditText editTextAddr = (EditText) findViewById(R.id.formTextAddr);
        final Button addOk = (Button) findViewById(R.id.addOk);
        final Button addCancel = (Button) findViewById(R.id.addCancel);



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
                    dismiss();
                } else {
                    Toast toastPwd = Toast.makeText(getContext(), "비밀번호가 다릅니다!", Toast.LENGTH_SHORT);
                    toastPwd.setGravity(Gravity.CENTER, 0, 0);
                    toastPwd.show();
                }
            }
        });
        addCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
//    public boolean onClick(View v, MotionEvent event) {
//        if (v == addOk) {
//            String formTextId = editTextId.getText().toString();
//            String formTextPwd1 = editTextPwd1.getText().toString();
//            String formTextPwd2 = editTextPwd2.getText().toString();
//            String formTextAddr = editTextAddr.getText().toString();
//            //String formTextEmail1 = editTextEmail1.getText().toString();
//            //String formTextEmail2 = editTextEmail2.getText().toString();
//            Log.i("test1", formTextPwd1);
//            Log.i("test2", formTextPwd2);
//            if (formTextPwd1.equals(formTextPwd2)){
//                dismiss();
//            } else {
//                Toast toastPwd = Toast.makeText(getContext(), "비밀번호가 다릅니다!", Toast.LENGTH_SHORT);
//                toastPwd.setGravity(Gravity.CENTER, 0, 0);
//                toastPwd.show();
//            }
//
//
//        } else if (v == addCancel)
//            cancel();
//        return false;
//    }

