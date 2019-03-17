/*
 * Ứng dụng Calculator đơn giản với những phép tính quen thuộc như: Cộng, Trừ, Nhân, Chia,
 * Phần trăm, Bình phương, Căn bậc 2, Tính giai thừa làm việc với cả số nguyên và số thực.
 * Ứng dụng được thực hiện bởi nhóm 7 - Lập trình di động - Chiều thứ 5
 * Giảng viên hướng dẫn: Cô Trương Thị Ngọc Phượng
 * Thành viên nhóm:
 *      Nguyễn Thanh Giàu - 16110317
 *      Trần Văn Luyện - 16110385
 *      Nguyễn Trường Yên - 16110531
 *      Trần Phạm Minh Tín - 16110487
 *      Trần Minh Tùng - 16110516
 */
package com.group7.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMath;
    private TextView tvResult;
    private String mAns = "0";
    private Boolean mF = false, mDaTinh=false;
    //list the ids of the buttons
    private int[] idButton = {
            R.id.btn0,
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot, R.id.btnPlus, R.id.btnSub,
            R.id.btnMul, R.id.btnDiv, R.id.btnMod, R.id.btnOpen,
            R.id.btnClose, R.id.btnFactorial, R.id.btnANS,
            R.id.btnSquareRoot, R.id.btnX2, R.id.btnResult,
            R.id.btnDel, R.id.btnAC
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
    }

    //Method connectView
    private void connectView(){
        tvMath = (TextView) findViewById(R.id.tvMath);
        tvResult = (TextView) findViewById(R.id.tvResult);

        for (int i = 0; i < idButton.length; i++) {
            findViewById(idButton[i]).setOnClickListener(this);
        }
        Init();
    }
    private void Init()
    {
        tvMath.setText("|");
        tvResult.setText("0");
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        if (mF == true)
        {
            mF = false;
        }
        for (int i = 0; i < idButton.length - 3; i++) {
            if (id == idButton[i]) {
                String text = ((TextView) findViewById(id)).getText().toString();

                // clear char | on top
                if (tvMath.getText().toString().trim().equals("|")) {
                    tvMath.setText("");
                }
                if (id==R.id.btnANS) {
                    if (mDaTinh==true)
                        tvMath.setText("");
                    tvMath.append("ans");
                } else {
                    //show the text of the textview
                    tvMath.append(text);
                    mDaTinh=false;
                }
                return;
            }
        }

        // clear screen
        if (id == R.id.btnAC) {
            Init();
        } else //calculate
            if (id == R.id.btnResult) {
                Calculate();
                mDaTinh=true;
            } else //delete the last number of the string
                if (id==R.id.btnDel)
                {
                    String mTextMath;
                    mTextMath = tvMath.getText().toString();
                    mTextMath = mTextMath.substring(0,mTextMath.length()-1);
                    tvMath.setText(mTextMath);
                    mDaTinh=false;
                }
    }

    private void Calculate() {

        String math = tvMath.getText().toString().trim();
        if (math.length() > 0) {
            Balan balan = new Balan();
            String result = balan.valueMath(math) + "";
            String error = balan.getError();
            tvResult.setText(result);
            // check error
            if (error != null) {
                Toast.makeText(MainActivity.this, error
                        , Toast.LENGTH_SHORT).show();
            } else { // show result
                //save the result into ans when we touch the button =
                if (math.equals("ans")) {
                    tvResult.setText(mAns.toString());
                } else {
                    tvResult.setText(result);
                    mAns = result;
                    mF = true;
                }
            }
        }
    }
}
