package com.example.administrator.security.login.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.administrator.security.R;

/**
 * register page
 */

public class RegisterItemView extends LinearLayout{
    public RegisterItemView(Context context) {
        this(context,null);
    }

    public RegisterItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_register,this,true);
    }
}
