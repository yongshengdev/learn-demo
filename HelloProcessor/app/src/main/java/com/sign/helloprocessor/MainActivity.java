package com.sign.helloprocessor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sign.lib_annotation.BindingView;
import com.sign.lib_interface.BindView;

/**
 * @author: yongshengdev@163.com
 * @created on 2022/2/23
 * @description:
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindingView.init(this);
        tv.setText("1234567");
    }
}
