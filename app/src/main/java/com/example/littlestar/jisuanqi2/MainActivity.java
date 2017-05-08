package com.example.littlestar.jisuanqi2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Stack ;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt0;
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    Button bt7;
    Button bt8;
    Button bt9;
    Button btjian;
    Button btjia;
    Button btdeng;
    Button btcheng;
    Button btchu;
    Button btC;
    Button btDEL;
    Button btzuo;
    Button btpoint;
    Button btyou;
    EditText et_input;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt0 = (Button) findViewById(R.id.bt0);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt7 = (Button) findViewById(R.id.bt7);
        bt8 = (Button) findViewById(R.id.bt8);
        bt9 = (Button) findViewById(R.id.bt9);
        btjia = (Button) findViewById(R.id.btjia);
        btjian = (Button) findViewById(R.id.btjian);
        btcheng = (Button) findViewById(R.id.btcheng);
        btchu = (Button) findViewById(R.id.btchu);
        btDEL= (Button) findViewById(R.id.btDEL);
        btdeng = (Button) findViewById(R.id.btdeng);
        btC = (Button) findViewById(R.id.btC);
        btzuo = (Button) findViewById(R.id.btzuo);
        btyou = (Button) findViewById(R.id.btyou);
        btpoint = (Button) findViewById(R.id.btpoint);
        et_input= (EditText) findViewById(R.id.et_input);


        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        btjia.setOnClickListener(this);
        btjian.setOnClickListener(this);
        btcheng.setOnClickListener(this);
        btchu.setOnClickListener(this);
        btDEL.setOnClickListener(this);
        btzuo.setOnClickListener(this);
        btyou.setOnClickListener(this);
        btpoint.setOnClickListener(this);
        btC.setOnClickListener(this);
        btdeng.setOnClickListener(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override public void onClick(View v) {
        String str = et_input.getText().toString();
            switch (v.getId()) {
                case R.id.bt0:
                case R.id.bt1:
                case R.id.bt2:
                case R.id.bt3:
                case R.id.bt4:
                case R.id.bt5:
                case R.id.bt6:
                case R.id.bt7:
                case R.id.bt8:
                case R.id.bt9:
                case R.id.btpoint:
                case R.id.btjia:
                case R.id.btjian:
                case R.id.btcheng:
                case R.id.btchu:
                    et_input.setText(str + ((Button) v).getText());
                    break;
                case R.id.btDEL:
                    et_input.setText(str.substring(0, str.length() - 1));
                    break;
                case R.id.btC:
                    et_input.setText("");
                    break;
                case R.id.btzuo:
                case R.id.btyou:
                    et_input.setText(str + ((Button) v).getText());
                    break;
                case R.id.btdeng:
                    Stack a=new Stack();
                    Stack b=new Stack();
                    int k=0;
                    String strc="";
                    String str1="";
                    String str2="";
                    int i=0,last=0,pre=0;
                    for(i=0;i<str.length();i++)
                    {
                        str2=str.substring(i,i+1);
                        if(str2.charAt(0)=='+'||str2.charAt(0)=='-')
                        {
                            if(k==1)
                            {
                                et_input.setText("error");
                                return;
                            }
                            k=1;
                            str1=str1+str.substring(last, i)+" ";
                            if(!a.empty())
                            {
                                if((a.peek().toString()).charAt(0)=='*'||(a.peek().toString()).charAt(0)=='/'||(a.peek().toString()).charAt(0)=='+'||(a.peek().toString()).charAt(0)=='-')
                                {
                                    while(!a.empty())
                                    {
                                        if((a.peek().toString()).charAt(0)=='(')
                                        {
                                            a.pop();
                                            break;
                                        }
                                        str1=str1+a.pop()+" ";
                                    }
                                    a.push(str2);
                                }
                                else
                                {
                                    a.push(str2);
                                }
                            }
                            else
                            {
                                a.push(str2);
                            }
                            last=i+1;
                        }
                        else if (str2.charAt(0)=='*'||str2.charAt(0)=='/')
                        {
                            if(k==1)
                            {
                                et_input.setText("error");
                                return;
                            }
                            k=1;
                            str1=str1+str.substring(last, i)+" ";
                            if(!a.empty())
                            {
                                if((a.peek().toString()).charAt(0)=='*'||(a.peek().toString()).charAt(0)=='/')
                                {
                                    while(!a.empty())
                                    {
                                        if((a.peek().toString()).charAt(0)=='(')
                                        {
                                            a.pop();
                                            break;
                                        }
                                        str1=str1+a.pop()+" ";
                                    }
                                    a.push(str2);
                                }
                                else
                                {
                                    a.push(str2);
                                }
                            }
                            else
                            {
                                a.push(str2);
                                k=0;
                            }
                            last=i+1;

                        }
                        else if(str2.charAt(0)=='(')
                        {

                            k=0;
                            a.push(str2);
                            last=i+1;
                        }
                        else if(str2.charAt(0)==')')
                        {
                            k-=0;
                            str1=str1+str.substring(last, i)+" ";
                            if(!a.empty())
                            {
                                while((a.peek().toString()).charAt(0)!='(')
                                {
                                    str1=str1+a.pop()+" ";
                                }
                                str1=str1.substring(0, str1.length()-1);
                                strc=strc+a.pop();
                            }
                            last=i+1;
                        }
                        else k=0;

                    }
                    str=str.substring(last);
                    str1=str1+str+" ";
                    while(!a.empty())
                    {
                        str1=str1+a.pop()+" ";
                    }

                    //后缀字符串；
                    last=0;
                    String num1;
                    String num2;
                    double numa;
                    double numb;
                    for(i=0;i<str1.length();i++)
                    {

                        if(str1.substring(i, i+1).charAt(0)==' ')
                        {
                            if(str1.substring(i-1, i).charAt(0)=='+')
                            {
                                num1=b.pop().toString();
                                num2=b.pop().toString();
                                numa=Double.parseDouble(num1);
                                numb=Double.parseDouble(num2);
                                b.push(numb+numa);
                                last=i;
                            }
                            else if(str1.substring(i-1, i).charAt(0)=='-')
                            {
                                num1=b.pop().toString();
                                num2=b.pop().toString();
                                numa=Double.parseDouble(num1);
                                numb=Double.parseDouble(num2);
                                b.push(numb-numa);
                                last=i;
                            }
                            else if(str1.substring(i-1, i).charAt(0)=='*')
                            {
                                num1=b.pop().toString();
                                num2=b.pop().toString();
                                numa=Double.parseDouble(num1);
                                numb=Double.parseDouble(num2);
                                b.push(numb*numa);
                                last=i;
                            }
                            else if(str1.substring(i-1, i).charAt(0)=='/')
                            {

                                num1=b.pop().toString();
                                num2=b.pop().toString();
                                numa=Double.parseDouble(num1);
                                numb=Double.parseDouble(num2);
                                b.push(numb/numa);
                                last=i;
                            }
                            else
                            {

                                b.push(str1.substring(last, i));
                                last=i+1;
                            }
                        }


                    }

                    et_input.setText(b.peek().toString());
             }

    }

}
